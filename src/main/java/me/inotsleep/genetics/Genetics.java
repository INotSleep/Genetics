package me.inotsleep.genetics;

import me.inotsleep.utils.AbstractPlugin;

public final class Genetics extends AbstractPlugin {
    public static Config config;

    @Override
    public void doDisable() {

    }

    @Override
    public void doEnable() {
        config = new Config(this);
    }
}
