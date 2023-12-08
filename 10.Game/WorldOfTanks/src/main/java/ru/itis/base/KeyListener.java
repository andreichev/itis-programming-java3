package ru.itis.base;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyListener {

    public static KeyListener instance = new KeyListener();

    private KeyListener() {}

    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;
    public boolean w = false;
    public boolean s = false;
    public boolean a = false;
    public boolean d = false;
    public boolean r = false;
    public boolean m = false;

    public EventHandler<KeyEvent> onKeyPressed = event -> {
        switch (event.getCode()) {
            case UP: up = true; break;
            case DOWN: down = true; break;
            case LEFT: left = true; break;
            case RIGHT: right = true; break;
            case W: w = true; break;
            case S: s = true; break;
            case A: a = true; break;
            case D: d = true; break;
            case R: r = true; break;
            case M: m = true; break;
            case ESCAPE: Runtime.getRuntime().exit(0);
        }
    };

    public EventHandler<KeyEvent> onKeyReleased = event -> {
        switch (event.getCode()) {
            case UP: up = false; break;
            case DOWN: down = false; break;
            case LEFT: left = false; break;
            case RIGHT: right = false; break;
            case W: w = false; break;
            case S: s = false; break;
            case A: a = false; break;
            case D: d = false; break;
            case R: r = false; break;
            case M: m = false; break;
        }
    };

    public boolean up(boolean isFirstPlayer) {
        if (isFirstPlayer) {
            return up;
        } else {
            return w;
        }
    }

    public boolean down(boolean isFirstPlayer) {
        if (isFirstPlayer) {
            return down;
        } else {
            return s;
        }
    }

    public boolean left(boolean isFirstPlayer) {
        if (isFirstPlayer) {
            return left;
        } else {
            return a;
        }
    }

    public boolean right(boolean isFirstPlayer) {
        if (isFirstPlayer) {
            return right;
        } else {
            return d;

        }
    }

    public boolean fire(boolean isFirstPlayer) {
        if (isFirstPlayer) {
            return m;
        } else {
            return r;
        }
    }
}