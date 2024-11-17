package controllers;

import database.Conexao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.AlertUtils;
import util.SceneManager;
import util.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @FXML
    private PasswordField pfSenha;
    @FXML
    private TextField tfUsuario;
    @FXML
    private Button btnLogin;

    @FXML
    void initialize() {
        btnLogin.setDisable(true);

        tfUsuario.textProperty().addListener((obs, oldText, newText) -> validarCampos());
        pfSenha.textProperty().addListener((obs, oldText, newText) -> validarCampos());
    }

    @FXML
    void acaoDoBotao(ActionEvent event) {
        AlertUtils alerta = new AlertUtils();
        try {
            String usuario = tfUsuario.getText().trim();
            String senha = pfSenha.getText().trim();

            if (usuario.isEmpty() || senha.isEmpty()) {
                alerta.warning("Campos obrigatórios", null, "Por favor, preencha todos os campos!", null);
                return;
            }

            if (verificarCredenciais(usuario, senha)) {
                String telaDestino = Session.isAdmin() ? "/view/TelaAdministrador.fxml" : "/view/TelaFuncionario.fxml";
                carregarTela(telaDestino);
            } else {
                alerta.error("Login", null, "Usuário ou senha incorretos!", null);
            }
        } catch (IOException e) {
            logger.severe("Erro ao carregar tela: " + e.getMessage());
            alerta.error("Falha no carregamento da Tela", null, e.getMessage(), null);
        }
    }

    private void carregarTela(String caminhoTela) throws IOException {
        Stage stage = (Stage) tfUsuario.getScene().getWindow();
        SceneManager.changeScene(stage, caminhoTela);
    }

    private boolean verificarCredenciais(String usuario, String senha) {
        String sql = "SELECT adm FROM Usuarios WHERE nome = ? AND senha = ?";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Session.setAdmin(rs.getBoolean("adm"));
                return true;
            }
        } catch (SQLException e) {
            logger.severe("Erro ao verificar credenciais: " + e.getMessage());
        }
        return false;
    }

    private void validarCampos() {
        btnLogin.setDisable(tfUsuario.getText().trim().isEmpty() || pfSenha.getText().trim().isEmpty());
    }
}