package ru.itits.fxexample.game.network;

public enum NetworkEventType {
    PLAYER_MOVED(1),
    PLAYER_CONNECTED(2)
    ;

    public final int value;

    NetworkEventType(int value) {
        this.value = value;
    }
}
