package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.Physics;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.opengl.Texture;

import java.util.Random;

public class DotsCounter extends Component {
    private Transform transform;
    private Events events;
    private int count;
    private Entity dotEntity;
    private Entity counterEntity;
    private float lastCaughtTime = 0.f;
    private float time = 0.f;
    public static final Texture[] digitsTextures = {
            new Texture("resources/textures/0.png"),
            new Texture("resources/textures/1.png"),
            new Texture("resources/textures/2.png"),
            new Texture("resources/textures/3.png"),
            new Texture("resources/textures/4.png"),
            new Texture("resources/textures/5.png"),
            new Texture("resources/textures/6.png"),
            new Texture("resources/textures/7.png"),
            new Texture("resources/textures/8.png"),
            new Texture("resources/textures/9.png"),
            new Texture("resources/textures/10.png"),
    };

    public DotsCounter(boolean isFirst) {
        World world = Application.shared.getWorld();
        this.dotEntity = world.findEntityByName("dot").get();
        this.counterEntity = world.findEntityByName(isFirst ? "firstCounter" : "secondCounter").get();
    }

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        events = getEntity().getEvents();
        count = 0;
    }

    @Override
    public void terminate() {
        for(Texture texture: digitsTextures) {
            texture.delete();
        }
    }

    @Override
    public void update(float deltaTime) {
        time += deltaTime;
        Physics physics = getEntity().getPhysics();
        Random random = new Random();

        if(physics.intersects(getEntity(), dotEntity) && time - lastCaughtTime >= 1.f) {
            lastCaughtTime = time;
            count++;
            System.out.println(count);
            Laser laser = dotEntity.getComponentWithType(Laser.class);
            laser.setTarget(new Vector4f(random.nextInt(10) - 5, random.nextInt(10) - 5, 0, 0));
        }
        showCounter(counterEntity);
    }

    private void showCounter(Entity counterEntity) {
        if(count == 0 || count > 10){
            return;
        }
        Texture textureCounter = DotsCounter.digitsTextures[count];
        counterEntity.getComponentWithType(Mesh.class).setTexture(textureCounter);
    }
}
