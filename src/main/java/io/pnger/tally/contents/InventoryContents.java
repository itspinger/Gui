package io.pnger.tally.contents;

import io.pnger.tally.GuiInventory;
import io.pnger.tally.item.IntelligentItem;
import io.pnger.tally.slot.InventorySlotIterator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

/**
 * This type holds all contents of a {@link GuiInventory}.
 * <p>
 * When updating items in the inventory, creating new {@link InventorySlotIterator iterators}, or
 * assigning property to a provider, this interface should be used.
 * <p>
 * This interface is persistent over every inventory, meaning that it's
 * persistent for every player.
 */

public interface InventoryContents {

    /**
     * This method returns the inventory that this content
     * is persistent for.
     * <p>
     * If you need a {@link Inventory bukkit inventory} instead,
     * you can use the {@link #getBukkitInventory()} method.
     *
     * @return the inventory
     */

    @Nonnull
    GuiInventory getInventory();

    /**
     * This method returns the {@link Inventory bukkit inventory} created when
     * the {@link GuiInventory} has been opened by the user.
     * <p>
     * The user of this method should not worry about this method returning null,
     * because this interface is only accessible once the inventory has been
     * opened and not before.
     *
     * @return the bukkit inventory
     */

    default Inventory getBukkitInventory() {
        return getInventory().getInventory();
    }

    /**
     * Returns the current pagination instance, which can then be changed
     * within the instance itself.
     *
     * @return the pagination
     */

    InventoryPagination getPagination();

    /**
     * Returns a deep copy of all items that are contained in this inventory.
     *
     * @return copy of items
     */

    IntelligentItem[][] getItems();

    /**
     * This method creates a new iterator with a custom id and saves it.
     *
     * @param id the id of the iterator
     * @param type the iterator type
     * @param startRow the start row
     * @param startColumn the start column
     * @return the iterator
     */

    InventorySlotIterator newIterator(String id, IteratorType type, int startRow, int startColumn);

    /**
     * This method creates a new iterator with the specified iterator type and starting position.
     *
     * @param type the iterator type
     * @param startRow the start row
     * @param startColumn the start column
     * @return the iterator
     */

    InventorySlotIterator newIterator(IteratorType type, int startRow, int startColumn);

    /**
     * Returns an item that is located in a specific row and column of the inventory.
     *
     * @param row the row
     * @param column the column
     * @return the item
     */

    Optional<IntelligentItem> getItem(int row, int column);

    /**
     * Returns the item stack that is currently found under this inventory.
     *
     * @param row the row
     * @param column the column
     * @return the item
     */

    Optional<ItemStack> getItemStack(int row, int column);

    /**
     * Returns the item stack that is currently found in this slot.
     *
     * @param slot the slot that we want to search
     * @return the item
     */

    Optional<ItemStack> getItemStack(int slot);

    /**
     * Applies a new value of the {@link IntelligentItem} to a specific row and column.
     *
     * @param row the row
     * @param column the column
     * @param item the item
     */

    void setItem(int row, int column, IntelligentItem item);

    /**
     * Adds the {@link IntelligentItem} to the last unused spot of the inventory.
     *
     * @param item the item
     */

    void addItem(IntelligentItem item);

    /**
     * This method fills the entire inventory with the item specified in the arguments.
     * <p>
     * We may choose to fill the whole inventory with
     *
     * @param item the item
     */

    void fill(IntelligentItem item);

    /**
     * This method fills an entire row with a specific item.
     *
     * @param item the item
     */

    void fillRow(int row, IntelligentItem item);

    /**
     * This method fills a specific column with a specific item.
     *
     * @param column the column that is getting filled
     * @param item the item to fill with
     */

    void fillColumn(int column, IntelligentItem item);

    /**
     * This method returns a property value that is connected to the key.
     * <p>
     * If either the key or the value is null, the value returned will be the default one or null.
     *
     * @param key the key
     * @param def the default value
     * @param <V> the type
     * @return the property instance
     */

    <V> V getProperty(String key, V def);

    /**
     * This method is used to set a property, which is useful for keeping track
     * of data that may be used within the inventory, without having the need to
     * save it in a separate data structure.
     *
     * @param key the key of the property
     * @param value the value it holds
     * @param <V> the type of the value
     */

    <V> void setProperty(String key, V value);

    /**
     * Returns all properties that are associated with this inventory.
     *
     * @return the properties
     */

    Map<Object, Object> getProperties();

}
