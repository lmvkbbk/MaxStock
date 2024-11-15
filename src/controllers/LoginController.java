package controllers;

import database.Conexao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.AlertUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private boolean tipoUsuario;

    @FXML
    private PasswordField pfSenha;
    @FXML
    private TextField tfUsuario;

    @FXML
    void acaoDoBotao(ActionEvent event) {
        AlertUtils alerta = new AlertUtils();
        try {
            String usuario = tfUsuario.getText();
            String senha = pfSenha.getText();

            if (verificarCredenciais(usuario, senha)) {
                String telaDestino = tipoUsuario ? "/view/TelaAdministrador.fxml" : "/view/TelaFuncionario.fxml";
                carregarTela(telaDestino);
            } else {
                alerta.error(null,"Usuário ou senha incorretos!", null, null);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            alerta.error("Falha no carregamento da Tela", null, e.getMessage(), null);
        }
    }

    private void carregarTela(String caminhoTela) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(caminhoTela));
        Stage stage = (Stage) tfUsuario.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private boolean verificarCredenciais(String usuario, String senha) {
        String sql = "SELECT adm FROM Usuarios WHERE nome = ? AND senha = ?";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tipoUsuario = rs.getBoolean("adm");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar credenciais: " + e.getMessage());
        }
        return false;
    }
}
