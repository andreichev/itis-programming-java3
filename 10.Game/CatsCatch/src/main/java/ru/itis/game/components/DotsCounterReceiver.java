package ru.itis.game.components;

import ru.itis.game.network.CounterValue;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.network.model.NetworkComponentState;
import ru.itis.gengine.renderer.Texture;

public class DotsCounterReceiver extends Component {
    private Entity counterEntity;
    private final boolean isFirst;
    private int count;

    public DotsCounterReceiver(int id, boolean isFirst) {
        super(id, true);
        this.isFirst = isFirst;
    }

    @Override
    public void initialize() {
        World world = Application.shared.getWorld();
        this.counterEntity = world.findEntityByName(isFirst ? "firstCounter" : "secondCounter").orElse(null);
    }

    private void showCounter(Entity counterEntity) {
        if(counterEntity == null) {
            World world = Application.shared.getWorld();
            counterEntity = world.findEntityByName(isFirst ? "firstCounter" : "secondCounter").orElse(null);
        }
        if(count == 0 || count > 10){
            return;
        }
        Texture textureCounter = DotsCounter.digitsTextures[count];
        counterEntity.getComponentWithType(Mesh.class).setTexture(textureCounter);
    }

    @Override
    public void setState(NetworkComponentState state) {
        CounterValue counterValue = (CounterValue) state;
        count = counterValue.value();
        showCounter(counterEntity);
    }
}
