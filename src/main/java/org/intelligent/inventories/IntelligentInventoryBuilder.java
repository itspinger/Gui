package org.intelligent.inventories;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;
import org.intelligent.inventories.manager.IntelligentManager;
import org.intelligent.inventories.plugin.IntelligentPlugin;
import org.intelligent.inventories.provider.IntelligentProvider;

public final class IntelligentInventoryBuilder {

    private String title = "";
    private InventoryType type = InventoryType.CHEST;
    private boolean closeable = true;
    private long update = 1;
    private int rows = 6, columns = 9;
    private IntelligentInventory parent;

    private IntelligentProvider provider = null;
    private IntelligentManager manager = null;

    private IntelligentInventoryBuilder() {}

    public static IntelligentInventoryBuilder newBuilder() {
        return new IntelligentInventoryBuilder();
    }

    public IntelligentInventoryBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public IntelligentInventoryBuilder setInventoryType(InventoryType type) {
        this.type = type;
        return this;
    }

    public IntelligentInventoryBuilder setCloseable(boolean closeable) {
        this.closeable = closeable;
        return this;
    }

    public IntelligentInventoryBuilder setUpdateTime(long update) {
        this.update = update;
        return this;
    }

    public IntelligentInventoryBuilder setSize(int rows, int columns) {
        this.rows = rows; this.columns = columns;
        return this;
    }

    public IntelligentInventoryBuilder setParent(IntelligentInventory parent) {
        this.parent = parent;
        return this;
    }

    public IntelligentInventory build() {
        // Manager and provider must both be non-null
        IntelligentManager manager = this.manager == null ?
                JavaPlugin.getPlugin(IntelligentPlugin.class).getIntelligentManager() : this.manager;

        if (manager == null)
            throw new IllegalArgumentException("The InventoryManager may not be null, make sure to initialize it");

        if (this.provider == null)
            throw new IllegalArgumentException("The IntelligentProvider has not been set.");

        IntelligentInventory inv = new IntelligentInventory(this.manager, this.provider);

        // Set the contents
        inv.closeable = this.closeable;
        inv.title = this.title;
        inv.type = this.type;
        inv.update = this.update;
        inv.rows = this.rows;
        inv.columns = this.columns;
        inv.parent = this.parent;

        return inv;
    }

}
