package ru.itits.fxexample.game.objects;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import ru.itits.fxexample.Main;
import ru.itits.fxexample.application.JavaFxApplication;
import ru.itits.fxexample.engine.Entity;
import ru.itits.fxexample.events.Events;

public class Rect extends Entity {
    private final Events events;
    private final float speed = 100.f;

    public Rect(float x, float y) {
        Image image = new Image(getClass().getResourceAsStream("/images/kama.png"));
        imageProperty().set(image);
        setFitHeight(100);
        setFitWidth(100);
        setLayoutX(x);
        setLayoutY(y);
        events = JavaFxApplication.getInstance().getEvents();
    }

    @Override
    public void update(float deltaTime) {
        if(events.isKeyPressed(KeyCode.W)) {
            setLayoutY(getLayoutY() - deltaTime * speed);
        }
        if(events.isKeyPressed(KeyCode.S)) {
            setLayoutY(getLayoutY() + deltaTime * speed);
        }
        if(events.isKeyPressed(KeyCode.A)) {
            setLayoutX(getLayoutX() - deltaTime * speed);
        }
        if(events.isKeyPressed(KeyCode.D)) {
            setLayoutX(getLayoutX() + deltaTime * speed);
        }
    }
}
