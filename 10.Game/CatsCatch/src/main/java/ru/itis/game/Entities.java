package ru.itis.game;

public enum Entities {
    FIRST_CAT(0),
    SECOND_CAT(1),
    BLOCK_1(105),
    BLOCK_2(106),
    BLOCK_3(107),
    BLOCK_4(108),
    BLOCK_5(109),
    BLOCK_6(110),
    LASER(2),
    FIRST_COUNT_LABEL(3),
    SECOND_COUNT_LABEL(4),
    ;

    public final int id;

    Entities(int id) {
        this.id = id;
    }
}
