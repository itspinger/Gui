package io.pnger.gui;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;
import io.pnger.gui.manager.GuiManager;
import io.pnger.gui.provider.GuiProvider;

public final class GuiBuilder {

    private String title = "";
    private InventoryType type = InventoryType.CHEST;
    private boolean closeable = true;
    private int rows = 6, columns = 9;
    private GuiInventory parent;

    private GuiProvider provider = null;
    private GuiManager manager = null;

    private GuiBuilder() {}

    public static GuiBuilder newBuilder() {
        return new GuiBuilder();
    }

    public GuiBuilder title(String title) {
        this.title = title;
        return this;
    }

    public GuiBuilder type(InventoryType type) {
        this.type = type;
        return this;
    }

    public GuiBuilder closeable(boolean closeable) {
        this.closeable = closeable;
        return this;
    }

    public GuiBuilder size(int rows, int columns) {
        this.rows = rows; this.columns = columns;
        return this;
    }

    public GuiBuilder parent(GuiInventory parent) {
        this.parent = parent;
        return this;
    }

    public GuiBuilder provider(GuiProvider provider) {
        this.provider = provider;
        return this;
    }

    public GuiBuilder manager(GuiManager manager) {
        this.manager = manager;
        return this;
    }

    public GuiInventory build() {
        // Manager and provider must both be non-null
        GuiManager manager = this.manager == null ?
                JavaPlugin.getPlugin(GuiPlugin.class).getIntelligentManager() : this.manager;

        if (manager == null)
            throw new IllegalArgumentException("The InventoryManager may not be null, make sure to initialize it");

        if (this.provider == null)
            throw new IllegalArgumentException("The IntelligentProvider has not been set.");

        GuiInventory inv = new GuiInventory(this.manager, this.provider);

        // Set the contents
        inv.closeable = this.closeable;
        inv.title = this.title;
        inv.type = this.type;
        inv.rows = this.rows;
        inv.columns = this.columns;
        inv.parent = this.parent;

        return inv;
    }

}
