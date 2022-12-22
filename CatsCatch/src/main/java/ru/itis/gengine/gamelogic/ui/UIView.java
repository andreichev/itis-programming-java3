package ru.itis.gengine.gamelogic.ui;

import ru.itis.gengine.base.GRect;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.VertexBuffer;
import ru.itis.gengine.renderer.VertexBufferLayout;

public class UIView extends UINode {
    // TODO: - Один шейдер на все вьюхи
    private final Shader shader;
    private final VertexBuffer vertexBuffer;
    private GRect frame;

    public UIView() {
        this(GRect.zero());
    }

    public UIView(GRect frame) {
        this.frame = frame;
        float [] data = new float[12];
        shader = new Shader(
                "resources/shaders/ui/uiview/uiview_vertex.glsl",
                "resources/shaders/ui/uiview/uiview_fragment.glsl"
        );
        VertexBufferLayout layout = new VertexBufferLayout();
        layout.pushFloat(2);
        vertexBuffer = new VertexBuffer(data, true, layout);
    }

    public GRect getFrame() {
        return frame;
    }

    public void setFrame(GRect frame) {
        this.frame = frame;
        layout();
    }

    @Override
    public void layout() {
        float leftEdge = frame.origin.x / windowSize.width;
        float rightEdge = (frame.origin.x + frame.size.width) / windowSize.width;
        float topEdge = 1f - frame.origin.y / windowSize.height;
        float bottomEdge = 1f - (frame.origin.y + frame.size.height) / windowSize.height;
        float[] data = new float[] {
            rightEdge, topEdge,
            leftEdge, topEdge,
            leftEdge, bottomEdge,
            rightEdge, topEdge,
            leftEdge, bottomEdge,
            rightEdge, bottomEdge
        };
        for(int i = 0; i < data.length; i++) {
            data[i] = data[i] * 2 - 1;
        }
        vertexBuffer.update(data);
    }

    @Override
    public void draw() {
        shader.use();
        vertexBuffer.bind();
        renderer.draw(6);
    }

    @Override
    public void terminate() {
        vertexBuffer.delete();
        shader.delete();
    }
}
