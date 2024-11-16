package database;

import java.sql.*;

public class criarBancoDeDados {

    private static final String DATABASE_URL = "jdbc:sqlite:Banco.db";

    public static void createDatabaseAndTables() throws SQLException {
        try (Connection conexao = DriverManager.getConnection(DATABASE_URL)) {
            try (Statement stmt = conexao.createStatement()) {

                stmt.execute("CREATE TABLE IF NOT EXISTS Produto (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome TEXT," +
                        "categoria TEXT," +
                        "quantidade INTEGER," +
                        "custo REAL," +
                        "valor REAL)");

                stmt.execute("CREATE TABLE IF NOT EXISTS Cliente (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome TEXT," +
                        "cpf TEXT," +
                        "data_nascimento TEXT," +
                        "email TEXT," +
                        "telefone TEXT)");

                stmt.execute("CREATE TABLE IF NOT EXISTS Venda (" +
                        "idVenda INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "dataVenda TEXT," +
                        "metodoPagamento TEXT," +
                        "totalVenda REAL," +
                        "clienteNome TEXT)");

                stmt.execute("CREATE TABLE IF NOT EXISTS ItensVenda (" +
                        "idItemVenda INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "idProduto INTEGER," +
                        "quantidade INTEGER," +
                        "precoUnitario REAL," +
                        "idVenda INTEGER)");

                stmt.execute("CREATE TABLE IF NOT EXISTS Usuarios (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nome TEXT NOT NULL, " +
                        "senha TEXT NOT NULL, " +
                        "adm INTEGER NOT NULL);");

                System.out.println("Banco de dados e tabelas criados com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao criar tabelas: " + e.getMessage());
            }
        }
    }
}
