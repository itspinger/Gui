package org.intelligent.inventories.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.intelligent.inventories.manager.IntelligentManager;

public class IntelligentPlugin extends JavaPlugin {

    private IntelligentManager intelligentManager;

    @Override
    public void onEnable() {
        this.getLogger().info("Successfully found the IntelligentPlugin dependency.");
        this.intelligentManager = new IntelligentManager(this);
    }

    public IntelligentManager getIntelligentManager() {
        return intelligentManager;
    }
}
