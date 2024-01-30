package me.inotsleep.genetics;

import me.inotsleep.genetics.genes.Gene;
import me.inotsleep.genetics.genes.action.Action;
import me.inotsleep.genetics.genes.action.AttributeAction;
import me.inotsleep.genetics.genes.action.EffectAction;
import me.inotsleep.utils.AbstractConfig;
import me.inotsleep.utils.AbstractPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Config extends AbstractConfig {
    public Config(AbstractPlugin plugin) {
        super(plugin, "config.yml");
    }

    @Override
    public void addDefaults() {

        header=
            "Genes:" +
            "Genes can be 2 types: public and entityRelative\n" +
            "Public genes applies to all types of entity\n" +
            "Entity relative applies only to certain types of entity\n" +
            "\n" +
            "Gene can be dominant and recessive.\n" +
            "Each can have certain action on entity spawn.\n" +
            "There's only 2 types of action: Effect and Attribute.\n" +
            "That means, that you can specify action if entity have dominant or receive gene.\n" +
            "If entity do not have this gene at all, action of this gene will not apply to it.\n" +
            "Action meaning:\n" +
            "type of action|action key|action value\n" +
            "\n" +
            "Action key is Enum value for Attribute and PotionEffectType.\n" +
            "All Enum values can be seen here:\n" +
            "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/attribute/Attribute.html\n" +
            "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html\n" +
            "\n" +
            "Also you can specify chance of entity spawn with this gene and chance of gene being dominant." +
            "\n" +
            "Example of config given below.\n" +
            "\n" +
            "===================================\n" +
            "genes:\n" +
            "  public:\n" +
            "    \"A\":\n" +
            "      dominant:\n" +
            "      - \"attribute|GENERIC_MAX_HEALTH|1\"\n" +
            "      recessive:\n" +
            "      - \"attribute|GENERIC_MAX_HEALTH|5\"\n" +
            "      dominantChance: 0.8\n" +
            "      chance: 0.2\n" +
            "  entityRelative:\n" +
            "    \"HORSE\":\n" +
            "      \"B\":\n" +
            "        dominant: []\n" +
            "        recessive:\n" +
            "        - \"effect|REGENERATION|1\"\n" +
            "        dominantChance: 0.8\n" +
            "        chance: 0.2\n" +
            "===================================\n" +
            "\n" +
            "\n";

        ConfigurationSection genesSection = new YamlConfiguration();
        genesSection.set("public", new YamlConfiguration());
        genesSection.set("entityRelative", new YamlConfiguration());

        addDefault("genes", genesSection);
    }

    @Override
    public void doReloadConfig() {
        ConfigurationSection publicGenesSection = config.getConfigurationSection("genes.public");

        if (publicGenesSection == null) {
            Genetics.throwException("You need to configure your configuration! You can reset config by deleting it", true);
            return;
        }

        publicGenesSection.getKeys(false).forEach((gene) -> {
            List<Action> dominantActions = publicGenesSection.getStringList(gene+".dominant").stream().map(this::createAction).collect(Collectors.toList());
            List<Action> recessiveActions = publicGenesSection.getStringList(gene+".recessive").stream().map(this::createAction).collect(Collectors.toList());

            new Gene(gene, dominantActions, recessiveActions, publicGenesSection.getDouble(gene+".chance"), publicGenesSection.getDouble(gene+".dominantChance"), null);
        });

        ConfigurationSection perEntityGenesSection = config.getConfigurationSection("genes.entityRelative");
        if (perEntityGenesSection == null) {
            Genetics.throwException("You need to configure your configuration! You can reset config by deleting it", true);
            return;
        }

        perEntityGenesSection.getKeys(false).forEach((entity) -> {
            EntityType type = null;
            try {
                 type = EntityType.valueOf(entity.toUpperCase());
            } catch (IllegalArgumentException exception) {
                Genetics.throwException("Entity type "+entity+" is invalid. All entity types you can find at: https://jd.papermc.io/paper/1.20/org/bukkit/entity/EntityType.html", true);
            }

            if (type == null) return;

            ConfigurationSection entitySection = perEntityGenesSection.getConfigurationSection(entity);
            if (entitySection == null) {
                Genetics.throwException("This error is impossible to exists. Contact with developer. Code: ENTITY_SECTION_IS_NULL", true);
                return;
            }

            final EntityType finalType = type;
            entitySection.getKeys(false).forEach((gene) -> {
                List<Action> dominantActions = publicGenesSection.getStringList(gene+".dominant").stream().map(this::createAction).collect(Collectors.toList());
                List<Action> recessiveActions = publicGenesSection.getStringList(gene+".recessive").stream().map(this::createAction).collect(Collectors.toList());

                new Gene(gene, dominantActions, recessiveActions, publicGenesSection.getDouble(gene+".chance"), publicGenesSection.getDouble(gene+".dominantChance"), finalType);
            });
        });
    }

    private Action createAction(String rawAction) {
        String[] split = rawAction.split("\\|");
        String[] forConstructor = Arrays.stream(split).collect(Collectors.toList()).subList(1,2).toArray(new String[2]);

        switch (split[0]) {
            case "attribute":
                return new AttributeAction(forConstructor);
            case "effect":
                return new EffectAction(forConstructor);
        }
        throw new IllegalArgumentException(split[0]+" is not valid action type");
    }

    @Override
    public void doSave() {
        // There's nothing to save.
    }
}
