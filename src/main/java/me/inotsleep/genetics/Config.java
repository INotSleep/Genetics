package me.inotsleep.genetics;

import me.inotsleep.utils.AbstractConfig;
import me.inotsleep.utils.AbstractPlugin;

public class Config extends AbstractConfig {
    public Config(AbstractPlugin plugin) {
        super(plugin, "config.yml");
    }

    @Override
    public void addDefaults() {

    }

    @Override
    public void doReloadConfig() {

    }

    @Override
    public void doSave() {

    }
}
