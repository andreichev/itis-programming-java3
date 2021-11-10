package ru.itis.gameobjects;

import ru.itis.base.Game;
import ru.itis.base.GameObject;
import ru.itis.gameobjects.common.Destroyable;
import javafx.scene.image.Image;

public class Wall extends GameObject implements Destroyable {

    private int health = 2;
    private Image destoyedImage;

    public Wall(String key, double x, double y) {
        super(key);
        Image image = new Image(getClass().getResourceAsStream("/images/wall.png"));
        destoyedImage = new Image(getClass().getResourceAsStream("/images/destroyedWall.png"));
        imageProperty().set(image);
        setFitHeight(100);
        setFitWidth(100);
        setLayoutX(x);
        setLayoutY(y);
    }

    @Override
    public void update() {}

    @Override
    public void destroy() {
        health --;
        if (health > 0) {
            imageProperty().set(destoyedImage);
            return;
        }
        Boom boom = new Boom(getBoundsInParent().getCenterX(), getBoundsInParent().getCenterY());
        Game.instance.addGameObject(boom);
        Game.instance.removeGameObject(this);
    }
}
