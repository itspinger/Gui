package io.pnger.gui.opener;

import io.pnger.gui.GuiInventory;
import io.pnger.gui.item.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import io.pnger.gui.contents.GuiContents;

public interface GuiOpener {

    /**
     * This method tries to open an intelligent inventory to the specified player.
     *
     * @param player the player
     * @param inventory the inventory
     * @return the inventory that is being opened
     */

    Inventory open(Player player, GuiInventory inventory);

    /**
     * This method checks if a {@link InventoryType} type is supported within this opener.
     *
     * @param type the inventory type
     * @return if it is supported
     */

    boolean isSupported(InventoryType type);

    /**
     * This method is used to fill an inventory with the contents
     * that are defined inside the {@link GuiContents} object.
     *
     * @param handle the inventory that is being filled up
     * @param contents the contents of the inventory
     */

    default void fill(Inventory handle, GuiContents contents) {
        GuiItem[][] items = contents.getItems();

        for (int row = 0; row < items.length; row++) {
            for (int column = 0; column < items[row].length; column++) {
                if (items[row][column] != null) {
                    handle.setItem(9 * row + column, items[row][column].getItem());
                }
            }
        }
    }

}
