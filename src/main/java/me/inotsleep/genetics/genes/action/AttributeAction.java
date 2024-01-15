package me.inotsleep.genetics.genes.action;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;

public class AttributeAction implements Action {
    private final Attribute attribute;
    private final double value;
    public AttributeAction(String[] rawAction) {
        attribute = Attribute.valueOf(rawAction[0]);
        value = Double.parseDouble(rawAction[1]);
    }

    @Override
    public void apply(LivingEntity entity) {
        entity.registerAttribute(attribute);
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        if (attributeInstance == null) return;

        attributeInstance.setBaseValue(value);
        if (attribute == Attribute.GENERIC_MAX_HEALTH) entity.setHealth(value);
    }
}
