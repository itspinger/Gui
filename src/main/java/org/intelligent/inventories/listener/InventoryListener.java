package org.intelligent.inventories.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.intelligent.inventories.manager.IntelligentManager;

public class InventoryListener implements Listener {

    private final IntelligentManager manager;

    public InventoryListener(IntelligentManager manager) {
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
    public void onOpen(InventoryOpenEvent event) {

    }

}
