package DAO;

import models.Venda;
import database.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class VendaDAO {

    private static final String DATABASE_URL = "jdbc:sqlite:Banco.db";

    public ObservableList<Venda> pesquisarVendas(String pesquisa) {
        String sql = "SELECT idVenda, dataVenda, metodoPagamento, totalVenda, clienteNome FROM Venda " +
                "WHERE metodoPagamento LIKE ? OR clienteNome LIKE ? OR DATE(dataVenda) = ?";

        ObservableList<Venda> vendas = FXCollections.observableArrayList();

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, "%" + pesquisa + "%");
            stmt.setString(2, "%" + pesquisa + "%");
            stmt.setString(3, pesquisa.matches("\\d{4}-\\d{2}-\\d{2}") ? String.valueOf(Date.valueOf(LocalDate.parse(pesquisa))) : null);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Venda venda = new Venda();
                venda.setIdVenda(rs.getInt("idVenda"));
                venda.setDataVenda(rs.getString("dataVenda"));
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

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmtRemoverItens = conexao.prepareStatement(sqlRemoverItens);
             PreparedStatement stmtRemoverVenda = conexao.prepareStatement(sqlRemoverVenda)) {

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

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, totalVenda);
            stmt.setInt(2, idVenda);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar total da venda: " + e.getMessage());
            return false;
        }
    }

    public ObservableList<Venda> buscarVendasPorPeriodo(String inicio, String fim) {
        ObservableList<Venda> vendas = FXCollections.observableArrayList();
        String sql = """
            SELECT idVenda, dataVenda, totalVenda, clienteNome
            FROM Venda
            WHERE dataVenda BETWEEN ? AND ?
            """;

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, inicio);
            stmt.setString(2, fim);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idVenda = rs.getInt("idVenda");
                    String dataVenda = rs.getString("dataVenda");
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

    public double calcularLucroBruto(String inicio, String fim) {
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
            System.out.println("Erro ao calcular o lucrobruto: " + e.getMessage());
        }

        return lucroBruto;
    }

}
