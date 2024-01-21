package me.inotsleep.genetics;

import me.inotsleep.utils.AbstractConfig;
import me.inotsleep.utils.AbstractPlugin;

public class Config extends AbstractConfig {
    public Config(AbstractPlugin plugin) {
        super(plugin, "config.yml");
    }

    @Override
    public void addDefaults() {
/*
CONFIG FORMAT

genes:
  public:
    "A":
      dominant:
      - "attribute|GENERIC_MAX_HEALTH|5"
      recessive:
      - "attribute|GENERIC_MAX_HEALTH|0"
  entityRelative:
    "HORSE":
      "B":
        dominant:
        - "effect|REGENERATION|1"
        recessive: []
*/

    header= "Genes:" +
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
            "\n" +
            "";
    }

    @Override
    public void doReloadConfig() {

    }

    @Override
    public void doSave() {

    }
}
