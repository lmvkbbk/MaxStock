package DAO;

import database.Conexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.ItensVenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItensVendaDAO {

    public ObservableList<ItensVenda> buscarItensVendaPorId(int idVenda) {
        ObservableList<ItensVenda> itensVenda = FXCollections.observableArrayList();
        String sql = "SELECT p.nome, iv.quantidade, iv.precoUnitario " +
                "FROM ItensVenda iv " +
                "JOIN Produto p ON iv.idProduto = p.id " +
                "WHERE iv.idVenda = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idItemVenda);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao remover item de venda: " + e.getMessage());
            return false;
        }
    }
}

