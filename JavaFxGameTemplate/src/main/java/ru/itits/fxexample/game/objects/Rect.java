package ru.itits.fxexample.game.objects;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import ru.itits.fxexample.application.JavaFxApplication;
import ru.itits.fxexample.engine.Entity;
import ru.itits.fxexample.engine.Replicable;
import ru.itits.fxexample.engine.World;
import ru.itits.fxexample.engine.network.NetworkEvent;
import ru.itits.fxexample.game.network.NetworkEventType;
import ru.itits.fxexample.input.Input;

public class Rect extends Entity implements Replicable {
    private final Input input;
    private final World world;
    private final float speed = 100.f;
    private final boolean currentPlayer;

    public Rect(int id, double x, double y, boolean currentPlayer) {
        super(id);
        this.currentPlayer = currentPlayer;
        Image image = new Image(getClass().getResourceAsStream("/images/kama.png"));
        imageProperty().set(image);
        setFitHeight(100);
        setFitWidth(100);
        setLayoutX(x);
        setLayoutY(y);
        input = JavaFxApplication.getInstance().getInput();
        world = JavaFxApplication.getInstance().getWorld();
    }

    @Override
    public void update(float deltaTime) {
        if(currentPlayer == false) { return; }
        boolean playerMoved = false;
        if(input.isKeyPressed(KeyCode.W)) {
            setLayoutY(getLayoutY() - deltaTime * speed);
            playerMoved = true;
        }
        if(input.isKeyPressed(KeyCode.S)) {
            setLayoutY(getLayoutY() + deltaTime * speed);
            playerMoved = true;
        }
        if(input.isKeyPressed(KeyCode.A)) {
            setLayoutX(getLayoutX() - deltaTime * speed);
            playerMoved = true;
        }
        if(input.isKeyPressed(KeyCode.D)) {
            setLayoutX(getLayoutX() + deltaTime * speed);
            playerMoved = true;
        }
        if(playerMoved) {
            double[] data = new double[10];
            data[0] = getLayoutX();
            data[1] = getLayoutY();
            world.addEventToQueue(new NetworkEvent(NetworkEventType.PLAYER_MOVED.value, id, data));
        }
    }

    @Override
    public void updateState(NetworkEvent event) {
        if(currentPlayer) { return; }
        if(event.type == NetworkEventType.PLAYER_MOVED.value) {
            setLayoutX(event.data[0]);
            setLayoutY(event.data[1]);
        }
    }
}
