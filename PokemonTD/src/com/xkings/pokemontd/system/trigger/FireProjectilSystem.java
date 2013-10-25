package com.xkings.pokemontd.system.trigger;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.badlogic.gdx.math.Vector3;
import com.xkings.core.component.PositionComponent;
import com.xkings.core.component.SpeedComponent;
import com.xkings.pokemontd.App;
import com.xkings.pokemontd.component.DamageComponent;
import com.xkings.pokemontd.component.attack.projectile.ProjectileComponent;
import com.xkings.pokemontd.entity.Projectile;
import com.xkings.pokemontd.system.ClosestCreepSystem;

/**
 * Created by Tomas on 10/4/13.
 */
public class FireProjectilSystem extends ApplyAbilitySystem {

    @Mapper
    ComponentMapper<SpeedComponent> speedMapper;
    @Mapper
    ComponentMapper<ProjectileComponent> projectileMapper;
    @Mapper
    ComponentMapper<PositionComponent> positionMapper;
    @Mapper
    ComponentMapper<DamageComponent> damageMapper;


    public FireProjectilSystem() {
        super(ProjectileComponent.class, ClosestCreepSystem.class);
    }

    @Override
    protected void processTarget(Entity entity, Entity target) {
        Vector3 position = positionMapper.get(entity).getPoint();
        float speed = speedMapper.get(entity).getSpeed();
        float damage = damageMapper.get(entity).getDamage();

        Vector3 closestEnemyPosition = positionMapper.get(target).getPoint();

        ProjectileComponent projectileType = projectileMapper.get(entity);
        createProjectile(projectileType, position, damage, speed, closestEnemyPosition, target);
    }

    public boolean createProjectile(ProjectileComponent projectileType, Vector3 position, float damage, float speed,
                                    Vector3 targetPosition, Entity target) {
        switch (projectileType.getType()) {
            case FOLLOW_TARGET:
                Projectile.registerProjectile(world, projectileType, position.x, position.y, damage, speed,
                        targetPosition, target);
                break;
            case PASS_THROUGH:
                Vector3 direction = targetPosition.cpy().sub(position).nor().scl(5 * App.WORLD_SCALE).add(position);
                Projectile.registerProjectile(world, projectileType, position.x, position.y, damage, speed, direction,
                        target);
                break;
            case IMMEDIATE_ATTACK:
                Projectile.registerProjectile(world, projectileType, position.x, position.y, damage, speed,
                        targetPosition, target);
                break;
        }
        return true;
    }

}
