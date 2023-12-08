package ru.itits.fxexample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.itits.fxexample.application.JavaFxApplication;
import ru.itits.fxexample.engine.network.Server;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    private void startServerButtonTapped(ActionEvent event) throws IOException {
        GameController gameController = startGame();
        Server server = new Server();
        JavaFxApplication.getInstance().setServer(server);
        Socket socket = new Socket("127.0.0.1", 16431);
        gameController.initializeGame(socket);
    }

    @FXML
    private void connectButtonTapped(ActionEvent event) throws IOException {
        GameController gameController = startGame();
        Socket socket = new Socket("127.0.0.1", 16431);
        gameController.initializeGame(socket);
    }

    @FXML
    private void exitButtonTapped(ActionEvent event) {
        System.exit(0);
    }

    private GameController startGame() throws IOException {
        String fxmlFile = "/fxml/Game.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        fxmlLoader.load();
        GameController gameController = fxmlLoader.getController();

        Scene scene = new Scene(gameController.pane);
        JavaFxApplication.getInstance().getInput().listenScene(scene);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);

        return gameController;
    }
}
