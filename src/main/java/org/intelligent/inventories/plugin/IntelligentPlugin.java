package org.intelligent.inventories.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class IntelligentPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().info("Successfully found the IntelligentPlugin dependency.");
    }
}
