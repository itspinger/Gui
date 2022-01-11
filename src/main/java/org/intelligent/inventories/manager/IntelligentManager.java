package org.intelligent.inventories.manager;

import com.google.common.collect.Maps;
import org.bukkit.plugin.java.JavaPlugin;
import org.intelligent.inventories.IntelligentInventory;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
     * Returns the plugin that is running this api.
     * This may never be null, since the plugin wouldn't start otherwise.
     *
     * @return the plugin
     */

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
