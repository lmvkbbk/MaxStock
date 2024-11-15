package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Produto;
import database.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDAO {

    public ObservableList<Produto> pesquisarProdutos(String pesquisa) {
        String sql = "SELECT id, nome, categoria, quantidade, custo, valor FROM Produto " +
                "WHERE nome LIKE ? OR categoria LIKE ? OR quantidade = ?";
        ObservableList<Produto> produtos = FXCollections.observableArrayList();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + pesquisa + "%");
            stmt.setString(2, "%" + pesquisa + "%");

            if (pesquisa.matches("\\d+")) {
                stmt.setInt(3, Integer.parseInt(pesquisa));
            } else {
                stmt.setInt(3, -1);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setCusto(rs.getDouble("custo"));
                produto.setValor(rs.getDouble("valor"));

                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar produtos: " + e.getMessage());
        }

        return produtos;
    }

    public boolean cadastrarProduto(Produto produto) {
        String sql = "INSERT INTO Produto (nome, categoria, quantidade, custo, valor) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = Conexao.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getCategoria());
            statement.setInt(3, produto.getQuantidade());
            statement.setDouble(4, produto.getCusto());
            statement.setDouble(5, produto.getValor());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar o produto: " + e.getMessage());
            return false;
        }
    }

    public boolean removerProduto(int produtoId) {
        String sql = "DELETE FROM Produto WHERE id = ?";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, produtoId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao remover produto: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarProduto(Produto produto) {
        String sql = "UPDATE Produto SET nome = ?, categoria = ?, quantidade = ?, custo = ?, valor = ? WHERE id = ?";
        try (Connection connection = Conexao.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getCategoria());
            statement.setInt(3, produto.getQuantidade());
            statement.setDouble(4, produto.getCusto());
            statement.setDouble(5, produto.getValor());
            statement.setInt(6, produto.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o produto: " + e.getMessage());
            return false;
        }
    }

    public ObservableList<Produto> pesquisarProdutosVenda(String pesquisa) {
        String sql = "SELECT id, nome, categoria, quantidade, custo, valor FROM Produto " +
                "WHERE nome LIKE ? OR quantidade = ? OR valor = ?";
        ObservableList<Produto> produtos = FXCollections.observableArrayList();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + pesquisa + "%");

            if (pesquisa.matches("\\d+")) {
                stmt.setInt(2, Integer.parseInt(pesquisa));
                stmt.setDouble(3, -1.0);

            } else if (pesquisa.matches("\\d+\\.\\d+")) {
                stmt.setInt(2, -1);
                stmt.setDouble(3, Double.parseDouble(pesquisa));

            } else {
                stmt.setInt(2, -1);
                stmt.setDouble(3, -1.0);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setCusto(rs.getDouble("custo"));
                produto.setValor(rs.getDouble("valor"));

                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar produtos: " + e.getMessage());
        }

        return produtos;
    }

    public boolean atualizarQuantidadeProduto(int idProduto, int quantidadeVendida) {
        String sql = "UPDATE Produto SET quantidade = quantidade - ? WHERE id = ?";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, quantidadeVendida);
            stmt.setInt(2, idProduto);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar a quantidade do produto: " + ex.getMessage());
            return false;
        }
    }

    public ObservableList<Produto> buscarProdutosComQuantidadeBaixo(int min, int max ) {
        ObservableList<Produto> produtos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Produto WHERE quantidade > ? AND quantidade <= ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, min);
            stmt.setInt(2, max);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setValor(rs.getDouble("valor"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public ObservableList<Produto> buscarProdutosComEstoqueZerado(int quantidade) {
        ObservableList<Produto> produtos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Produto WHERE quantidade <= ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantidade);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setValor(rs.getDouble("valor"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

}
