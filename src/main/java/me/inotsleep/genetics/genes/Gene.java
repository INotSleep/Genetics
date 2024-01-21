package me.inotsleep.genetics.genes;

import me.inotsleep.genetics.genes.action.Action;
import org.bukkit.entity.EntityType;

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

    public Gene(String geneSymbol, List<Action> actionsDominant, List<Action> actionsRecessive, double chance, double chanceDominant, EntityType entityType) {
        this.geneSymbol = geneSymbol;
        this.actionsDominant = actionsDominant;
        this.actionsRecessive = actionsRecessive;
        this.chance = chance;
        this.chanceDominant = chanceDominant;

        if (entityType == null) publicGenes.put(geneSymbol, this);
        else {
            Map<String, Gene> genes = perEntityGenes.getOrDefault(entityType, new HashMap<>());
            genes.put(geneSymbol, this);
            perEntityGenes.put(entityType, genes);
        }
    }
}
