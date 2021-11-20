package ru.itits.fxexample.controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import ru.itits.fxexample.application.JavaFxApplication;
import ru.itits.fxexample.events.Events;
import ru.itits.fxexample.engine.World;
import ru.itits.fxexample.game.levels.Level1;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    Pane pane;

    boolean isServer = false;
    Socket socket;

    private Events events;
    private World world;

    private double lastFrameTime;
    private int estimateFPS;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        events = JavaFxApplication.getInstance().getEvents();
        world = JavaFxApplication.getInstance().getWorld();
        world.setPane(pane);
        startTimer();
        Level1.initialize(world);
    }

    private void startTimer() {
        lastFrameTime = System.currentTimeMillis() / 1.0E3;
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        animationTimer.start();
    }

    private void update() {
        double time = System.currentTimeMillis() / 1.0E3;
        float deltaTime = (float) (time - lastFrameTime);
        estimateFPS = (int) (1f / deltaTime);
        lastFrameTime = time;
        world.update(deltaTime);
    }
}
