package org.intelligent.inventories.provider;

import org.bukkit.entity.Player;
import org.intelligent.inventories.contents.InventoryContents;

public interface IntelligentProvider {

    /**
     * This method is invoked when the inventory is firstly opened.
     *
     * @param p the player
     * @param contents the contents of the inventory
     */

    void initialize(Player p, InventoryContents contents);

    /**
     * This method is invoked every {@link org.intelligent.inventories.IntelligentInventory#getUpdate()} ticks when the inventory is supposed to update.
     * <p>
     * Note that this method is not necessary, unlike {@link #initialize(Player, InventoryContents)}.
     *
     * @param p the player
     * @param contents the contents of the inventory
     */

    default void update(Player p, InventoryContents contents) {

    }

}
