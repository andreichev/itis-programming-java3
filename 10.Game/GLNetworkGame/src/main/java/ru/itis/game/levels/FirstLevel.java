package ru.itis.game.levels;

import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Camera;
import ru.itis.gengine.network.model.NetworkEntity;
import ru.itis.gengine.renderer.Shader;


public class FirstLevel extends LevelBase {
    Shader baseShader;

    @Override
    public void startServer(World world) {
        System.out.println("SERVER LEVEL START!");
        world.getRenderer().setClearColor(0.65f, 0.75f, 0.81f, 1.0f);
        baseShader = new Shader(
                "resources/shaders/vertex_shader.glsl",
                "resources/shaders/fragment_shader.glsl"
        );

        createCamera(world);
    }

    @Override
    public void startClient(World world) {
        System.out.println("CLIENT LEVEL START!");
        world.getRenderer().setClearColor(0.65f, 0.75f, 0.81f, 1.0f);
        baseShader = new Shader(
                "resources/shaders/vertex_shader.glsl",
                "resources/shaders/fragment_shader.glsl"
        );

        createCamera(world);
    }

    @Override
    public void createEntityNetworkEvent(NetworkEntity entity) {

    }

    @Override
    public void terminate() {
        baseShader.delete();
    }

    private void createCamera(World world) {
        Entity cameraEntity = world.instantiateEntity(100, false,"camera");
        Camera camera = new Camera();
        cameraEntity.addComponent(camera);

        camera.setFieldOfView(60.f);
        camera.setShader(baseShader);
        cameraEntity.getTransform().setPosition(0.f, 0.f, 10.f);
    }
}
