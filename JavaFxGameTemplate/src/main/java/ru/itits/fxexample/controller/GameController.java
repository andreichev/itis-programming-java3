package ru.itits.fxexample.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import ru.itits.fxexample.application.JavaFxApplication;
import ru.itits.fxexample.engine.World;
import ru.itits.fxexample.engine.network.NetworkEvent;
import ru.itits.fxexample.game.levels.Level1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    Pane pane;

    private Socket socket;

    private World world;

    private double lastFrameTime;
    private int estimateFPS;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void initializeGame(Socket socket) {
        this.socket = socket;
        JavaFxApplication application = JavaFxApplication.getInstance();
        world = application.getWorld();
        world.setPane(pane);
        startTimer();
        application.loadLevel(new Level1());
        communicateWithServer();
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

    private void communicateWithServer() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    List<NetworkEvent> events = world.pollEvents();
                    for(NetworkEvent event: events) {
                        NetworkEvent.writeEvent(event, dataOutputStream);
                    }
                    dataOutputStream.writeInt(NetworkEvent.END);
                    NetworkEvent event;
                    while ((event = NetworkEvent.readEvent(dataInputStream)) != null) {
                        world.processEventFromServer(event);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
