package ru.itits.fxexample.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class Input {
    private final boolean[] keys;

    public Input() {
        this.keys = new boolean[1032];
    }

    public void listenScene(Scene scene) {
        scene.setOnKeyPressed(event -> {
            int key = event.getCode().getCode();
            if(key >= keys.length) {
                return;
            }
            keys[key] = true;
        });
        scene.setOnKeyReleased(event -> {
            int key = event.getCode().getCode();
            if(key >= keys.length) {
                return;
            }
            keys[key] = false;
        });
    }

    public boolean isKeyPressed(KeyCode key) {
        return keys[key.getCode()];
    }
}
