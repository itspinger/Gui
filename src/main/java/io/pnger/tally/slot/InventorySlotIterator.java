package io.pnger.tally.slot;

import io.pnger.tally.TallyInventory;
import io.pnger.tally.item.IntelligentItem;

import java.util.Optional;

/**
 * This interface is often used when we want to iterate through our {@link TallyInventory},
 * and set a new {@link IntelligentItem} in certain positions.
 * <p>
 * A new addition to this class inserts a way to blacklist specific rows or columns.
 *
 * @since 1.0.0
 */

public interface InventorySlotIterator {

    /**
     * Returns the current row of the slot iterator.
     *
     * @return the row
     */

    int getRow();

    /**
     * This method jumps the iterator to a new specific row.
     *
     * @param row the row
     */

    void setRow(int row);

    /**
     * Returns the current column of the slot iterator.
     *
     * @return the column
     */

    int getColumn();

    /**
     * This method jumps the iterator to a new specific column.
     *
     * @param column the column
     */

    void setColumn(int column);

    /**
     * This method returns the item that is located in the {@link #getRow()} and {@link #getColumn()} position.
     * <p>
     * Since there is a big chance that the item is null,
     * the method now uses an optional class.
     *
     * @return the item
     */

    Optional<IntelligentItem> getItem();

    /**
     * This method sets an item to a current row and column position.
     *
     * @param item the item
     * @return the slot iterator
     */

    InventorySlotIterator setItem(IntelligentItem item);

    /**
     * This method increases the iterator by one.
     *
     * @return the iterator
     */

    InventorySlotIterator next();

    /**
     * This method decreases the iterator by one.
     *
     * @return the iterator
     */

    InventorySlotIterator previous();

    /**
     * This method blacklists a certain position, so it is skipped by the iterator.
     * <p>
     * This method is a very useful method when we want to disable certain indexes in the inventory from being used.
     *
     * @param row the row
     * @param column the column
     */

    void blacklist(int row, int column);

    /**
     * This method blacklists an entire row, which the iterator
     * will skip.
     *
     * @param row the row
     */

    void blacklistRow(int row);

    /**
     * This method blacklists an entire column, which the iterator
     * will skip.
     *
     * @param column the column
     */

    void blacklistColumn(int column);

    /**
     * This option prevents the iterator from overriding items in the inventory.
     * <p>
     * If it is set to false, the iterator will skip slots where the item has already been set.
     *
     * @return the override
     */

    boolean isOverride();

    /**
     * Set a value for the slot override.
     *
     * @param override the override
     */

    void setOverride(boolean override);

    /**
     * Whether the iterator has already passed the last element.
     *
     * @return whether it has passed the element
     */

    boolean isLast();

}

