package ru.itis.game.levels;

import ru.itis.game.Entities;
import ru.itis.game.components.CameraFollow;
import ru.itis.game.components.PlayerMove;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.BoxCollider;
import ru.itis.gengine.gamelogic.components.Camera;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.gamelogic.primitives.Primitives;
import ru.itis.gengine.network.model.NetworkEntity;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;


public class FirstLevel extends LevelBase {
    Shader baseShader;
    Texture groundTexture;

    @Override
    public void startServer(World world) {
        System.out.println("SERVER LEVEL START!");
        world.getRenderer().setClearColor(0.65f, 0.75f, 0.81f, 1.0f);
        baseShader = new Shader(
                "resources/shaders/vertex_shader.glsl",
                "resources/shaders/fragment_shader.glsl"
        );
        groundTexture = new Texture("resources/textures/ground.png");


        Entity playerEntity = world.instantiateEntity(Entities.PLAYER.id, true, "Player");
        Texture texture = new Texture("resources/textures/solder.png");
        MeshData data = Primitives.createSquare(1.f, 1.f);
        Mesh mesh = new Mesh(data, false, texture, baseShader);
        playerEntity.addComponent(mesh);
        playerEntity.addComponent(new BoxCollider(1.f, 1.f));
        playerEntity.addComponent(new PlayerMove(1));

        createGround(world, -1, 0.f, -1.f, 5.f, 1.f);
        createGround(world, -2,7.f, -4.f, 5.f, 1.f);

        createCamera(world, playerEntity.getTransform());
    }

    @Override
    public void startClient(World world) {
    }

    @Override
    public void createEntityNetworkEvent(NetworkEntity entity) {

    }

    @Override
    public void terminate() {
        baseShader.delete();
    }

    private void createCamera(World world, Transform target) {
        Entity cameraEntity = world.instantiateEntity(100, false,"camera");
        Camera camera = new Camera();
        cameraEntity.addComponent(camera);
        cameraEntity.addComponent(new CameraFollow(0, target));
        camera.setFieldOfView(60.f);
        camera.setShader(baseShader);
    }

    private void createGround(World world, int id, float x, float y, float w, float h) {
        Entity groundEntity = world.instantiateEntity(id, false, "Ground");
        MeshData groundData = Primitives.createSquare(w, h);
        Mesh groundMesh = new Mesh(groundData, false, groundTexture, baseShader);
        groundEntity.addComponent(groundMesh);
        groundEntity.addComponent(new BoxCollider(w, h));
        groundEntity.getTransform().setPosition(x, y, 0.f);
    }
}
