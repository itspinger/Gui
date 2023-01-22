package io.pnger.tally;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;
import io.pnger.tally.manager.InventoryManager;
import io.pnger.tally.provider.InventoryProvider;

public final class InventoryBuilder {

    private String title = "";
    private InventoryType type = InventoryType.CHEST;
    private boolean closeable = true;
    private int rows = 6, columns = 9;
    private TallyInventory parent;

    private InventoryProvider provider = null;
    private InventoryManager manager = null;

    private InventoryBuilder() {}

    public static InventoryBuilder newBuilder() {
        return new InventoryBuilder();
    }

    public InventoryBuilder title(String title) {
        this.title = title;
        return this;
    }

    public InventoryBuilder type(InventoryType type) {
        this.type = type;
        return this;
    }

    public InventoryBuilder closeable(boolean closeable) {
        this.closeable = closeable;
        return this;
    }

    public InventoryBuilder size(int rows, int columns) {
        this.rows = rows; this.columns = columns;
        return this;
    }

    public InventoryBuilder parent(TallyInventory parent) {
        this.parent = parent;
        return this;
    }

    public InventoryBuilder provider(InventoryProvider provider) {
        this.provider = provider;
        return this;
    }

    public InventoryBuilder manager(InventoryManager manager) {
        this.manager = manager;
        return this;
    }

    public TallyInventory build() {
        // Manager and provider must both be non-null
        InventoryManager manager = this.manager == null ?
                JavaPlugin.getPlugin(TallyPlugin.class).getIntelligentManager() : this.manager;

        if (manager == null)
            throw new IllegalArgumentException("The InventoryManager may not be null, make sure to initialize it");

        if (this.provider == null)
            throw new IllegalArgumentException("The IntelligentProvider has not been set.");

        TallyInventory inv = new TallyInventory(this.manager, this.provider);

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
