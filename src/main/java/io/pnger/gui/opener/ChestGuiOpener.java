package io.pnger.gui.opener;

import com.google.common.base.Preconditions;
import io.pnger.gui.GuiInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class ChestGuiOpener implements GuiOpener {

    @Override
    public Inventory open(Player player, GuiInventory inventory) {
        Preconditions.checkArgument(
            inventory.getColumns() == 9,
            "The number of columns for %s type must be 9",
            inventory.getType()
        );

        Preconditions.checkArgument(inventory.getRows() >= 1 && inventory.getRows() <= 6, "The number of rows for this type must be between 1 and 6");

        Inventory open = Bukkit.createInventory(player, inventory.getRows() * inventory.getColumns(), inventory.getTitle());
        this.fill(open, inventory.getContents());
        player.openInventory(open);

        return open;
    }

    @Override
    public boolean isSupported(InventoryType type) {
        return type == InventoryType.CHEST || type == InventoryType.ENDER_CHEST;
    }
}
