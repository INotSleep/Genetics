package me.inotsleep.genetics;

import me.inotsleep.genetics.genes.Gene;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.util.List;
import java.util.Map;

public class Listeners implements Listener {
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Map<String, Gene> entityGenes = Gene.perEntityGenes.get(event.getEntity().getType());
        if (entityGenes == null || !(event.getEntity() instanceof LivingEntity)) return;
        PersistentDataContainer container = event.getEntity().getPersistentDataContainer();

        if (container.has(new NamespacedKey(Genetics.getInstance(), "genes"), PersistentDataType.STRING)) return;

        StringBuilder sb = new StringBuilder();

        entityGenes.values().forEach(gene -> {
            sb.append(gene.tryToCreate(event.getEntity()));
        });

        container.set(new NamespacedKey(Genetics.getInstance(), "genes"), PersistentDataType.STRING, sb.toString());
    }
}
