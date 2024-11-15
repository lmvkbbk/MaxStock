package DAO;

import models.Venda;
import database.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class VendaDAO {

    public ObservableList<Venda> pesquisarVendas(String pesquisa) {
        String sql = "SELECT idVenda, dataVenda, metodoPagamento, totalVenda, clienteNome FROM Venda " +
                "WHERE metodoPagamento LIKE ? OR clienteNome LIKE ? OR DATE(dataVenda) = ?";

        ObservableList<Venda> vendas = FXCollections.observableArrayList();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + pesquisa + "%");
            stmt.setString(2, "%" + pesquisa + "%");

            try {
                LocalDate dataPesquisa = LocalDate.parse(pesquisa);
                stmt.setDate(3, Date.valueOf(dataPesquisa));
            } catch (DateTimeParseException e) {
                stmt.setDate(3, null);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Venda venda = new Venda();
                venda.setIdVenda(rs.getInt("idVenda"));
                venda.setDataVenda(rs.getDate("dataVenda").toLocalDate());
                venda.setMetodoPagamento(rs.getString("metodoPagamento"));
                venda.setTotalVenda(rs.getDouble("totalVenda"));
                venda.setClienteNome(rs.getString("clienteNome"));

                vendas.add(venda);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao efetuar a pesquisa de vendas: " + e.getMessage());
        }

        return vendas;
    }

    public boolean removerVenda(int idVenda) {
        String sqlRemoverItens = "DELETE FROM ItensVenda WHERE idVenda = ?";
        String sqlRemoverVenda = "DELETE FROM Venda WHERE idVenda = ?";

        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmtRemoverItens = connection.prepareStatement(sqlRemoverItens);
             PreparedStatement stmtRemoverVenda = connection.prepareStatement(sqlRemoverVenda)) {

            stmtRemoverItens.setInt(1, idVenda);
            stmtRemoverItens.executeUpdate();

            stmtRemoverVenda.setInt(1, idVenda);
            int rowsAffected = stmtRemoverVenda.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao remover venda: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarTotalVenda(int idVenda, double totalVenda) {
        String sql = "UPDATE Venda SET totalVenda = ? WHERE idVenda = ?";

        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, totalVenda);
            stmt.setInt(2, idVenda);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar total da venda: " + e.getMessage());
            return false;
        }
    }

    public ObservableList<Venda> buscarVendasPorPeriodo(Date inicio, Date fim) {
        ObservableList<Venda> vendas = FXCollections.observableArrayList();
        String sql = """
            SELECT idVenda, dataVenda, totalVenda, clienteNome
            FROM Venda
            WHERE dataVenda BETWEEN ? AND ?
            """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, inicio);
            stmt.setDate(2, fim);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idVenda = rs.getInt("idVenda");
                    LocalDate dataVenda = rs.getDate("dataVenda").toLocalDate();
                    double totalVenda = rs.getDouble("totalVenda");
                    String clienteNome = rs.getString("clienteNome");

                    vendas.add(new Venda(idVenda, dataVenda, totalVenda, clienteNome));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar vendas por período: " + e.getMessage());
        }
        return vendas;
    }

    public double calcularTotalArrecadado(ObservableList<Venda> vendas) {
        double total = 0.0;
        for (Venda venda : vendas) {
            total += venda.getTotalVenda();
        }
        return total;
    }

    public double calcularLucroBruto(Date inicio, Date fim) {
        double lucroBruto = 0.0;
        String sql = """
            SELECT SUM(iv.quantidade * (iv.precoUnitario - p.custo)) AS lucroBruto
            FROM ItensVenda iv
            JOIN Produto p ON iv.idProduto = p.id
            JOIN Venda v ON iv.idVenda = v.idVenda
            WHERE v.dataVenda BETWEEN ? AND ?
            """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, inicio);
            stmt.setObject(2, fim);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    lucroBruto = rs.getDouble("lucroBruto");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lucroBruto;
    }

}
