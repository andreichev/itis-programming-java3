package ru.itis.game.levels;

import ru.itis.game.components.DotsCounter;
import ru.itis.game.components.Laser;
import ru.itis.game.components.PlayerMove;
import ru.itis.game.components.SecondPlayerMove;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.base.GSize;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.BoxCollider;
import ru.itis.gengine.gamelogic.components.Camera;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.gamelogic.primitives.Primitives;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;


public class FirstLevel extends LevelBase {
    Shader baseShader;

    @Override
    public void playerConnected(int id, double x, double y, boolean currentPlayer) {
        System.out.printf("PLAYER %d connected\n", id);
        World world = Application.shared.getWorld();
        if (currentPlayer) {
            Texture texture1 = new Texture("resources/textures/cat.png");
            MeshData meshData1 = Primitives.createSquare(1);
            Mesh mesh1 = new Mesh(meshData1, false, texture1, baseShader);
            Entity catPlayer1Entity = world.instantiateEntity("player1");
            catPlayer1Entity.addComponent(mesh1);
            catPlayer1Entity.getTransform().setPosition(-5.f, 0.f, 0);
            catPlayer1Entity.addComponent(new BoxCollider());
            catPlayer1Entity.addComponent(new PlayerMove());
            catPlayer1Entity.getRenderer().setViewportSize(new GSize(2.f, 2.f));
            catPlayer1Entity.addComponent(new DotsCounter(true));
        } else {
            Texture texture2 = new Texture("resources/textures/cat2.png");
            MeshData meshData2 = Primitives.createSquare(1);
            Mesh mesh2 = new Mesh(meshData2, false, texture2, baseShader);

            Entity catPlayer2Entity = world.instantiateEntity("player2");
            catPlayer2Entity.addComponent(mesh2);
            catPlayer2Entity.getTransform().setPosition(5.f, 0.f, 0);
            catPlayer2Entity.addComponent(new BoxCollider());
            catPlayer2Entity.addComponent(new SecondPlayerMove());
            catPlayer2Entity.getRenderer().setViewportSize(new GSize(2.f, 2.f));
            catPlayer2Entity.addComponent(new DotsCounter(false));
        }
    }

    @Override
    public void start(World world) {
        world.getRenderer().setClearColor(0.65f, 0.75f, 0.81f, 1.0f);
        baseShader = new Shader(
                "resources/shaders/vertex_shader.glsl",
                "resources/shaders/fragment_shader.glsl"
        );

        Entity cameraEntity = world.instantiateEntity("camera");
        Camera camera = new Camera();
        cameraEntity.addComponent(camera);

        camera.setFieldOfView(60.f);
        camera.setShader(baseShader);
        cameraEntity.getTransform().setPosition(0.f, 0.f, 10.f);

        Entity dotEntity = world.instantiateEntity("dot");
        Texture textureDot = new Texture("resources/textures/dot.png");
        MeshData dotMeshData = Primitives.createSquare(1.f);
        Mesh dotMesh = new Mesh(dotMeshData, false, textureDot, baseShader);
        dotEntity.addComponent(dotMesh);
        BoxCollider dotTrigger = new BoxCollider();
        dotTrigger.setIsTrigger(true);
        dotEntity.addComponent(dotTrigger);
        dotEntity.addComponent(new Laser());
        dotEntity.getTransform().setPosition(0,0,0);

        MeshData meshDataCounter1 = Primitives.createSquare(1.0f);
        Mesh meshCounter1 = new Mesh(meshDataCounter1, false, DotsCounter.digitsTextures[0], baseShader);

        MeshData meshDataCounter2 = Primitives.createSquare(1.0f);
        Mesh meshCounter2 = new Mesh(meshDataCounter2, false, DotsCounter.digitsTextures[0], baseShader);

        Entity firstCounter = world.instantiateEntity("firstCounter");
        firstCounter.addComponent(meshCounter1);
        firstCounter.getTransform().setPosition(-6, 5, 0);

        Entity secondCounter = world.instantiateEntity("secondCounter");
        secondCounter.addComponent(meshCounter2);
        secondCounter.getTransform().setPosition(6, 5, 0);

        createBlocks(world);
    }

    @Override
    public void terminate() {
        baseShader.delete();
    }

    private void createBlocks(World world) {
        Texture textureBlock = new Texture("resources/textures/block.png");

        for (int i = 0; i < 6; i++) {
            Entity block = world.instantiateEntity("block" + i);
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
