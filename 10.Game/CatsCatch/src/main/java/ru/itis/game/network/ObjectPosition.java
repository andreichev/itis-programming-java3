package ru.itis.game.network;

import ru.itis.gengine.network.model.NetworkComponentState;

public record ObjectPosition(float x, float y) implements NetworkComponentState {}
