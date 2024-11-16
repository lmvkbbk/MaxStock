package DAO;

import models.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class ClienteDAO {

    private static final String DATABASE_URL = "jdbc:sqlite:Banco.db";

    public ObservableList<Cliente> pesquisarClientes(String pesquisa) {
        String sql = "SELECT id, nome, cpf, data_nascimento, email, telefone FROM Cliente " +
                "WHERE nome LIKE ? OR cpf LIKE ? OR email LIKE ? OR telefone LIKE ?";
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, "%" + pesquisa + "%");
            stmt.setString(2, "%" + pesquisa + "%");
            stmt.setString(3, "%" + pesquisa + "%");
            stmt.setString(4, "%" + pesquisa + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setDataNascimento(rs.getString("data_nascimento"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));

                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao realizar a pesquisa de cliente: " + e.getMessage());
        }
        return clientes;
    }

    public boolean cadastrarCliente(String nome, String cpf, LocalDate dataNascimento, String email, String telefone) {
        String sql = "INSERT INTO Cliente (nome, cpf, data_nascimento, email, telefone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            System.out.println("Data de Nascimento: " + Date.valueOf(dataNascimento));

            statement.setString(1, nome);
            statement.setString(2, cpf);
            statement.setString(3, String.valueOf(Date.valueOf(dataNascimento)));
            statement.setString(4, email);
            statement.setString(5, telefone);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean removerCliente(int clienteId) {
        String sql = "DELETE FROM Cliente WHERE id = ?";

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, clienteId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao remover cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarCliente(Cliente cliente) {
        String sql = "UPDATE Cliente SET nome = ?, email = ?, telefone = ?, cpf = ?, data_nascimento = ? WHERE id = ?";

        try (Connection conexao = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getTelefone());
            statement.setString(4, cliente.getCpf());
            statement.setString(5, String.valueOf(cliente.getDataNascimento()));
            statement.setInt(6, cliente.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }
}