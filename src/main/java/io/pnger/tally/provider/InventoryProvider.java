package io.pnger.tally.provider;

import io.pnger.tally.contents.GuiContents;
import io.pnger.tally.manager.InventoryManager;
import org.bukkit.entity.Player;

public interface InventoryProvider {

    /**
     * This method is initializing method for an inventory. It is being executed
     * right after the inventory has been opened for the player, so any
     * important caches should go inside this method.
     *
     * @param player the player to open the inventory for
     * @param contents the contents of the opened inventory
     */

    default void initialize(Player player, GuiContents contents) {}

    /**
     * This method is called every {@link InventoryManager#UPDATE_INTERVAL} ticks.
     * <p>
     * Using this method is necessary when items are often being removed,
     * added from the inventory.
     *
     * @param player the player
     * @param contents the contents of the opened inventory
     */

    default void update(Player player, GuiContents contents) {}

}
