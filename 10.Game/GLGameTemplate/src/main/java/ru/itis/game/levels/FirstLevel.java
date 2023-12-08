package ru.itis.game.levels;

import ru.itis.game.components.CameraMove;
import ru.itis.game.components.CubeCreator;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Camera;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.gamelogic.primitives.Primitives;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;

public class FirstLevel extends LevelBase {
    Shader baseShader;

    @Override
    public void start(World world) {
        world.getRenderer().setClearColor(0.07f, 0.13f, 0.17f, 1.0f);
        baseShader = new Shader(
                "resources/shaders/vertex_shader.glsl",
                "resources/shaders/fragment_shader.glsl"
        );

        Entity playerEntity = world.instantiateEntity();
        Camera camera = new Camera();
        playerEntity.addComponent(camera);
        camera.setFieldOfView(60.f);
        camera.setShader(baseShader);
        playerEntity.getTransform().setPosition(0.f, 4.f, 5.f);
        playerEntity.getTransform().rotate((float) (Math.PI / 4.f), 0.f, 0.f);
        playerEntity.addComponent(new CameraMove());

        Texture texture = new Texture("resources/textures/block.png");
        MeshData meshData = Primitives.createCube(2.0f);
        Mesh mesh = new Mesh(meshData, false, texture, baseShader);
        Entity blockEntity = world.instantiateEntity();
        blockEntity.addComponent(mesh);

        CubeCreator cubeCreator = new CubeCreator();
        cubeCreator.setShader(baseShader);
        cubeCreator.setTexture(texture);
        playerEntity.addComponent(cubeCreator);
    }

    @Override
    public void terminate() {
        baseShader.delete();
    }
}
