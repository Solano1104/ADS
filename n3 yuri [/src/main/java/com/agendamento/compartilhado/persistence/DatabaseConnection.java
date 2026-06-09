package com.agendamento.compartilhado.persistence;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:agendamento.db";
    private static Connection instance;
    private static boolean schemaExecutado = false;

    private DatabaseConnection() {}

    public static Connection getInstance() throws SQLException {
        if (instance == null || instance.isClosed()) {
            try { Class.forName("org.sqlite.JDBC"); }
            catch (ClassNotFoundException e) { throw new SQLException("Driver SQLite não encontrado", e); }

            instance = DriverManager.getConnection(URL);
            instance.createStatement().execute("PRAGMA foreign_keys = ON");
        }
        return instance;
    }

    public static void inicializar() {
        if (schemaExecutado) return;
        schemaExecutado = true;

        URL resource = DatabaseConnection.class.getClassLoader().getResource("schema.sql");
        if (resource == null) {
            System.out.println("[DB] schema.sql não encontrado — DDL será gerado automaticamente por convenção.");
            return;
        }

        try (InputStream is = resource.openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String conteudo = reader.lines()
                    .filter(l -> !l.trim().startsWith("--"))
                    .collect(Collectors.joining("\n"));

            List<String> statements = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (String linha : conteudo.split("\n")) {
                sb.append(linha).append("\n");
                if (linha.trim().endsWith(";")) {
                    String stmt = sb.toString().replace(";", "").trim();
                    if (!stmt.isEmpty()) statements.add(stmt);
                    sb.setLength(0);
                }
            }

            Connection conn = getInstance();
            try (Statement stmt = conn.createStatement()) {
                int ok = 0, skip = 0;
                for (String sql : statements) {
                    try {
                        stmt.execute(sql);
                        ok++;
                    } catch (SQLException e) {
                        if (e.getMessage().contains("already exists") ||
                            e.getMessage().contains("UNIQUE constraint")) {
                            skip++;
                        } else {
                            System.err.println("[DB] SQL ignorado: " + e.getMessage());
                        }
                    }
                }
                System.out.printf("[DB] schema.sql: %d statements OK, %d ignorados (já existentes).%n", ok, skip);
            }

        } catch (IOException | SQLException e) {
            System.err.println("[DB] Erro ao executar schema.sql: " + e.getMessage());
        }
    }

    public static void close() throws SQLException {
        if (instance != null && !instance.isClosed()) instance.close();
    }
}
