package com.pixelthieves.elementtd.system.abilitySytems.damage.hit;

import com.artemis.Entity;
import com.pixelthieves.elementtd.component.attack.effects.SlowEffect;
import com.pixelthieves.elementtd.component.attack.projectile.data.SlowData;

/**
 * Created by Tomas on 10/4/13.
 */
public class HitSlowSystem extends HitEffectSystem<SlowData, SlowEffect> {

    public HitSlowSystem() {
        super(SlowData.class, SlowEffect.class);
    }

    @Override
    protected void initialize() {
        super.initialize();
        // DISCUS this on stackoverflow !
        setAoe(new AoeSystem() {
        });
    }

    @Override
    protected SlowEffect resetEffect(Entity e, Entity target, SlowEffect effect, SlowData effectData) {
        effect.set(effectData.getEffect(), effectData.getDuration(), effectData.getSlowRatio());
        return effect;
    }

    @Override
    protected SlowEffect createEffect(Entity e, Entity target, SlowData effectData) {
        return resetEffect(e, target, new SlowEffect(), effectData);
    }
}
