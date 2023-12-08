package ru.itis.gengine.events;

public enum MouseButton {
    BUTTON_1(0),
    BUTTON_2(1),
    BUTTON_3(2),
    BUTTON_4(3),
    BUTTON_5(4),
    BUTTON_6(5),
    BUTTON_7(6),
    BUTTON_8(7),
    LAST(7),
    LEFT(0),
    RIGHT(1),
    MIDDLE(2)
    ;

    public int code;

    MouseButton(int code) {
        this.code = code;
    }
}
