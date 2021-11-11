package ru.itits.fxexample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private void startServerButtonTapped(ActionEvent event) {

    }

    @FXML
    private void connectButtonTapped(ActionEvent event) {
        
    }

    @FXML
    private void exitButtonTapped(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
