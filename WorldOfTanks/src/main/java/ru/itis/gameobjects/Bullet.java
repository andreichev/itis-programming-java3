package ru.itis.gameobjects;

import ru.itis.base.Direction;
import ru.itis.base.Game;
import ru.itis.base.GameObject;
import ru.itis.gameobjects.common.Destroyable;
import javafx.scene.image.Image;

import java.util.List;

public class Bullet extends GameObject {

    long bulletLifeTime = 0;
    private Direction direction;
    private GameObject sender;

    public Bullet(double x, double y, Direction direction, GameObject sender) {
        super("bullet");
        isCollision = false;
        bulletLifeTime = System.currentTimeMillis();
        Image image = new Image(getClass().getResourceAsStream("/images/kama.jpg"));
        imageProperty().set(image);
        setFitHeight(50);
        setFitWidth(50);
        setLayoutX(x - 25);
        setLayoutY(y - 25);
        this.direction = direction;
        this.sender = sender;
    }

    @Override
    public void update() {
        updateBulletDirection();
        if (System.currentTimeMillis() - bulletLifeTime > 3000) {
            Game.instance.removeGameObject(this);
        }
        List<GameObject> destroyableList = Game.instance.getAllDestroyable();
        for (GameObject gameObject : destroyableList) {
            if (gameObject.equals(sender)) {
                continue;
            }
            if (getBoundsInParent().intersects(gameObject.getBoundsInParent())) {
                Destroyable destroyable = (Destroyable) gameObject;
                destroyable.destroy();
                Game.instance.removeGameObject(this);
            }
        }

    }

    private void updateBulletDirection() {
        switch (direction) {
            case UP: setLayoutY(getLayoutY() - 10); break;
            case DOWN: setLayoutY(getLayoutY() + 10); break;
            case RIGHT: setLayoutX(getLayoutX() + 10); break;
            case LEFT: setLayoutX(getLayoutX() - 10); break;
        }
    }
}
