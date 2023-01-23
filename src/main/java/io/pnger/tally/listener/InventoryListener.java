package io.pnger.tally.listener;

import io.pnger.tally.item.GuiItem;
import io.pnger.tally.manager.InventoryManager;
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

public class InventoryListener implements Listener {

    private final InventoryManager manager;

    public InventoryListener(InventoryManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onClose(InventoryCloseEvent event) {
        Player p = (Player) event.getPlayer();

        // Handle if the inv present
        this.manager.ifInventoryPresent(p, inv -> {
            if (!inv.isCloseable()) {
                Bukkit.getScheduler().runTask(this.manager.getPlugin(), () -> {
                    p.openInventory(event.getInventory());
                });

                return;
            }

            event.getInventory().clear();
            this.manager.removePlayer(p);
        });
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryDrag(InventoryDragEvent e) {
        Player p = (Player) e.getWhoClicked();

        this.manager.ifInventoryPresent(p, inv -> {
            for (int slot : e.getRawSlots()) {
                if (slot >= p.getOpenInventory().getTopInventory().getSize())
                    continue;

                e.setCancelled(true);
                break;
            }
        });
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.manager.ifInventoryPresent(e.getPlayer(), inv -> this.manager.removePlayer(e.getPlayer()));
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onPluginDisable(PluginDisableEvent e) {
        if (!e.getPlugin().equals(this.manager.getPlugin())) {
            return;
        }

        new HashMap<>(this.manager.getInventories()).forEach((player, inv) -> {
            inv.close(Bukkit.getPlayer(player));
        });

        this.manager.getInventories().clear();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        this.manager.ifInventoryPresent(p, inv -> {
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
