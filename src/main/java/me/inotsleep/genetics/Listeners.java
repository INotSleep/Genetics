package me.inotsleep.genetics;

import me.inotsleep.genetics.genes.Gene;
import me.inotsleep.genetics.genes.GeneUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Listeners implements Listener {
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Map<String, Gene> entityGenes = Gene.perEntityGenes.get(event.getEntity().getType());
        if (entityGenes == null || !(event.getEntity() instanceof LivingEntity)) return;
        PersistentDataContainer container = event.getEntity().getPersistentDataContainer();

        if (container.has(new NamespacedKey(Genetics.getInstance(), "genes"), PersistentDataType.STRING)) return;

        StringBuilder sb = new StringBuilder();

        entityGenes.values().forEach(gene -> sb.append(gene.tryToCreate(event.getEntity())));

        container.set(new NamespacedKey(Genetics.getInstance(), "genes"), PersistentDataType.STRING, sb.toString());
    }

    @EventHandler
    public void onBreedEvent(EntityBreedEvent event) {
        PersistentDataContainer fatherContainer = event.getFather().getPersistentDataContainer();
        PersistentDataContainer motherContainer = event.getMother().getPersistentDataContainer();
        PersistentDataContainer childContainer = event.getEntity().getPersistentDataContainer();

        List<String> genes = new ArrayList<>();
        genes.add(fatherContainer.get(new NamespacedKey(Genetics.getInstance(), "genes"), PersistentDataType.STRING));
        genes.add(motherContainer.get(new NamespacedKey(Genetics.getInstance(), "genes"), PersistentDataType.STRING));

        genes = genes.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (genes.isEmpty()) return;
        if (genes.size() == 1) {
            childContainer.set(new NamespacedKey(Genetics.getInstance(), "genes"), PersistentDataType.STRING, genes.get(0));
            return;
        }

        String childGenes = GeneUtils.combine(genes.get(0), genes.get(1));
        childContainer.set(new NamespacedKey(Genetics.getInstance(), "genes"), PersistentDataType.STRING, childGenes);
        Set<String> sepGenes = new HashSet<>();

        Pattern pattern = Pattern.compile(".{2}");
        Matcher matcher = pattern.matcher(childGenes);
        while (matcher.find()) {
            sepGenes.add(matcher.group());
        }

        for (String sGene : sepGenes.toArray(new String[0])) {
            Gene gene = Gene.publicGenes.get(sGene.substring(0, 0).toUpperCase());
            gene.applyActions(event.getEntity(), sGene);
        }
    }
}
