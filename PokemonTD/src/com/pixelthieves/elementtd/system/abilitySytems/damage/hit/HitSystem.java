package com.pixelthieves.elementtd.system.abilitySytems.damage.hit;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.pixelthieves.core.component.PositionComponent;
import com.pixelthieves.core.component.SizeComponent;
import com.pixelthieves.core.component.TargetComponent;
import com.pixelthieves.core.utils.Collision;
import com.pixelthieves.elementtd.App;
import com.pixelthieves.elementtd.component.HealthComponent;
import com.pixelthieves.elementtd.component.attack.effects.buff.BuffableDamageComponent;
import com.pixelthieves.elementtd.component.attack.projectile.HitAbility;
import com.pixelthieves.elementtd.component.attack.projectile.data.EffectData;
import com.pixelthieves.elementtd.entity.TextInfo;

/**
 * Created by Tomas on 10/4/13.
 */
public abstract class HitSystem<T extends EffectData> extends EntityProcessingSystem {

    private final Class<T> effectDataClass;
    @Mapper
    ComponentMapper<BuffableDamageComponent> damageMapper;
    @Mapper
    ComponentMapper<PositionComponent> positionMapper;
    @Mapper
    ComponentMapper<TargetComponent> targetMapper;
    @Mapper
    ComponentMapper<SizeComponent> sizeMapper;
    @Mapper
    ComponentMapper<HitAbility> projectileMapper;

    private ComponentMapper<T> effectDataMapper;
    private AoeSystem aoe;
    private T effectData;
    private Entity entity;
    private Entity target;


    public HitSystem(Class<T> effectDataClass) {
        super(Aspect.getAspectForAll(TargetComponent.class, effectDataClass));
        this.effectDataClass = effectDataClass;
    }

    @Override
    protected void initialize() {
        super.initialize();
        effectDataMapper = world.getMapper(effectDataClass);
    }


    public void setAoe(AoeSystem aoe) {
        this.aoe = aoe;
        world.setSystem(this.aoe, true);
    }

    @Override
    protected void process(Entity e) {
        this.entity = e;
        this.effectData = effectDataMapper.get(e);

        Vector3 entityPosition = positionMapper.get(e).getPoint();
        Vector3 entitySize = sizeMapper.get(e).getPoint();
        HitAbility hitAbility = projectileMapper.get(e);
        target = targetMapper.get(e).getTarget();

        if (hitAbility.getType().equals(HitAbility.Type.IMMEDIATE_NOCONTACT_DAMAGE)) {
            aoe.start(entityPosition, hitAbility.getAoe());
            e.deleteFromWorld();
        } else if (positionMapper.get(target) == null) {
            e.deleteFromWorld();
        } else if (hitAbility.getType().equals(HitAbility.Type.IMMEDIATE_ATTACK)) {
            tryToHit(effectDataMapper.get(e), e, target);
            e.deleteFromWorld();
        } else {

            Vector3 targetPosition = positionMapper.get(target).getPoint();
            Vector3 targetSize = sizeMapper.get(target).getPoint();

            if (Collision.intersectRects(entityPosition, targetPosition, entitySize, targetSize)) {
                if (hitAbility.getAoe() > 0) {
                    aoe.start(targetPosition, hitAbility.getAoe());
                } else {
                    tryToHit(effectDataMapper.get(e), e, target);
                }
                e.deleteFromWorld();
            }
        }
    }

    protected void tryToHit(T effectData, Entity e, Entity target) {
        if (App.CHANCE.happens(effectData.getChance())) {
            hit(effectData, e, target);
        } else {
            Vector3 position = positionMapper.get(target).getPoint();
            TextInfo.registerTextInfo(world, "MISSED", Color.RED, position.x, position.y);
        }
    }

    protected abstract void hit(T effectData, Entity e, Entity target);

    public abstract class AoeSystem extends EntityProcessingSystem {

        @Mapper
        ComponentMapper<PositionComponent> positionMapper;
        private float aoe;
        private Vector3 position;
        private int hit;

        public AoeSystem() {
            super(Aspect.getAspectForAll(HealthComponent.class, PositionComponent.class));
        }

        @Override
        protected void begin() {
            hit = 0;
        }

        @Override
        protected void end() {
            System.out.println("Aoe hit: "+hit);
        }

        @Override
        protected void process(Entity e) {
            if (position.dst(positionMapper.get(e).getPoint()) <= aoe) {
                hit++;
                tryToHit(effectData, entity, e);
            }
        }


        public void start(Vector3 position, float aoe) {
            this.position = position;
            this.aoe = aoe;
            this.process();
        }

    }

}
