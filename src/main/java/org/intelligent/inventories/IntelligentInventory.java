package org.intelligent.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.intelligent.inventories.contents.InventoryContents;
import org.intelligent.inventories.contents.entity.IntelligentInventoryContents;
import org.intelligent.inventories.manager.IntelligentManager;
import org.intelligent.inventories.opener.IntelligentInventoryOpener;
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

    /**
     * This method opens the specified page of the inventory to a player.
     *
     * @param player the player
     * @return the inventory
     */

    public Inventory open(Player player, int page) {
        // First close the old inventory
        this.close(player);

        InventoryContents contents = new IntelligentInventoryContents(this, player.getUniqueId());
        contents.getPagination().setPage(page);

        // Assign it
        this.contents = contents;

        try {
            this.provider.initialize(player, this.contents);

            if (!this.contents.equals(contents)) {
                return null;
            }

            IntelligentInventoryOpener opener = this.manager.findOpener(this.type)
                    .orElseThrow(() -> new IllegalArgumentException("No openers found for this inventory type: " + this.type.name()));
            Inventory inv = opener.open(player, this);

            this.manager.setInventory(player, this);
            return inv;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to open the inventory for a player. ", e);
        }
    }

    /**
     * This method opens the first page of the intelligent inventory to a player.
     *
     * @param player the player
     * @return the inventory
     */

    public Inventory open(Player player) {
        return this.open(player, 0);
    }

    /**
     * This method closes an intelligent inventory for a specific player.
     *
     * @param player the player that the inventory is being closed on
     */

    public void close(Player player) {
        this.manager.setInventory(player, null);
        this.contents = null;
        player.closeInventory();
    }

    /**
     * Returns whether the intelligent inventory is closeable.
     *
     * @return whether the inventory is closeable
     */

    public boolean isCloseable() {
        return closeable;
    }

    /**
     * Returns the manager of this inventory.
     *
     * @return the manager
     */

    public IntelligentManager getManager() {
        return manager;
    }

    /**
     * This method returns the provider that is responsible for the connection
     * between the inventory and the {@link InventoryContents} object.
     *
     * @return the provider
     */

    public IntelligentProvider getProvider() {
        return provider;
    }

    /**
     * Returns the {@link InventoryType} of this inventory.
     *
     * @return the type
     */

    public InventoryType getType() {
        return type;
    }

    /**
     * Returns the title of this inventory.
     *
     * @return the title
     */

    public String getTitle() {
        return title;
    }

    /**
     * Returns the update interval of this inventory.
     *
     * @return the update interval
     */

    public long getUpdate() {
        return update;
    }

    /**
     * Returns the amount of columns of this inventory.
     *
     * @return the amount of columns
     */

    public int getColumns() {
        return columns;
    }

    /**
     * Returns the amount of rows of this inventory.
     *
     * @return the amount of rows
     */

    public int getRows() {
        return rows;
    }

    /**
     * Returns an instance of the contents object.
     *
     * @return the contents object
     */

    public InventoryContents getContents() {
        return contents;
    }

    /**
     * Returns the parent {@link IntelligentInventory} object of this inventory.
     *
     * @return the parent inventory
     */

    public Optional<IntelligentInventory> getParent() {
        return Optional.ofNullable(this.parent);
    }
}
