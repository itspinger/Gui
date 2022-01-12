package org.intelligent.inventories.contents;

import org.intelligent.inventories.item.IntelligentItem;

/**
 * This interface is often used when we want to iterate through our {@link org.intelligent.inventories.IntelligentInventory},
 * and set a new {@link org.intelligent.inventories.item.IntelligentItem} in certain positions.
 * <p>
 * A new addition to this class inserts a way to blacklist specific rows or columns.
 *
 * @since 1.0.0
 */

public interface InventorySlotIterator {

    InventorySlotIterator set(IntelligentItem item);

}

