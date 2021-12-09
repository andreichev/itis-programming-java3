package ru.itits.fxexample.game.objects;

import javafx.scene.image.Image;
import ru.itits.fxexample.engine.Entity;

public class Block extends Entity {
    public Block(int id, float x, float y, float width, float height) {
        super(id, true);
        setFitHeight(height);
        setFitWidth(width);
        setLayoutX(x);
        setLayoutY(y);

        Image image = new Image(getClass().getResourceAsStream("/images/block.png"));
        imageProperty().set(image);
    }
}
