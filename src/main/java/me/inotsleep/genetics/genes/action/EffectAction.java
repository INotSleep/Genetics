package me.inotsleep.genetics.genes.action;

import me.inotsleep.genetics.Genetics;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class EffectAction implements Action {
    private PotionEffect effect;

    public EffectAction(String[] rawAction) {
        try {
            this.effect = new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(rawAction[0].toUpperCase())), Integer.MAX_VALUE - 1, Integer.parseInt(rawAction[1]), false, false);
        } catch (Exception e) {
            Genetics.throwException("Here's error in your EffectAction:", false);
            Genetics.throwException(e.getMessage(), true);
        }
    }

    @Override
    public void apply(LivingEntity entity) {
        entity.addPotionEffect(effect);
    }
}
