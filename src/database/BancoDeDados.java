package database;

import java.sql.*;

public class BancoDeDados {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "BancoWSMS";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static void createDatabaseAndTables() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            try (Statement stmt = connection.createStatement()) {

                stmt.execute("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
                System.out.println("Banco de dados criado ou já existente.");

                String databaseUrl = DATABASE_URL + DATABASE_NAME;
                try (Connection dbConnection = DriverManager.getConnection(databaseUrl, USER, PASSWORD);
                     Statement dbStmt = dbConnection.createStatement()) {

                    String createTableProduto = "CREATE TABLE IF NOT EXISTS Produto (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "nome VARCHAR(100) NOT NULL," +
                            "categoria VARCHAR(50)," +
                            "quantidade INT NOT NULL DEFAULT 0," +
                            "custo DECIMAL(10, 2) NOT NULL," +
                            "valor DECIMAL(10, 2) NOT NULL" +
                            ");";
                    dbStmt.execute(createTableProduto);

                    String createTableUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "nome VARCHAR(100) NOT NULL," +
                            "senha VARCHAR(255) UNIQUE NOT NULL," +
                            "adm BOOLEAN" +
                            ");";


                    dbStmt.execute(createTableUsuarios);

                    String createTableVenda = "CREATE TABLE IF NOT EXISTS Venda (" +
                            "idVenda INT PRIMARY KEY AUTO_INCREMENT," +
                            "dataVenda DATETIME DEFAULT CURRENT_TIMESTAMP," +
                            "metodoPagamento VARCHAR(50)," +
                            "totalVenda DECIMAL(10, 2) NOT NULL," +
                            "clienteNome VARCHAR(255)" +
                            ");";
                    dbStmt.execute(createTableVenda);

                    String createTableItensVenda = "CREATE TABLE IF NOT EXISTS ItensVenda (" +
                            "idItemVenda INT PRIMARY KEY AUTO_INCREMENT," +
                            "idProduto INT," +
                            "quantidade INT NOT NULL," +
                            "precoUnitario DECIMAL(10, 2) NOT NULL," +
                            "idVenda INT," +
                            "FOREIGN KEY (idVenda) REFERENCES Venda(idVenda)," +
                            "FOREIGN KEY (idProduto) REFERENCES Produto(id)" +
                            ");";
                    dbStmt.execute(createTableItensVenda);

                    String createTableCliente = "CREATE TABLE IF NOT EXISTS Cliente (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "nome VARCHAR(100) NOT NULL," +
                            "cpf VARCHAR(30) UNIQUE NOT NULL," +
                            "data_nascimento DATE NOT NULL," +
                            "email VARCHAR(100) UNIQUE," +
                            "telefone VARCHAR(30)" +
                            ");";
                    dbStmt.execute(createTableCliente);

                    System.out.println("Tabelas criadas com sucesso!");


                }
            } catch (SQLException e) {
                System.out.println("Erro ao criar o banco de dados ou tabelas: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

