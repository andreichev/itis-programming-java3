package ru.itis.game.network;

import ru.itis.gengine.network.model.NetworkComponentState;

public record CounterValue(int value) implements NetworkComponentState {}
