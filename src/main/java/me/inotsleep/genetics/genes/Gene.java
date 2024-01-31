package me.inotsleep.genetics.genes;

import me.inotsleep.genetics.genes.action.Action;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gene {
    public static Map<String, Gene> publicGenes;
    public static Map<EntityType, Map<String, Gene>> perEntityGenes;

    public String geneSymbol;
    public List<Action> actionsDominant;
    public List<Action> actionsRecessive;
    public double chance;
    public double chanceDominant;
    private double chanceRecessive;

    public Gene(String geneSymbol, List<Action> actionsDominant, List<Action> actionsRecessive, double chance, double chanceDominant,double chanceRecessive, EntityType entityType) {
        this.geneSymbol = geneSymbol;
        this.actionsDominant = actionsDominant;
        this.actionsRecessive = actionsRecessive;
        this.chance = chance;
        this.chanceDominant = chanceDominant;
        this.chanceRecessive = chanceRecessive;

        if (entityType == null) publicGenes.put(geneSymbol, this);
        else {
            Map<String, Gene> genes = perEntityGenes.getOrDefault(entityType, new HashMap<>());
            genes.put(geneSymbol, this);
            perEntityGenes.put(entityType, genes);
        }
    }

    public String tryToCreate(@NotNull Entity entity) {
        switch ((Math.random()<chanceDominant ? 2 : 0) + (Math.random()<chanceRecessive ? 1 : 0)) {
            case 1: {
                actionsRecessive.forEach((action -> action.apply((LivingEntity) entity)));
                return (geneSymbol + geneSymbol).toLowerCase();
            }
            case 2: {
                actionsDominant.forEach((action -> action.apply((LivingEntity) entity)));
                return (geneSymbol + geneSymbol).toUpperCase();
            }
            case 3: {
                actionsDominant.forEach((action -> action.apply((LivingEntity) entity)));
                return (geneSymbol).toUpperCase() + (geneSymbol).toLowerCase();
            }
            default: {
                if (chanceDominant > chanceRecessive) {
                    return (geneSymbol + geneSymbol).toUpperCase();
                } else {
                    actionsRecessive.forEach((action -> action.apply((LivingEntity) entity)));
                    return (geneSymbol + geneSymbol).toLowerCase();
                }
            }
        }
    };
}
