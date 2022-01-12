package org.intelligent.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.intelligent.inventories.contents.InventoryContents;
import org.intelligent.inventories.manager.IntelligentManager;
import org.intelligent.inventories.provider.IntelligentProvider;

import java.util.Optional;

public class IntelligentInventory {

    // The title of this inventory
    protected String title;

    // The inventory type of this inventory
    protected InventoryType type;

    // Whether this inventory should be closeable
    protected boolean closeable;

    // The number of rows and columns for this inventory
    protected int rows, columns;

    // The update time
    protected long update;

    // The parent inventory of this inventory
    protected IntelligentInventory parent;

    // The inventory provider of this inventory
    protected final IntelligentProvider provider;

    // The intelligent manager of this inventory
    protected final IntelligentManager manager;

    // The contents of this inventory
    protected InventoryContents contents;

    IntelligentInventory(IntelligentManager manager, IntelligentProvider provider) {
        this.manager = manager;
        this.provider = provider;
    }

    public Inventory open(Player player, int page) {
        // First close the old inventory
        InventoryContents contents =
    }

    public boolean isCloseable() {
        return closeable;
    }

    public IntelligentManager getManager() {
        return manager;
    }

    public IntelligentProvider getProvider() {
        return provider;
    }

    public InventoryType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public long getUpdate() {
        return update;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public InventoryContents getContents() {
        return contents;
    }

    public Optional<IntelligentInventory> getParent() {
        return Optional.ofNullable(this.parent);
    }
}
