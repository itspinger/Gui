package io.pnger.tally;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import io.pnger.tally.contents.InventoryContents;
import io.pnger.tally.contents.entity.IntelligentInventoryContents;
import io.pnger.tally.manager.InventoryManager;
import io.pnger.tally.opener.IntelligentInventoryOpener;
import io.pnger.tally.provider.InventoryProvider;

import java.util.Optional;

public class TallyInventory {

    protected String title;
    protected InventoryType type;
    protected boolean closeable;
    protected int rows, columns;
    protected TallyInventory parent;
    protected final InventoryProvider provider;
    protected final InventoryManager manager;
    protected InventoryContents contents;
    protected Inventory inventory;

    /**
     * This constructor creates a new {@link TallyInventory} with the settings
     * provided.
     * <p>
     * Each method below explains what it does briefly. For building inventories,
     * use the {@link InventoryBuilder} class.
     *
     * @param manager the manager
     * @param provider the provider for this inventory
     */

    TallyInventory(InventoryManager manager, InventoryProvider provider) {
        this.manager = manager;
        this.provider = provider;
    }

    /**
     * This method opens the specified page of the inventory to a player.
     *
     * @param o the player object
     * @param page the page
     * @return the inventory
     */

    public Inventory open(Object o, int page) {
        if (!(o instanceof Player))
            throw new IllegalArgumentException("Inventories may only be opened to players.");

        return this.open((Player) o, page);
    }

    /**
     * This method opens the first page of the intelligent inventory to a player.
     *
     * @param o the player object
     * @return the inventory
     */

    public Inventory open(Object o) {
        if (!(o instanceof Player))
            throw new IllegalArgumentException("Inventories may only be opened to players.");

        return this.open((Player) o);
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
            return this.inventory = inv;
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
        this.manager.removePlayer(player);
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

    public InventoryManager getManager() {
        return manager;
    }

    /**
     * This method returns the provider that is responsible for the connection
     * between the inventory and the {@link InventoryContents} object.
     *
     * @return the provider
     */

    public InventoryProvider getProvider() {
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
     * Returns the parent {@link TallyInventory} object of this inventory.
     *
     * @return the parent inventory
     */

    public Optional<TallyInventory> getParent() {
        return Optional.ofNullable(this.parent);
    }

    /**
     * Changes the value of the closable
     *
     * @param closeable the closable
     */

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    /**
     * Returns the inventory.
     *
     * @return the inventory
     */

    public Inventory getInventory() {
        return inventory;
    }
}
