package ru.itis.game.levels;

import ru.itis.game.Entities;
import ru.itis.game.components.DotsCounter;
import ru.itis.game.components.Laser;
import ru.itis.game.components.PlayerMove;
import ru.itis.game.components.SecondPlayerMove;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.BoxCollider;
import ru.itis.gengine.gamelogic.components.Camera;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.gamelogic.primitives.Primitives;
import ru.itis.gengine.network.model.NetworkEntity;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;


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

        Entity dotEntity = world.instantiateEntity(101, true, "dot");
        Texture textureDot = new Texture("resources/textures/dot.png");
        MeshData dotMeshData = Primitives.createSquare(1.f);
        Mesh dotMesh = new Mesh(dotMeshData, false, textureDot, baseShader);
        dotEntity.addComponent(dotMesh);
        BoxCollider dotTrigger = new BoxCollider();
        dotTrigger.setIsTrigger(true);
        dotEntity.addComponent(dotTrigger);
        dotEntity.addComponent(new Laser(0));
        dotEntity.getTransform().setPosition(0,0,0);
        dotEntity.sendCurrentState();

        MeshData meshDataCounter1 = Primitives.createSquare(1.0f);
        Mesh meshCounter1 = new Mesh(meshDataCounter1, false, DotsCounter.digitsTextures[0], baseShader);

        MeshData meshDataCounter2 = Primitives.createSquare(1.0f);
        Mesh meshCounter2 = new Mesh(meshDataCounter2, false, DotsCounter.digitsTextures[0], baseShader);

        Entity firstCounter = world.instantiateEntity(102, true, "firstCounter");
        firstCounter.addComponent(meshCounter1);
        firstCounter.getTransform().setPosition(-6, 5, 0);
        firstCounter.sendCurrentState();

        Entity secondCounter = world.instantiateEntity(103, true, "secondCounter");
        secondCounter.addComponent(meshCounter2);
        secondCounter.getTransform().setPosition(6, 5, 0);
        secondCounter.sendCurrentState();

        createBlocks(world);

        Texture texture1 = new Texture("resources/textures/cat.png");
        MeshData meshData1 = Primitives.createSquare(1);
        Mesh mesh1 = new Mesh(meshData1, false, texture1, baseShader);
        Entity catPlayer1Entity = world.instantiateEntity(Entities.FIRST_CAT.id, true, "player1");
        catPlayer1Entity.addComponent(mesh1);
        catPlayer1Entity.getTransform().setPosition(-5.f, 0.f, 0);
        catPlayer1Entity.addComponent(new BoxCollider());
        catPlayer1Entity.addComponent(new PlayerMove(5));
        catPlayer1Entity.addComponent(new DotsCounter(1, true));
        catPlayer1Entity.sendCurrentState();
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

        Texture texture1 = new Texture("resources/textures/cat2.png");
        MeshData meshData1 = Primitives.createSquare(1);
        Mesh mesh = new Mesh(meshData1, false, texture1, baseShader);
        Entity catPlayerEntity = world.instantiateEntity(Entities.SECOND_CAT.id, true, "player2");
        catPlayerEntity.addComponent(mesh);
        catPlayerEntity.getTransform().setPosition(5.f, 0.f, 0);
        catPlayerEntity.addComponent(new BoxCollider());
        catPlayerEntity.addComponent(new PlayerMove(5));
        catPlayerEntity.sendCurrentState();
    }

    @Override
    public void createEntityNetworkEvent(NetworkEntity entity) {
        World world = Application.shared.getWorld();
        int id = entity.getId();
        if(world.findEntityById(id).isPresent()) {
            System.out.println("ENTITY WITH ID " + id + " ALREADY EXISTS");
            return;
        }
        System.out.println("CREATE ENTITY WITH ID: " + id);
        if(id == Entities.FIRST_CAT.id) {
            Texture texture = new Texture("resources/textures/cat.png");
            MeshData meshData = Primitives.createSquare(1);
            Mesh mesh2 = new Mesh(meshData, false, texture, baseShader);
            Entity catPlayer1Entity = world.instantiateEntity(id, true, "player1");
            catPlayer1Entity.addComponent(mesh2);
            catPlayer1Entity.getTransform().setPosition(5.f, 0.f, 0);
            catPlayer1Entity.addComponent(new BoxCollider());
            catPlayer1Entity.addComponent(new SecondPlayerMove(5));
            catPlayer1Entity.applyEntity(entity);
            // catPlayer1Entity.addComponent(new DotsCounter(false));
        } else if(id == Entities.SECOND_CAT.id) {
            Texture texture = new Texture("resources/textures/cat2.png");
            MeshData meshData = Primitives.createSquare(1);
            Mesh mesh2 = new Mesh(meshData, false, texture, baseShader);
            Entity catPlayer2Entity = world.instantiateEntity(id, true, "player2");
            catPlayer2Entity.addComponent(mesh2);
            catPlayer2Entity.getTransform().setPosition(5.f, 0.f, 0);
            catPlayer2Entity.addComponent(new BoxCollider());
            catPlayer2Entity.addComponent(new SecondPlayerMove(5));
            catPlayer2Entity.applyEntity(entity);
            // catPlayer2Entity.addComponent(new DotsCounter(false));
        }
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

    private void createBlocks(World world) {
        Texture textureBlock = new Texture("resources/textures/block.png");

        for (int i = 0; i < 6; i++) {
            Entity block = world.instantiateEntity(105 + i, true, "block" + i);
            MeshData meshData = Primitives.createSquare(1.0f);
            Mesh mesh = new Mesh(meshData, false, textureBlock, baseShader);

            block.addComponent(mesh);
            block.addComponent(new BoxCollider());

            float x = (float) (Math.random() * 9) - 4.5F;
            float y = (float) (Math.random() * 9) - 4.5F;
            block.getTransform().setPosition(x, y, 0);
        }
    }
}
