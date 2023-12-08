package ru.itis.gengine.gamelogic.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.renderer.*;

public class Mesh extends Component implements TransformDelegate {
    private final Texture texture;
    private final Shader shader;
    private final Matrix4f model;
    private IndexBuffer indexBuffer;
    private VertexBuffer vertexBuffer;
    private Transform transform;
    private Renderer renderer;
    private final boolean isDynamic;

    // MARK: - Init

    public Mesh(MeshData primitiveMeshData, boolean isDynamic, Texture texture, Shader shader) {
        this.isDynamic = isDynamic;
        this.texture = texture;
        this.shader = shader;
        model = new Matrix4f();
        indexBuffer = new IndexBuffer(primitiveMeshData.getIndices(), isDynamic);
        vertexBuffer = new VertexBuffer(primitiveMeshData.getVertices(), isDynamic);
    }

    public Mesh(Vertex[] vertices, int[] indices, boolean isDynamic, Texture texture, Shader shader) {
        this.isDynamic = isDynamic;
        this.texture = texture;
        this.shader = shader;
        model = new Matrix4f();
        indexBuffer = new IndexBuffer(indices, isDynamic);
        vertexBuffer = new VertexBuffer(vertices, isDynamic);
    }

    // MARK: - Overridden methods

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        renderer = getEntity().getRenderer();
        getEntity().getTransform().addDelegate(this);
    }

    @Override
    public void update(float deltaTime) {
        shader.use();
        shader.setUniform("model", model);
        texture.bind();
        // Порядок биндингов для OpenGL важен
        vertexBuffer.bind();
        indexBuffer.bind();
        renderer.drawIndexed(indexBuffer.getSize());
        texture.unbind();
        indexBuffer.unbind();
        vertexBuffer.unbind();
    }

    @Override
    public void terminate() {
        texture.delete();
        indexBuffer.delete();
        vertexBuffer.delete();
        transform.removeDelegate(this);
    }

    // MARK: - Public methods

    public void updateBuffer(MeshData data) {
        if(isDynamic) {
            vertexBuffer.update(data.getVertices());
            indexBuffer.update(data.getIndices());
        } else {
            vertexBuffer.delete();
            indexBuffer.delete();
            vertexBuffer = new VertexBuffer(data.getVertices(), false);
            indexBuffer = new IndexBuffer(data.getIndices(), false);
        }
    }

    public void updateBuffer(Vertex[] vertices, int[] indices) {
        if(isDynamic) {
            vertexBuffer.update(vertices);
            indexBuffer.update(indices);
        } else {
            vertexBuffer.delete();
            indexBuffer.delete();
            vertexBuffer = new VertexBuffer(vertices, false);
            indexBuffer = new IndexBuffer(indices, false);
        }
    }

    // MARK: - Private methods

    private void updateModelMatrix() {
        model.identity();
        Vector4f position = transform.getPosition();
        model.translate(position.x, position.y, position.z);
        Vector3f rotation = transform.getRotation();
        model.rotate(rotation.x, 1.f, 0.f, 0.f);
        model.rotate(rotation.y, 0.f, 1.f, 0.f);
        model.rotate(rotation.z, 0.f, 0.f, 1.f);
    }

    // MARK: - TransformDelegate

    @Override
    public void transformChanged(Vector4f position, Vector3f rotation) {
        updateModelMatrix();
    }
}
