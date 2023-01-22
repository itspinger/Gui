package io.pnger.tally.plugin;

import io.pnger.tally.manager.IntelligentManager;
import org.bukkit.plugin.java.JavaPlugin;

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
