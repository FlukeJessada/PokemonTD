package com.pixelthieves.pokemontd.entity.tower;

import com.pixelthieves.pokemontd.Treasure;
import com.pixelthieves.pokemontd.component.attack.AbilityComponent;
import com.pixelthieves.pokemontd.component.attack.EffectName;
import com.pixelthieves.pokemontd.component.attack.projectile.BuffAbility;
import com.pixelthieves.pokemontd.component.attack.projectile.HitAbility;
import com.pixelthieves.pokemontd.component.attack.projectile.SunbeamAbility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pixelthieves.pokemontd.component.attack.EffectName.*;
import static java.lang.Math.PI;
import static java.lang.Math.pow;

/**
 * User: Seda
 * Date: 5.10.13
 * Time: 12:03
 */

public class TowerTypeBuilder {

    public static final float SIZE = 1.5f;
    public static final float XS_RANGE = 1.5f;
    public static final float S_RANGE = 2f;
    public static final float N_RANGE = 3f;
    public static final float L_RANGE = 4f;
    public static final float XL_RANGE = 5f;
    public static final float XXL_RANGE = 6f;
    public static final float XXXL_RANGE = 7f;
    public static final float BUFF_RANGE = XXL_RANGE;
    public static final float XXL_SPEED = 0.25f;
    public static final float XL_SPEED = 0.5f;
    public static final float L_SPEED = 1f;
    public static final float N_SPEED = 2;
    public static final float S_SPEED = 4f;
    public static final float XS_SPEED = 8f;
    public static final float XXS_SPEED = 16f;
    public static final int INITIAL_BASE_DAMAGE = 15;
    public static final int INITIAL_GOLD = 10;
    private static final float koefficient = (float) (scaleByRange(XS_RANGE) - scaleByRange(XXXL_RANGE)) * 2f;

    private List<TowerType> getData(float scale) {
        List<Specs> specs = new ArrayList<Specs>();
        // Shop
        specs.add(new Specs(TowerName.Shop, null, 18, 0, 0, null, Treasure.fromNone()));
        /// ######################  PURES
        // Basic
        specs.add(new Specs(TowerName.Needle, Normal, 18, N_SPEED, N_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromNone().addGold(7)));
        specs.add(new Specs(TowerName.Pinch, Normal, 54, N_SPEED, N_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromNone().addGold(13)));
        specs.add(new Specs(TowerName.Sting, Normal, 162, N_SPEED, N_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromNone().addGold(37)));
        specs.add(new Specs(TowerName.Scratch, Splash, 5, S_SPEED, N_RANGE, HitAbility.getSplash("bullet", scale, 2), Treasure.fromNone().addGold(7)));
        specs.add(new Specs(TowerName.Bite, Splash, 14, S_SPEED, N_RANGE, HitAbility.getSplash("bullet", scale, 2), Treasure.fromNone().addGold(13)));
        specs.add(new Specs(TowerName.Smash, Splash, 41, S_SPEED, N_RANGE, HitAbility.getSplash("bullet", scale, 2), Treasure.fromNone().addGold(37)));
        // blue
        specs.add(new Specs(TowerName.Splash, Normal, 280, N_SPEED, N_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromWater(1).addGold(125)));
        specs.add(new Specs(TowerName.Ripple, Normal, 2124, N_SPEED, N_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromWater(2).addGold(375)));
        specs.add(new Specs(TowerName.Tsunami, Wave, 106614, N_SPEED, N_RANGE, HitAbility.getBubbleGrowing(scale), Treasure.fromWater(3).addGold(10000)));
        //red
        specs.add(new Specs(TowerName.Sparkle, Normal, 252, N_SPEED, S_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromFire(1).addGold(125)));
        specs.add(new Specs(TowerName.Burning, Normal, 1153, L_SPEED, S_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromFire(2).addGold(375)));
        specs.add(new Specs(TowerName.Inferno, Burn, 78346, XL_SPEED, S_RANGE, HitAbility.getFireDot(scale), Treasure.fromFire(3).addGold(10000)));
        //green
        specs.add(new Specs(TowerName.Flower, Normal, 514, XS_SPEED, S_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromNature(1).addGold(125)));
        specs.add(new Specs(TowerName.Forest, Normal, 7890, XS_SPEED, S_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromNature(2).addGold(375)));
        specs.add(new Specs(TowerName.Nature, Entangle, 181472, XS_SPEED, S_RANGE, HitAbility.getNature(scale, 0.7f, 2f, 0.5f), Treasure.fromNature(3).addGold(375)));
        //yellow
        specs.add(new Specs(TowerName.Chicken, Normal, 220, L_SPEED, N_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromLight(1).addGold(125)));
        specs.add(new Specs(TowerName.Screech, Normal, 1914, L_SPEED, N_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromLight(2).addGold(375)));
        specs.add(new Specs(TowerName.Claw, Peck, 84148, L_SPEED, N_RANGE, HitAbility.getClaw(scale, 0.3f, 2), Treasure.fromLight(3).addGold(10000)));
        //purple
        specs.add(new Specs(TowerName.Spooky, Normal, 239, N_SPEED, XL_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromDarkness(1).addGold(125)));
        specs.add(new Specs(TowerName.Haunted, Normal, 1744, N_SPEED, XL_RANGE, HitAbility.getNormal("bullet", scale), Treasure.fromDarkness(2).addGold(375)));
        specs.add(new Specs(TowerName.Nightmare, Terrify, 0.3f, N_SPEED, XL_RANGE, HitAbility.getTemLifeSteal(scale, 5f), Treasure.fromDarkness(3).addGold(10000)));
        /// ######################  TWO ELEMENT
        specs.add(new Specs(TowerName.Noble, Haste, 1.25f, 45, BUFF_RANGE, BuffAbility.getSpeed(20), Treasure.fromWater(1).addFire(1).addGold(375)));
        specs.add(new Specs(TowerName.Majestic, Haste, 1.5f, 45, BUFF_RANGE, BuffAbility.getSpeed(20), Treasure.fromWater(2).addFire(2).addGold(3500)));
        specs.add(new Specs(TowerName.Magnificent, Haste, 2f, 45, BUFF_RANGE, BuffAbility.getSpeed(20), Treasure.fromWater(3).addFire(3).addGold(10000)));

        specs.add(new Specs(TowerName.Sunny, Sunbeam, 574, XS_SPEED, L_RANGE, new SunbeamAbility(0.2f, L_RANGE), Treasure.fromWater(1).addNature(1).addGold(375)));
        specs.add(new Specs(TowerName.Solar, Sunbeam, 2817, XS_SPEED, L_RANGE, new SunbeamAbility(0.7f, L_RANGE * 1.5f), Treasure.fromWater(2).addNature(2).addGold(3500)));
        specs.add(new Specs(TowerName.Photonic, Sunbeam, 18, XS_SPEED, L_RANGE, new SunbeamAbility(1.7f, L_RANGE * 2), Treasure.fromWater(3).addNature(3).addGold(10000)));

        specs.add(new Specs(TowerName.Poison, Weaken, 411, N_SPEED, N_RANGE, HitAbility.getPoison(scale, 0.2f, 1, 0.1f), Treasure.fromWater(1).addLight(1).addGold(375)));
        specs.add(new Specs(TowerName.Toxic, Weaken, 2414, N_SPEED, N_RANGE, HitAbility.getPoison(scale, 0.5f, 1, 0.15f), Treasure.fromWater(2).addLight(2).addGold(3500)));
        specs.add(new Specs(TowerName.Venom, Weaken, 24917, N_SPEED, N_RANGE, HitAbility.getPoison(scale, 0.8f, 1, 0.2f), Treasure.fromWater(3).addLight(3).addGold(10000)));

        specs.add(new Specs(TowerName.Ice, Freeze, 378, N_SPEED, N_RANGE, HitAbility.getSlow("bullet", "ice", scale, 0f, 0.2f, 1, 1f), Treasure.fromWater(1).addDarkness(1).addGold(375)));
        specs.add(new Specs(TowerName.Freezing, Freeze, 1978, N_SPEED, N_RANGE, HitAbility.getSlow("bullet", "ice", scale, 1f, 0.3f, 1.5f, 1f), Treasure.fromWater(2).addDarkness(2).addGold(3500)));
        specs.add(new Specs(TowerName.Polar, Freeze, 25787, N_SPEED, N_RANGE, HitAbility.getSlow("bullet", "ice", scale, 2f, 0.7f, 2, 1f), Treasure.fromWater(3).addDarkness(3).addGold(10000)));

        specs.add(new Specs(TowerName.Burst, Incinerate, 277, XXL_SPEED, N_RANGE, HitAbility.getIncreasingDamage(scale), Treasure.fromFire(1).addNature(1).addGold(375)));
        specs.add(new Specs(TowerName.Pyro, Incinerate, 1978, XXL_SPEED, N_RANGE, HitAbility.getIncreasingDamage(scale), Treasure.fromFire(2).addNature(2).addGold(3500)));
        specs.add(new Specs(TowerName.Flamethrower, Incinerate, 21402, XXL_SPEED, N_RANGE, HitAbility.getIncreasingDamage(scale), Treasure.fromFire(3).addNature(3).addGold(10000)));

        specs.add(new Specs(TowerName.Punch, Stomp, 414, N_SPEED, N_RANGE, HitAbility.getSlow("rock", "rockEffect", scale, 2f, 0.1f, 1, 1f), Treasure.fromFire(1).addLight(1).addGold(375)));
        specs.add(new Specs(TowerName.Takedown, Stomp, 2013, N_SPEED, N_RANGE, HitAbility.getSlow("rock", "rockEffect", scale, 2f, 0.2f, 2, 1f), Treasure.fromFire(2).addLight(2).addGold(3500)));
        specs.add(new Specs(TowerName.Knockout, Stomp, 25811, N_SPEED, N_RANGE, HitAbility.getSlow("rock", "rockEffect", scale, 2f, 0.3f, 3, 1f), Treasure.fromFire(3).addLight(3).addGold(10000)));

        specs.add(new Specs(TowerName.Sneaky, Steal, 402, N_SPEED, N_RANGE, HitAbility.getMoney(scale, 1.5f), Treasure.fromFire(1).addDarkness(1).addGold(375)));
        specs.add(new Specs(TowerName.Stealth, Steal, 2147, N_SPEED, N_RANGE, HitAbility.getMoney(scale, 1.75f), Treasure.fromFire(2).addDarkness(2).addGold(3500)));
        specs.add(new Specs(TowerName.Thief, Steal, 26784, N_SPEED, N_RANGE, HitAbility.getMoney(scale, 2f), Treasure.fromFire(3).addDarkness(3).addGold(10000)));

        specs.add(new Specs(TowerName.Pebble, Boulder, 398, S_SPEED, N_RANGE, HitAbility.getSplash("rock", scale, 2), Treasure.fromNature(1).addLight(1).addGold(375)));
        specs.add(new Specs(TowerName.Rocky, Boulder, 2487, S_SPEED, S_RANGE, HitAbility.getSplash("rock", scale, 3), Treasure.fromNature(2).addLight(2).addGold(3500)));
        specs.add(new Specs(TowerName.Massive, Boulder, 31205, XS_SPEED, XS_RANGE, HitAbility.getSplash("rock", scale, 5), Treasure.fromNature(3).addLight(3).addGold(10000)));

        specs.add(new Specs(TowerName.Dizzy, Impair, 473, N_SPEED, N_RANGE, HitAbility.getSlow("bullet", "orb", scale, 0, 0.5f, 1, 0.5f), Treasure.fromNature(1).addDarkness(1).addGold(375)));
        specs.add(new Specs(TowerName.Paralyze, Impair, 2044, N_SPEED, N_RANGE, HitAbility.getSlow("bullet", "orb", scale, 0, 0.7f, 2, 0.75f), Treasure.fromNature(2).addDarkness(2).addGold(3500)));
        specs.add(new Specs(TowerName.Crippling, Impair, 26781, N_SPEED, N_RANGE, HitAbility.getSlow("bullet", "orb", scale, 0, 0.9f, 4, 0.85f), Treasure.fromNature(3).addDarkness(3).addGold(10000)));

        specs.add(new Specs(TowerName.Spell, Charm, 1.25f, 20, BUFF_RANGE, BuffAbility.getDamage(20), Treasure.fromWater(1).addFire(1).addGold(375)));
        specs.add(new Specs(TowerName.Enchanted, Charm, 1.5f, 20, BUFF_RANGE, BuffAbility.getDamage(20), Treasure.fromWater(2).addFire(2).addGold(3500)));
        specs.add(new Specs(TowerName.Magic, Charm, 2f, 20, BUFF_RANGE, BuffAbility.getDamage(20), Treasure.fromWater(3).addFire(3).addGold(10000)));


        /// ######################  TREE ELEMENT
        specs.add(new Specs(TowerName.Grind, Shatter, 2178, XS_SPEED, N_RANGE, HitAbility.getSplash("rock", scale, 3f), Treasure.fromWater(1).addFire(1).addNature(1).addGold(1125)));
        specs.add(new Specs(TowerName.Pulverize, Shatter, 8178, XXS_SPEED, S_RANGE, HitAbility.getSplash("rock", scale, 6f), Treasure.fromWater(1).addFire(1).addNature(1).addGold(5000)));

        specs.add(new Specs(TowerName.Throttling, Blaze, 1744, XXL_SPEED, S_RANGE, HitAbility.getBubble("fire", scale), Treasure.fromWater(1).addFire(1).addLight(1).addGold(1125)));
        specs.add(new Specs(TowerName.Galloping, Blaze, 7847, XXL_SPEED, S_RANGE, HitAbility.getBubble("fire", scale), Treasure.fromWater(2).addFire(2).addLight(2).addGold(5000)));

        specs.add(new Specs(TowerName.Vampire, LifeSteal, 2445, N_SPEED, N_RANGE, HitAbility.getLife(scale, 1f, 0.5f), Treasure.fromWater(1).addNature(1).addLight(1).addGold(1125)));
        specs.add(new Specs(TowerName.Dracula, LifeSteal, 9136, N_SPEED, N_RANGE, HitAbility.getLife(scale, 1f, 1f), Treasure.fromWater(2).addNature(2).addLight(2).addGold(5000)));

        specs.add(new Specs(TowerName.Disease, Infect, 2141, XS_SPEED, N_RANGE, HitAbility.getAuraDmg(scale, N_RANGE), Treasure.fromWater(1).addLight(1).addDarkness(1).addGold(1125)));
        specs.add(new Specs(TowerName.Epidemic, Infect, 7145, XL_SPEED, L_RANGE, HitAbility.getAuraDmg(scale, L_RANGE), Treasure.fromWater(2).addLight(2).addDarkness(2).addGold(5000)));

        specs.add(new Specs(TowerName.Confused, Normal, 1887, L_SPEED, N_RANGE, HitAbility.getDumbClaw(scale, 0f, 0), Treasure.fromWater(1).addLight(1).addDarkness(1).addGold(1125)));
        specs.add(new Specs(TowerName.Supersonic, SoundWave, 7332, L_SPEED, N_RANGE, HitAbility.getBubble("bubble", scale), Treasure.fromWater(2).addLight(2).addDarkness(2).addGold(5000)));

        specs.add(new Specs(TowerName.Charged, Thunderbolt, 2487, S_SPEED, XL_RANGE, HitAbility.getCharge(scale), Treasure.fromFire(1).addNature(1).addLight(1).addGold(1125)));
        specs.add(new Specs(TowerName.Supercharged, Thunderbolt, 9475, XS_SPEED, XXXL_RANGE, HitAbility.getCharge(scale * 2), Treasure.fromFire(2).addNature(2).addLight(2).addGold(5000)));

        specs.add(new Specs(TowerName.Stomp, Quake, 2017, N_SPEED, N_RANGE, HitAbility.getSlow("rock", "", scale, 0, 1f, 0.5f, 0.8f), Treasure.fromFire(1).addNature(1).addDarkness(1).addGold(1125)));
        specs.add(new Specs(TowerName.Earthquake, Quake, 8744, S_SPEED, N_RANGE, HitAbility.getSlow("rock", "", scale, 0, 1f, 1.5f, 1), Treasure.fromFire(2).addNature(2).addDarkness(2).addGold(5000)));

        specs.add(new Specs(TowerName.Erruption, Magma, 3046, XS_SPEED, N_RANGE, HitAbility.getVolcano(scale, N_RANGE, 4, 0.25f), Treasure.fromFire(1).addLight(1).addDarkness(1).addGold(1125)));
        specs.add(new Specs(TowerName.Volcanic, Magma, 7143, XXS_SPEED, S_RANGE, HitAbility.getVolcano(scale, S_RANGE, 16, 0.25f), Treasure.fromFire(2).addLight(2).addDarkness(2).addGold(5000)));

        specs.add(new Specs(TowerName.Hypnotic, Puzzle, 1987, N_SPEED, N_RANGE, HitAbility.getChangeDirection(scale, 1, 0.02f), Treasure.fromNature(1).addLight(1).addDarkness(1).addGold(1125)));
        specs.add(new Specs(TowerName.Illusion, Puzzle, 26281, N_SPEED, N_RANGE, HitAbility.getChangeDirection(scale, 5, 0.03f), Treasure.fromNature(2).addLight(2).addDarkness(2).addGold(5000)));

        List<TowerType> data = new ArrayList<TowerType>();
        for (Specs specification : specs) {
            data.add(createTowerType(scale, specification));
        }
        return data;
    }

    private TowerType createTowerType(float scale, Specs specs) {
        // float damage = getDamage(getBaseDamage(INITIAL_BASE_DAMAGE, specs.level), specs.range, specs.speed);
        return new TowerType(specs.name, specs.effectName, SIZE * scale, specs.speed, specs.damage, specs.range * scale, specs.attackComponent, specs.treasure);
    }
  /*
    private float getDamage(float baseDamage, float range, float speed) {
        return Math.max((baseDamage + baseDamage * (float) scaleByRange(range) / koefficient) * speed, 0);
    }     */

    private static double scaleByRange(float range) {
        return (PI * pow(N_RANGE, 2) - (PI * pow(range, 2)));
    }
  /*
    public float getBaseDamage(float initialBaseDamage, int levels) {
        return (float) (initialBaseDamage * pow(3, levels - 1));
    }  */

    public int getGold(int initialGold, int levels) {
        return (int) (initialGold * pow(2, levels - 1));
    }


    private static class Specs {
        private final TowerName name;
        private final float damage;
        private final float speed;
        private final float range;
        private final AbilityComponent attackComponent;
        private final Treasure treasure;
        private final EffectName effectName;

        private Specs(TowerName name, EffectName effectName, float damage, float speed, float range, AbilityComponent attackComponent, Treasure treasure) {
            this.name = name;
            this.effectName = effectName;
            this.damage = damage;
            this.speed = speed;
            this.range = range;
            this.attackComponent = attackComponent;
            this.treasure = treasure;
        }
    }


    public Map<TowerName, TowerType> build(float scale) {
        Map<TowerName, TowerType> map = new HashMap<TowerName, TowerType>();
        for (TowerType towerType : getData(scale)) {
            map.put(towerType.getName(), towerType);
        }
        return map;
    }
}
