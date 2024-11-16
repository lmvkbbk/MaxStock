package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.ItensVenda;

import java.sql.*;

public class ItensVendaDAO {

    private static final String DATABASE_URL = "jdbc:sqlite:Banco.db";

    public ObservableList<ItensVenda> buscarItensVendaPorId(int idVenda) {
        ObservableList<ItensVenda> itensVenda = FXCollections.observableArrayList();
        String sql = "SELECT p.nome, iv.quantidade, iv.precoUnitario " +
                "FROM ItensVenda iv " +
                "JOIN Produto p ON iv.idProduto = p.id " +
                "WHERE iv.idVenda = ?";

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nomeProduto = rs.getString("nome");
                    int quantidade = rs.getInt("quantidade");
                    double precoUnitario = rs.getDouble("precoUnitario");

                    ItensVenda itemVenda = new ItensVenda(nomeProduto, quantidade, precoUnitario);
                    itensVenda.add(itemVenda);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar itens da venda: " + e.getMessage());
        }

        return itensVenda;
    }

    public boolean adicionarItem(int idProduto, int quantidade, double precoUnitario, int idVenda) {
        String sql = "INSERT INTO ItensVenda (idProduto, quantidade, precoUnitario, idVenda) VALUES (?, ?, ?, ?)";

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, idProduto);
            pstmt.setInt(2, quantidade);
            pstmt.setDouble(3, precoUnitario);
            pstmt.setInt(4, idVenda);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar item à venda: " + e.getMessage());
            return false;
        }
    }

    public boolean removerItemVenda(int idItemVenda) {
        String sql = "DELETE FROM ItensVenda WHERE idItemVenda = ?";

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idItemVenda);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao remover item de venda: " + e.getMessage());
            return false;
        }
    }
}

