package org.intelligent.inventories.manager;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.intelligent.inventories.IntelligentInventory;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class IntelligentManager {

    // The plugin that is running this api
    private final JavaPlugin plugin;

    // Inventory of each player
    private final Map<UUID, IntelligentInventory> inventories = Maps.newHashMap();

    public IntelligentManager(@Nonnull JavaPlugin plugin) {
        Objects.requireNonNull(plugin, "Plugin must not be null.");
        this.plugin = plugin;
    }

    /**
     * Returns the current inventory of this player, if it exists.
     *
     * @param p the player
     * @return the current inventory
     */

    public Optional<IntelligentInventory> getInventory(Player p) {
        return Optional.ofNullable(this.inventories.get(p.getUniqueId()));
    }

    /**
     * Performs an action if the specified player has an inventory opened.
     *
     * @param p the player
     * @param inventory the inventory consumer
     */

    public void ifInventoryPresent(Player p, Consumer<IntelligentInventory> inventory) {
        if (!this.inventories.containsKey(p.getUniqueId()))
            return;

        IntelligentInventory intelligentInventory = this.inventories.get(p.getUniqueId());
        if (intelligentInventory == null)
            return;

        inventory.accept(intelligentInventory);
    }

    /**
     * Returns the plugin that is running this api.
     * This may never be null, since the plugin wouldn't start otherwise.
     *
     * @return the plugin
     */

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
