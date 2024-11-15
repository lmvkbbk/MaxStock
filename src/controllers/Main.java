package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import database.*;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        BancoDeDados.createDatabaseAndTables();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/TelaLogin.fxml")));

        Image icon = new Image(getClass().getResourceAsStream("/Resources/MaxStockLogo.png"));

        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("MaxStock");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}