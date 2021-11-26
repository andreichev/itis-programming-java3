package ru.itits.fxexample.game.objects;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import ru.itits.fxexample.application.JavaFxApplication;
import ru.itits.fxexample.engine.Entity;
import ru.itits.fxexample.engine.Event;
import ru.itits.fxexample.engine.Replicable;
import ru.itits.fxexample.engine.World;
import ru.itits.fxexample.events.Events;

public class Rect extends Entity implements Replicable {
    private final Events events;
    private final World world;
    private final float speed = 100.f;

    public Rect(int id, float x, float y) {
        super(id,true);
        Image image = new Image(getClass().getResourceAsStream("/images/kama.png"));
        imageProperty().set(image);
        setFitHeight(100);
        setFitWidth(100);
        setLayoutX(x);
        setLayoutY(y);
        events = JavaFxApplication.getInstance().getEvents();
        world = JavaFxApplication.getInstance().getWorld();
    }

    @Override
    public void update(float deltaTime) {
        boolean playerMoved = false;
        int[] data = new int[10];
        if(events.isKeyPressed(KeyCode.W)) {
            data[1] = (int) (getLayoutY() - deltaTime * speed);
            playerMoved = true;
        }
        if(events.isKeyPressed(KeyCode.S)) {
            data[1] = (int) (getLayoutY() + deltaTime * speed);
            playerMoved = true;
        }
        if(events.isKeyPressed(KeyCode.A)) {
            data[0] = (int) (getLayoutX() - deltaTime * speed);
            playerMoved = true;
        }
        if(events.isKeyPressed(KeyCode.D)) {
            data[0] = (int) (getLayoutX() + deltaTime * speed);
            playerMoved = true;
        }
        if(playerMoved) {
            world.addEvent(new Event(1, id, data));
        }
    }

    @Override
    public void updateState(Event event) {
        if(event.type == 1) {
            int x = event.data[0];
            int y = event.data[1];
            setLayoutX(x);
            setLayoutY(y);
        }
    }
}
