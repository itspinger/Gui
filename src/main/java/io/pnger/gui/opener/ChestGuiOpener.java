package io.pnger.gui.opener;

import io.pnger.gui.GuiInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class ChestGuiOpener implements GuiOpener {

    @Override
    public Inventory open(Player player, GuiInventory gui) {
        // Check if the inventory can be created
        this.checkValidInventory(gui);

        // Create the inventory if valid
        Inventory open = Bukkit.createInventory(player, gui.getRows() * gui.getColumns(), gui.getTitle());
        this.fill(open, gui.getContents());
        player.openInventory(open);

        return open;
    }

    @Override
    public boolean isSupported(InventoryType type) {
        return type == InventoryType.CHEST || type == InventoryType.ENDER_CHEST;
    }

    private void checkValidInventory(GuiInventory gui) {
        if (gui.getColumns() != 9) {
            throw new IllegalArgumentException("The number of columns must be 9!");
        }

        if (gui.getRows() < 1 || gui.getColumns() > 6) {
            throw new IllegalArgumentException("The number of rows must be between 1 and 6!");
        }
    }
}
