package com.pixelthieves.elementtd.entity.creep;

import com.artemis.World;
import com.pixelthieves.core.component.DamageComponent;
import com.pixelthieves.core.component.PositionComponent;
import com.pixelthieves.core.component.SizeComponent;
import com.pixelthieves.core.component.TimeComponent;
import com.pixelthieves.core.entity.ConcreteEntity;
import com.pixelthieves.elementtd.Health;
import com.pixelthieves.elementtd.Treasure;
import com.pixelthieves.elementtd.component.*;
import com.pixelthieves.elementtd.component.attack.effects.buff.BuffableSpeedComponent;
import com.pixelthieves.elementtd.map.Path;

/**
 * Created by Tomas on 10/5/13.
 */
public class Creep extends ConcreteEntity {

    private Creep(CreepType creepType, CreepAbilityType creepAbilityType, float speed, float size, int health,
                  Path path, WaveComponent waveComponent, World world, float x, float y, boolean endless) {
        super(world);
        addComponent(new PositionComponent(x, y, 0));
        // addComponent(new RotationComponent(0, 0, 0));
        addComponent(new PathComponent(path));
        addComponent(new NameComponent(creepType.getName().toString()));
        addComponent(new SpriteComponent(creepType.getAssets(), creepType.getTexture()));
        addComponent(new SizeComponent(size, size, 0));
        addComponent(new BuffableSpeedComponent(speed));
        addComponent(new HealthComponent(new Health(health)));
        addComponent(new TreasureComponent(new Treasure(creepType.getTreasure())));
        addComponent(new TimeComponent());
        addComponent(new CreepAbilityComponent(creepAbilityType));
        addComponent(new DamageComponent(1));
        addComponent(new CreepTypeComponent(creepType));
        addComponent(new VisibleComponent(true));
        addComponent(new TintComponent());
        if (endless) {
            addComponent(new EndlessWaveComponent());
        }
        addComponent(waveComponent);
    }

    public static void registerCreep(World world, Path path, WaveComponent waveComponent, CreepType creepType, float x,
                                     float y, float lifeMultiplier, boolean endless) {
        registerCreep(world, path, waveComponent, creepType, creepType.getAbilityType(), creepType.getSpeed(),
                creepType.getSize(), (int) (creepType.getHealth() * lifeMultiplier), x, y, endless);
    }

    public static void registerCreep(World world, Path path, WaveComponent waveComponent, CreepType creepType,
                                     CreepAbilityType creepAbilityType, float speed, float size, int health, float x,
                                     float y, boolean endless) {
        Creep creep =
                new Creep(creepType, creepAbilityType, speed, size, health, path, waveComponent, world, x, y, endless);
        creep.register();
        waveComponent.addCreep(creep.entity);
    }
}
