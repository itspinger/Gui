package io.pnger.tally.provider;

import io.pnger.tally.IntelligentInventory;
import org.bukkit.entity.Player;
import io.pnger.tally.contents.InventoryContents;

public interface InventoryProvider {

    /**
     * This method is invoked when the inventory is firstly opened.
     *
     * @param p the player
     * @param contents the contents of the inventory
     */

    default void initialize(Player p, InventoryContents contents) {}

    /**
     * This method is invoked every {@link IntelligentInventory#getUpdate()} ticks when the inventory is supposed to update.
     * <p>
     * Note that this method is not necessary, unlike {@link #initialize(Player, InventoryContents)}.
     *
     * @param p the player
     * @param contents the contents of the inventory
     */

    default void update(Player p, InventoryContents contents) {}

}
