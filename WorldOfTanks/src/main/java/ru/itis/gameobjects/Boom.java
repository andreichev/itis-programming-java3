package ru.itis.gameobjects;

import ru.itis.base.Game;
import ru.itis.base.GameObject;
import javafx.scene.image.Image;

public class Boom extends GameObject {

    private long initializedTime = 0;

    public Boom(double x, double y) {
        super("boom");
        isCollision = false;
        Image image = new Image(getClass().getResourceAsStream("/images/boom.png"));
        imageProperty().set(image);
        setFitHeight(200);
        setFitWidth(200);
        setLayoutX(x - 100);
        setLayoutY(y - 100);
        initializedTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - initializedTime > 1500) {
            Game.instance.removeGameObject(this);
        }
    }
}
