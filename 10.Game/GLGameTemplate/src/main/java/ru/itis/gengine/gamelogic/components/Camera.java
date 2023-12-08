package ru.itis.gengine.gamelogic.components;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.itis.gengine.base.GSize;
import ru.itis.gengine.events.WindowSizeListener;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.renderer.Shader;

public class Camera extends Component implements WindowSizeListener, TransformDelegate {
    private Vector4f target;
    private Matrix4f view;
    private Matrix4f projection;
    private Shader shader;
    private float fieldOfView;
    private GSize windowSize;
    private Transform transform;

    // MARK: - Overridden methods

    @Override
    public void initialize() {
        this.windowSize = getEntity().getWindow().getWindowSize();
        target = new Vector4f();
        view = new Matrix4f();
        projection = new Matrix4f();
        transform = getEntity().getTransform();
        getEntity().getEvents().addWindowSizeListener(this);
        getEntity().getTransform().addDelegate(this);
    }

    @Override
    public void terminate() {
        getEntity().getEvents().removeWindowSizeDelegate(this);
        getEntity().getTransform().removeDelegate(this);
    }

    // MARK: - Public methods

    public void setShader(Shader shader) {
        this.shader = shader;
        updateProjectionMatrix();
        updateViewMatrix();
    }

    public void setFieldOfView(float degrees) {
        fieldOfView = degrees;
    }

    // MARK: - Private methods

    private void updateProjectionMatrix() {
        projection.identity();
        projection.perspective(
                Math.toRadians(fieldOfView),
                windowSize.width / windowSize.height,
                0.1f, 1000.0f
        );
        shader.use();
        shader.setUniform("projection", projection);
    }

    private void updateViewMatrix() {
        // target = position + front
        Vector4f position = transform.getPosition();
        position.add(transform.getFront(), target);
        view.identity();
        view.lookAt(
                position.x, position.y, position.z,
                target.x, target.y, target.z,
                transform.getUp().x, transform.getUp().y, transform.getUp().z
        );
        shader.use();
        shader.setUniform("view", view);
    }

    // MARK: - WindowSizeListener

    @Override
    public void sizeChanged(GSize size) {
        windowSize = size;
        updateProjectionMatrix();
    }

    // MARK: - TransformDelegate

    @Override
    public void transformChanged(Vector4f position, Vector3f rotation) {
        updateViewMatrix();
    }
}
