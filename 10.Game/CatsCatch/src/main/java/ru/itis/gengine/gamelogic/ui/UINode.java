package ru.itis.gengine.gamelogic.ui;

import ru.itis.gengine.base.GSize;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.WindowSizeListener;
import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.window.Window;

import java.util.ArrayList;
import java.util.List;

public class UINode implements WindowSizeListener {
    private final List<UINode> subnodes;
    protected Window window;
    protected Events events;
    protected Renderer renderer;
    protected GSize windowSize;
    protected boolean isConfigured;

    public UINode() {
        this.subnodes = new ArrayList<>();
        this.isConfigured = false;
    }

    // MARK: - Public methods

    public void configure(Renderer renderer, Window window, Events events) {
        if (isConfigured) { return; }
        isConfigured = true;
        this.renderer = renderer;
        this.window = window;
        this.events = events;
        windowSize = window.getWindowSize();
        events.addWindowSizeListener(this);
        for(UINode node: subnodes) {
            node.configure(renderer, window, events);
        }
        layout();
    }

    public void render() {
        if(renderer == null) { return; }
        draw();
        for(UINode node: subnodes) {
            node.render();
        }
    }

    public void addSubnode(UINode node) {
        subnodes.add(node);
        if(isConfigured) {
            node.configure(renderer, window, events);
        }
    }

    public void removeSubnode(UINode node) {
        if(subnodes.remove(node)) {
            node.terminate();
        }
    }

    public List<UINode> getSubnodes() {
        return subnodes;
    }

    // MARK: - Need to be overriden

    // Вызывается для позиционирования (обновление буфера)
    public void layout() {}
    // Вызывается для drawCall. Batching пока не настроен
    public void draw() {}
    // Выгрузка буфера, шейдера
    public void terminate() {}

    // MARK: - WindowSizeListener

    @Override
    public void sizeChanged(GSize size) {
        windowSize = size;
        layout();
    }
}
