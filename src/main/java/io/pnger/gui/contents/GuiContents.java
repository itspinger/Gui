package io.pnger.gui.contents;

import io.pnger.gui.GuiInventory;
import io.pnger.gui.item.GuiItem;
import io.pnger.gui.pagination.GuiPagination;
import io.pnger.gui.slot.GuiIteratorType;
import io.pnger.gui.slot.GuiSlotIterator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

/**
 * This type holds all contents of a {@link GuiInventory}.
 * <p>
 * When updating items in the inventory, creating new {@link GuiSlotIterator iterators}, or
 * assigning property to a provider, this interface should be used.
 * <p>
 * This interface is persistent over every inventory, meaning that it's
 * persistent for every player.
 */

public interface GuiContents {

    /**
     * This method returns a matrix, od 2-dimensional array of all {@link GuiItem}
     * or contents assigned for this inventory.
     *
     * @return the items
     */

    GuiItem[][] getItems();

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
     * This method returns a {@link GuiPagination pagination} instance created
     * for this content holder.
     * <p>
     * Use pagination for handling multiple pages, with different items set
     * per page. For more information about the pagination type, visit the
     * {@link GuiPagination} page.
     *
     * @return the pagination
     */

    GuiPagination getPagination();

    /**
     * This method creates a new iterator with a custom id and saves it.
     *
     * @param id the id of the iterator
     * @param type the iterator type
     * @param startRow the start row
     * @param startColumn the start column
     * @return the iterator
     */

    GuiSlotIterator newIterator(String id, GuiIteratorType type, int startRow, int startColumn);

    /**
     * This method creates a new iterator with the specified iterator type and starting position.
     *
     * @param type the iterator type
     * @param startRow the start row
     * @param startColumn the start column
     * @return the iterator
     */

    GuiSlotIterator newIterator(GuiIteratorType type, int startRow, int startColumn);

    /**
     * This method returns a {@link GuiItem} in the specific row and column, if present.
     *
     * @param row the row
     * @param column the column
     * @return the item if present
     */

    Optional<GuiItem> getItem(int row, int column);

    /**
     * This method returns an {@link ItemStack} in the specific row and column, if present.
     *
     * @param row the row
     * @param column the column
     * @return the item if present
     */

    Optional<ItemStack> getItemStack(int row, int column);

    /**
     * This method returns an {@link ItemStack} in the specific row and column, if present.
     * <p>
     * If you want to find an item in the specific row and column, without having to calculate
     * the slot yourself, use the {@link #getItemStack(int, int)} method, which does this
     * for you.
     *
     * @param slot the slot
     * @return the item if present
     */

    Optional<ItemStack> getItemStack(int slot);

    /**
     * This method sets a new {@link GuiItem} to a specific row and column.
     *
     * @param row the row
     * @param column the column
     * @param item the item
     */

    void setItem(int row, int column, GuiItem item);

    /**
     * This method creates a new {@link GuiItem} from the specified stack.
     *
     * @param stack the stack
     * @return the item created
     */

    default GuiItem createItem(ItemStack stack) {
        return GuiItem.of(stack);
    }

    /**
     * This method adds the specified {@link GuiItem} in the last unused slot
     * in the inventory.
     *
     * @param item the item to add
     */

    void addItem(GuiItem item);

    /**
     * This method fills the entire inventory with the specified item.
     * <p>
     * By default, the GuiItems are air, and should not be set, unless you need
     * some different behaviour even from the empty slots.
     *
     * @param item the item to fill the gui with
     */

    void fill(GuiItem item);

    /**
     * This method fills an entire row with a specific item.
     *
     * @param item the item
     */

    void fillRow(int row, GuiItem item);

    /**
     * This method fills a specific column with a specific item.
     *
     * @param column the column that is getting filled
     * @param item the item to fill with
     */

    void fillColumn(int column, GuiItem item);

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
     * <p>
     * For setting and getting specific properties, check these methods out:
     * <ul>
     *     <li>{@link #getProperty(String, Object)}</li>
     *     <li>{@link #setProperty(String, Object)}</li>
     * </ul>
     *
     * @return the properties
     */

    Map<String, Object> getProperties();

}
