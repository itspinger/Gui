package io.pnger.gui;

import io.pnger.gui.manager.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiPlugin extends JavaPlugin {

    private InventoryManager intelligentManager;

    @Override
    public void onEnable() {
        this.getLogger().info("Tally is enabled successfully. Will try to enable the tally manager now.");
        this.intelligentManager = new InventoryManager(this);
        this.getLogger().info("Successfully enabled Tally. You can now manage your inventories");
    }

    public InventoryManager getIntelligentManager() {
        return intelligentManager;
    }
}
