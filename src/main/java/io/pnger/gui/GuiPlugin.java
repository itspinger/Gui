package io.pnger.gui;

import io.pnger.gui.manager.GuiManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiPlugin extends JavaPlugin {

    private GuiManager manager;

    @Override
    public void onEnable() {
        this.manager = new GuiManager(this);
    }

    public GuiManager getManager() {
        return this.manager;
    }
}
