package io.pnger.tally.opener;

import io.pnger.tally.TallyInventory;
import io.pnger.tally.item.IntelligentItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import io.pnger.tally.contents.InventoryContents;

public interface IntelligentInventoryOpener {

    /**
     * This method tries to open an intelligent inventory to the specified player.
     *
     * @param player the player
     * @param inventory the inventory
     * @return the inventory that is being opened
     */

    Inventory open(Player player, TallyInventory inventory);

    /**
     * This method checks if a {@link InventoryType} type is supported within this opener.
     *
     * @param type the inventory type
     * @return if it is supported
     */

    boolean isSupported(InventoryType type);

    /**
     * This method is used to fill an inventory with the contents that are defined inside the {@link InventoryContents} object.
     *
     * @param handle the inventory that is being filled up
     * @param contents the contents of the inventory
     */

    default void fill(Inventory handle, InventoryContents contents) {
        IntelligentItem[][] items = contents.getItems();

        for(int row = 0; row < items.length; row++) {
            for(int column = 0; column < items[row].length; column++) {
                if(items[row][column] != null)
                    handle.setItem(9 * row + column, items[row][column].getItem());
            }
        }
    }

}
