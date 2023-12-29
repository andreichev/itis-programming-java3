package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.game.network.CounterValue;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.Physics;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.network.model.NetworkComponentState;
import ru.itis.gengine.renderer.Texture;

import java.util.Random;

public class DotsCounter extends Component {
    private int count;
    private Entity dotEntity;
    private Entity counterEntity;
    private Entity target;
    private float lastCaughtTime = 0.f;
    private float time = 0.f;
    private final boolean isFirst;
    private final int targetId;
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

    public DotsCounter(int id, int targetId, boolean isFirst) {
        super(id, true);
        this.isFirst = isFirst;
        this.targetId = targetId;
    }

    @Override
    public void initialize() {
        count = 0;
        World world = Application.shared.getWorld();
        this.target = world.findEntityById(targetId).orElse(null);
        this.dotEntity = world.findEntityByName("dot").orElse(null);
        this.counterEntity = world.findEntityByName(isFirst ? "firstCounter" : "secondCounter").orElse(null);
    }

    @Override
    public void terminate() {
        for(Texture texture: digitsTextures) {
            texture.delete();
        }
    }

    @Override
    public void update(float deltaTime) {
        if(dotEntity == null) {
            World world = Application.shared.getWorld();
            dotEntity = world.findEntityByName("dot").orElse(null);
            counterEntity = world.findEntityByName(isFirst ? "firstCounter" : "secondCounter").orElse(null);
            if(dotEntity == null) {
                return;
            }
        }
        time += deltaTime;
        Physics physics = target.getPhysics();
        if(physics.intersects(target, dotEntity) && time - lastCaughtTime >= 1.f) {
            lastCaughtTime = time;
            Random random = new Random();
            count++;
            System.out.println(count);
            Laser laser = dotEntity.getComponentWithType(Laser.class);
            laser.setTarget(new Vector4f(random.nextInt(10) - 5, random.nextInt(10) - 5, 0, 0));
            showCounter(counterEntity);
            sendCurrentState();
        }
    }

    private void showCounter(Entity counterEntity) {
        if(count == 0 || count > 10){
            return;
        }
        Texture textureCounter = DotsCounter.digitsTextures[count];
        counterEntity.getComponentWithType(Mesh.class).setTexture(textureCounter);
    }

    @Override
    public NetworkComponentState getState() {
        return new CounterValue(count);
    }
}
