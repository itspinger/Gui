package org.intelligent.inventories.builder;

import org.bukkit.event.inventory.InventoryType;
import org.intelligent.inventories.IntelligentInventory;

public final class IntelligentInventoryBuilder {

    private String title;
    private InventoryType type;
    private boolean closeable;

    private IntelligentInventoryBuilder() {}

    public static IntelligentInventoryBuilder newBuilder() {
        return new IntelligentInventoryBuilder();
    }

    public IntelligentInventoryBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public IntelligentInventory build() {
        return new IntelligentInventory();
    }

}
