package com.agendamento.compartilhado.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReflectionMapper {

    public static String tableName(Class<?> cls) {
        return cls.getSimpleName().toLowerCase();
    }

    public static Optional<Field> findPrimaryKeyField(Class<?> cls) {
        for (Field f : getAllFields(cls)) {
            if ("id".equalsIgnoreCase(f.getName())) return Optional.of(f);
        }
        return Optional.empty();
    }

    public static List<Field> getAllFields(Class<?> cls) {
        List<Field> fields = new ArrayList<>();
        Class<?> current = cls;
        while (current != null && current != Object.class) {
            for (Field f : current.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) fields.add(f);
            }
            current = current.getSuperclass();
        }
        return fields;
    }

    public static String buildCreateTable(Class<?> cls) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(tableName(cls)).append(" (\n");
        for (Field f : getAllFields(cls)) {
            String col = f.getName();
            if ("id".equalsIgnoreCase(col)) sb.append("  id INTEGER PRIMARY KEY AUTOINCREMENT,\n");
            else sb.append("  ").append(col).append(" TEXT,\n");
        }
        sb.setLength(sb.length() - 2);
        sb.append("\n);");
        return sb.toString();
    }

    public static String buildInsert(Class<?> cls) {
        List<Field> fields = getAllFields(cls);
        String cols = "";
        String params = "";
        for (Field f : fields) {
            if ("id".equalsIgnoreCase(f.getName())) continue;
            cols += f.getName() + ", ";
            params += "?, ";
        }
        if (!cols.isEmpty()) {
            cols = cols.substring(0, cols.length() - 2);
            params = params.substring(0, params.length() - 2);
        }
        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName(cls), cols, params);
    }

    public static void bindInsert(PreparedStatement ps, Object obj) throws Exception {
        List<Field> fields = getAllFields(obj.getClass());
        int idx = 1;
        for (Field f : fields) {
            if ("id".equalsIgnoreCase(f.getName())) continue;
            f.setAccessible(true);
            ps.setObject(idx++, f.get(obj));
        }
    }

    public static <T> T mapRow(ResultSet rs, Class<T> cls) throws Exception {
        T inst = cls.getDeclaredConstructor().newInstance();
        ResultSetMetaData meta = rs.getMetaData();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            String col = meta.getColumnLabel(i);
            try {
                Field f = cls.getDeclaredField(col);
                f.setAccessible(true);
                Object val = rs.getObject(i);
                if (val != null) f.set(inst, val);
            } catch (NoSuchFieldException ignored) {}
        }
        return inst;
    }

    public static String buildSelectById(Class<?> cls) {
        return "SELECT * FROM " + tableName(cls) + " WHERE id = ?";
    }

    public static String buildSelectAll(Class<?> cls) { return "SELECT * FROM " + tableName(cls); }

    public static String buildUpdate(Class<?> cls) {
        List<Field> fields = getAllFields(cls);
        String set = "";
        for (Field f : fields) {
            if ("id".equalsIgnoreCase(f.getName())) continue;
            set += f.getName() + " = ?, ";
        }
        if (!set.isEmpty()) set = set.substring(0, set.length() - 2);
        return String.format("UPDATE %s SET %s WHERE id = ?", tableName(cls), set);
    }

    public static void bindUpdate(PreparedStatement ps, Object obj) throws Exception {
        List<Field> fields = getAllFields(obj.getClass());
        int idx = 1;
        Object idVal = null;
        for (Field f : fields) {
            f.setAccessible(true);
            if ("id".equalsIgnoreCase(f.getName())) { idVal = f.get(obj); continue; }
            ps.setObject(idx++, f.get(obj));
        }
        ps.setObject(idx, idVal);
    }

    public static String buildDelete(Class<?> cls) { return "DELETE FROM " + tableName(cls) + " WHERE id = ?"; }
}
