package me.inotsleep.genetics;

import me.inotsleep.utils.AbstractPlugin;
import org.bukkit.Bukkit;

public final class Genetics extends AbstractPlugin {
    public static Config config;

    @Override
    public void doDisable() {

    }

    @Override
    public void doEnable() {
        config = new Config(this);
    }

    public static void throwException(String message, boolean disable) {
        getPluginLogger().severe(message);
        if (disable && !Bukkit.getPluginManager().isPluginEnabled(getInstance())) Bukkit.getPluginManager().disablePlugin(getInstance());
    }
}
