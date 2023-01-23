package io.pnger.gui.listener;

import io.pnger.gui.item.GuiItem;
import io.pnger.gui.manager.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GuiListener implements Listener {

    private final GuiManager manager;

    public GuiListener(GuiManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onClose(InventoryCloseEvent event) {
        Player p = (Player) event.getPlayer();

        // Handle if the inv present
        this.manager.handleInventory(p, inv -> {
            if (!inv.isCloseable()) {
                // Don't allow the player to close the inventory
                // If closable is set to false
                Bukkit.getScheduler().runTask(this.manager.getPlugin(),
                    () -> p.openInventory(event.getInventory())
                );
                return;
            }

            event.getInventory().clear();
            this.manager.removePlayer(p);
        });
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryDrag(InventoryDragEvent e) {
        Player p = (Player) e.getWhoClicked();

        this.manager.handleInventory(p, inv -> {
            for (int slot : e.getRawSlots()) {
                if (slot >= p.getOpenInventory().getTopInventory().getSize())
                    continue;

                // This is not the method to handle drags
                // So drags that would go outside the bound of this inventory
                // Are disabled
                e.setCancelled(true);
                break;
            }
        });
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.manager.handleInventory(e.getPlayer(), inv -> this.manager.removePlayer(e.getPlayer()));
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onPluginDisable(PluginDisableEvent e) {
        if (!e.getPlugin().equals(this.manager.getPlugin())) {
            return;
        }

        // The inventory should only be removed for players
        // That are handled by the specified plugin from the manager
        new HashMap<>(this.manager.getInventories()).forEach((player, inv) -> inv.close(Bukkit.getPlayer(player)));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        this.manager.handleInventory(p, inv -> {
            // Restrict putting items from the bottom inventory into the top inventory
            Inventory clickedInventory = e.getClickedInventory();

            if (clickedInventory == p.getOpenInventory().getBottomInventory()) {
                if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    e.setCancelled(true);
                    return;
                }

                if (e.getAction() == InventoryAction.NOTHING && e.getClick() != ClickType.MIDDLE) {
                    e.setCancelled(true);
                    return;
                }
            }

            if (clickedInventory == p.getOpenInventory().getTopInventory()) {
                int row = e.getSlot() / 9;
                int column = e.getSlot() % 9;

                if (row < 0 || column < 0) {
                    e.setCancelled(true);
                    return;
                }

                if (row >= inv.getRows() || column >= inv.getColumns()) {
                    e.setCancelled(true);
                    return;
                }

                GuiItem item = inv.getContents().getItem(row, column).orElse(null);

                if (item == null) {
                    e.setCancelled(true);
                    return;
                }

                if (item.isDrag()) {
                    item.applyClick(e);
                    return;
                }

                e.setCancelled(true);
                item.applyClick(e);
            }
        });

    }
}
