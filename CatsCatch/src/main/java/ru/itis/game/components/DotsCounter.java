package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.Physics;
import ru.itis.gengine.gamelogic.components.Transform;

import java.util.Random;

public class DotsCounter extends Component {
    private Transform transform;
    private Events events;
    private int count;
    private Entity dotEntity;
    private float lastCaughtTime = 0.f;
    private float time = 0.f;

    public DotsCounter(Entity dotEntity) {
        this.dotEntity = dotEntity;
    }

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        events = getEntity().getEvents();
        count = 0;
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
    }
}
