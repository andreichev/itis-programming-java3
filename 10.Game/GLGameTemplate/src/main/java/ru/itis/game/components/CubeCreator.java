package ru.itis.game.components;

import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.gamelogic.primitives.Primitives;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;

public class CubeCreator extends Component {
    Events events;
    World world;
    Texture texture;
    Shader shader;
    float xOffset = 3.f;

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    @Override
    public void initialize() {
        world = getEntity().getWorld();
        events = getEntity().getEvents();
    }

    @Override
    public void update(float deltaTime) {
        if(events.isKeyJustPressed(Key.I)) {
            Entity entity = world.instantiateEntity();
            MeshData meshData = Primitives.createCube(2.0f);
            Mesh mesh = new Mesh(meshData, false, texture, shader);
            entity.addComponent(mesh);
            entity.getTransform().translate(xOffset, 0.f, 0.f);
            xOffset += 3.f;
        }
    }
}
