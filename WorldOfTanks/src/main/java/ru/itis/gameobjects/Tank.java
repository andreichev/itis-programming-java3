package ru.itis.gameobjects;

import ru.itis.Main;
import ru.itis.base.Direction;
import ru.itis.base.Game;
import ru.itis.base.GameObject;
import ru.itis.base.KeyListener;
import ru.itis.gameobjects.common.Destroyable;
import ru.itis.utills.SceneEndChecker;
import javafx.scene.image.Image;
import ru.itis.gameobjects.common.Destroyable;


public class Tank extends GameObject implements Destroyable {

    public Tank(String key, double x, double y, String imagePath, boolean isFirstPlayer, Direction direction) {
        super(key);
        Image image = new Image(Main.class.getResourceAsStream(imagePath));
        imageProperty().set(image);
        setFitHeight(120);
        setFitWidth(120);
        setLayoutX(x);
        setLayoutY(y);
        this.direction = direction;
        updateTankDirection();
        this.isFirstPlayer = isFirstPlayer;
    }

    private long lastShotTime;
    private Direction direction;
    private boolean isFirstPlayer;

    public boolean getIsFirstPlayer() {
        return isFirstPlayer;
    }

    @Override
    public void update() {
        if(KeyListener.instance.up(isFirstPlayer)) {
            direction = Direction.UP;
            if (SceneEndChecker.moveAcceptable(this, Direction.UP)) {
                setLayoutY(getLayoutY() - 3);
            }
        } else if(KeyListener.instance.down(isFirstPlayer)) {
            direction = Direction.DOWN;
            if (SceneEndChecker.moveAcceptable(this, Direction.DOWN)) {
                setLayoutY(getLayoutY() + 3);
            }
        } else if(KeyListener.instance.left(isFirstPlayer)) {
            direction = Direction.LEFT;
            if (SceneEndChecker.moveAcceptable(this, Direction.LEFT)) {
                setLayoutX(getLayoutX() - 3);
            }
        } else if(KeyListener.instance.right(isFirstPlayer)) {
            direction = Direction.RIGHT;
            if (SceneEndChecker.moveAcceptable(this, Direction.RIGHT)) {
                setLayoutX(getLayoutX() + 3);
            }
        }
        if(KeyListener.instance.fire(isFirstPlayer)) {
            fire();
        }
        updateTankDirection();
    }

    void fire() {
        if (System.currentTimeMillis() - lastShotTime > 1000) {
            lastShotTime = System.currentTimeMillis();
            Bullet bullet = new Bullet(getBoundsInParent().getCenterX(), getBoundsInParent().getCenterY(), direction, this);

            Game.instance.addGameObject(bullet);
        }
    }

    void updateTankDirection() {
        switch (direction) {
            case UP: setRotate(-90); break;
            case DOWN: setRotate(90); break;
            case RIGHT: setRotate(0); break;
            case LEFT: setRotate(180); break;
        }
    }

    @Override
    public void destroy() {
        Boom boom = new Boom(getBoundsInParent().getCenterX(), getBoundsInParent().getCenterY());
        Game.instance.addGameObject(boom);
        Game.instance.removeGameObject(this);
    }
}
