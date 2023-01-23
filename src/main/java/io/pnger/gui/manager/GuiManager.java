package io.pnger.gui.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.pnger.gui.GuiInventory;
import io.pnger.gui.listener.GuiListener;
import io.pnger.gui.opener.ChestInventoryOpener;
import io.pnger.gui.opener.IntelligentInventoryOpener;
import io.pnger.gui.opener.SpecialInventoryOpener;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;

public class GuiManager {

    public static final int UPDATE_INTERVAL = 1;

    private final JavaPlugin plugin;
    private final Map<UUID, GuiInventory> inventories = Maps.newHashMap();

    // Openers
    private final List<IntelligentInventoryOpener> openers =
            Lists.newArrayList(new ChestInventoryOpener(), new SpecialInventoryOpener());

    public GuiManager(@Nonnull JavaPlugin plugin) {
        Objects.requireNonNull(plugin, "Plugin must not be null.");
        this.plugin = plugin;

        // Register the event
        Bukkit.getPluginManager().registerEvents(new GuiListener(this), plugin);

        // Update every inventory
        new BukkitRunnable() {
            @Override
            public void run() {
                new HashMap<>(GuiManager.this.inventories).forEach((id, inv) -> {
                    Player p = Bukkit.getPlayer(id);

                    // Update the inventory for the specified player
                    try {
                        inv.getProvider().update(p, inv.getContents());
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Failed to update the inventory of the player. ", e);
                    }
                });
            }
        }.runTaskTimer(plugin, 1L, GuiManager.UPDATE_INTERVAL);
    }

    /**
     * This method returns all players that currently have
     * this specific inventory opened.
     *
     * @param inventory the inventory that is opened
     * @return the players that have this inventory opened
     */

    public List<Player> getOpened(GuiInventory inventory) {
        List<Player> players = Lists.newArrayList();

        this.inventories.forEach((id, inv) -> {
            if (!inv.equals(inventory)) {
                return;
            }

            // Just a safe check
            if (Bukkit.getPlayer(id) == null)
                return;

            players.add(Bukkit.getPlayer(id));
        });

        return players;
    }


    /**
     * Returns the current inventory of this player, if it exists.
     *
     * @param p the player
     * @return the current inventory
     */

    public Optional<GuiInventory> getInventory(Player p) {
        return Optional.ofNullable(this.inventories.get(p.getUniqueId()));
    }

    /**
     * Performs an action if the specified player has an inventory opened.
     *
     * @param p the player
     * @param inventory the inventory consumer
     */

    public void handleInventory(Player p, Consumer<GuiInventory> inventory) {
        this.getInventory(p).ifPresent(inventory);
    }

    /**
     * This method finds an inventory opener that corresponds to this inventory type.
     *
     * @param type the inventory type
     * @return the inventory opener
     */

    public Optional<IntelligentInventoryOpener> findOpener(InventoryType type) {
        return this.openers.stream()
                .filter(opener -> opener.isSupported(type))
                .findAny();
    }

    /**
     * This method sets the inventory of the specified player.
     *
     * @param player the player
     * @param inventory the inventory
     */

    public void setInventory(Player player, GuiInventory inventory) {
        this.inventories.put(player.getUniqueId(), inventory);
    }

    /**
     * Removes this player from the both inventories and the contents map.
     * <p>
     * This method should never be used unless you are absolutely sure what it does.
     *
     * @param p the player
     */

    public void removePlayer(Player p) {
        this.inventories.remove(p.getUniqueId());
    }

    /**
     * Returns the plugin that is running this api.
     * This may never be null, since the plugin wouldn't start otherwise.
     *
     * @return the plugin
     */

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    /**
     * Returns the inventories of all players.
     *
     * @return the inventories
     */

    public Map<UUID, GuiInventory> getInventories() {
        return inventories;
    }
}
