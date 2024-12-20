package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:sqlite:Banco.db";

    public static Connection getConnection() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar o banco: " + ex.getMessage());
        }
        return conexao;
    }
}