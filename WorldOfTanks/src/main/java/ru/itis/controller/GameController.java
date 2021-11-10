package ru.itis.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import ru.itis.base.Direction;
import ru.itis.base.Game;
import ru.itis.gameobjects.Tank;
import ru.itis.gameobjects.Wall;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Button helloButton;

    @FXML
    private Label helloLabel;

    @FXML
    public AnchorPane pane;

    @FXML
    private void startButtonTapped(ActionEvent event) {
        pane.getChildren().remove(helloButton);
        helloLabel.setText("ПОГНАЛИ Ё-МАЁ!!!");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), animation -> {
            pane.getChildren().remove(helloLabel);
            startGame();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void startGame() {
        Tank tank1 = new Tank("Player1", 1200, 700, "/images/tank1.png", true, Direction.LEFT);
         Tank tank2 = new Tank("Player2", 100, 100, "/images/tank2.png", false, Direction.RIGHT);
        Game.instance.addGameObject(tank1);
        Game.instance.addGameObject(tank2);
        double value;
        for (int i = 1; i < 10; i++) {
            if (i % 2 == 0) {
                value = 150;
            } else {
                value = -150;
            }
            Wall wall = new Wall("wall", 600 + value, i * 100 - 100);
            Game.instance.addGameObject(wall);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView imageView = new ImageView();
//        imageView.imageProperty().set(new Image(getClass().getResourceAsStream("/images/background.jpg")));
        pane.getChildren().add(imageView);
        imageView.setFitWidth(pane.getWidth());
        imageView.setFitHeight(pane.getHeight());
        Game.instance.setGamingPane(pane);
    }
}

