package ru.itits.fxexample.game.objects;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import ru.itits.fxexample.application.JavaFxApplication;
import ru.itits.fxexample.engine.*;
import ru.itits.fxexample.engine.network.NetworkEvent;
import ru.itits.fxexample.game.network.NetworkEventType;
import ru.itits.fxexample.input.Input;

public class KamaPlayer extends Entity implements Replicable {
    private final Input input;
    private final World world;
    private final boolean currentPlayer;
    private final float moveSpeed = 250.f;
    private final float gravity = 800f;
    private final float jumpForce = 400f;
    private boolean isGrounded = false;
    private float verticalForce = 0f;

    public KamaPlayer(int id, double x, double y, boolean currentPlayer) {
        super(id);
        this.currentPlayer = currentPlayer;
        Image image = new Image(getClass().getResourceAsStream("/images/kama.png"));
        imageProperty().set(image);
        setFitHeight(100);
        setFitWidth(70);
        setLayoutX(x);
        setLayoutY(y);
        input = JavaFxApplication.getInstance().getInput();
        world = JavaFxApplication.getInstance().getWorld();
    }

    @Override
    public void update(float deltaTime) {
        if(currentPlayer == false) { return; }
        boolean playerMoved = false;
        isGrounded = false;

        verticalForce -= gravity * deltaTime;
        float verticalSpeed = - deltaTime * verticalForce;
        float horizontalSpeed = deltaTime * moveSpeed;

        if(Math.abs(verticalForce) > 10f) {
            if(verticalForce > 0f) {
                if(CollisionDetector.moveAcceptable(this, Direction.UP, verticalSpeed)) {
                    setLayoutY(getLayoutY() + verticalSpeed);
                    playerMoved = true;
                } else {
                    verticalForce = 0f;
                }
            } else {
                if(CollisionDetector.moveAcceptable(this, Direction.DOWN, verticalSpeed)) {
                    setLayoutY(getLayoutY() + verticalSpeed);
                    playerMoved = true;
                } else {
                    verticalForce = 0f;
                    isGrounded = true;
                }
            }
        }

        if(input.isKeyPressed(KeyCode.SPACE) && isGrounded) {
            verticalForce = jumpForce;
        }

        if(input.isKeyPressed(KeyCode.A) && CollisionDetector.moveAcceptable(this, Direction.LEFT, horizontalSpeed)) {
            setLayoutX(getLayoutX() - horizontalSpeed);
            playerMoved = true;
        }
        if(input.isKeyPressed(KeyCode.D) && CollisionDetector.moveAcceptable(this, Direction.RIGHT, horizontalSpeed)) {
            setLayoutX(getLayoutX() + horizontalSpeed);
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
