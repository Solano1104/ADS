package com.agendamento.compartilhado.persistence;

import com.agendamento.compartilhado.reflection.ReflectionMapper;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public abstract class BaseRepository<T> {

    protected final Class<T> entityClass;

    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
        createTable();
    }

    private void createTable() {
        String ddl = ReflectionMapper.buildCreateTable(entityClass);
        try (Statement stmt = conn().createStatement()) {
            stmt.execute(ddl);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela: " + e.getMessage(), e);
        }
    }

    public void save(T entity) {
        String sql = ReflectionMapper.buildInsert(entityClass);
        try (PreparedStatement ps = conn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ReflectionMapper.bindInsert(ps, entity);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                setPrimaryKey(entity, keys.getLong(1));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar: " + e.getMessage(), e);
        }
    }

    public Optional<T> findById(long id) {
        String sql = ReflectionMapper.buildSelectById(entityClass);
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(ReflectionMapper.mapRow(rs, entityClass));
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar por ID: " + e.getMessage(), e);
        }
    }

    public List<T> findAll() {
        String sql = ReflectionMapper.buildSelectAll(entityClass);
        List<T> list = new ArrayList<>();
        try (Statement stmt = conn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(ReflectionMapper.mapRow(rs, entityClass));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar: " + e.getMessage(), e);
        }
        return list;
    }

    protected List<T> findWhere(String whereClause, Object... params) {
        String sql = ReflectionMapper.buildSelectAll(entityClass) + " WHERE " + whereClause;
        List<T> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(ReflectionMapper.mapRow(rs, entityClass));
        } catch (Exception e) {
            throw new RuntimeException("Erro na query: " + e.getMessage(), e);
        }
        return list;
    }

    public void update(T entity) {
        String sql = ReflectionMapper.buildUpdate(entityClass);
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ReflectionMapper.bindUpdate(ps, entity);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar: " + e.getMessage(), e);
        }
    }

    public void delete(long id) {
        String sql = ReflectionMapper.buildDelete(entityClass);
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar: " + e.getMessage(), e);
        }
    }

    protected Connection conn() {
        try { return DatabaseConnection.getInstance(); }
        catch (SQLException e) { throw new RuntimeException("Conexão falhou", e); }
    }

    private void setPrimaryKey(T entity, long id) throws Exception {
        Optional<Field> pk = ReflectionMapper.findPrimaryKeyField(entityClass);
        if (pk.isPresent()) {
            Field f = pk.get();
            f.setAccessible(true);
            if (f.getType() == long.class || f.getType() == Long.class) f.set(entity, id);
            else if (f.getType() == int.class || f.getType() == Integer.class) f.set(entity, (int) id);
        }
    }
}
