package io.pnger.tally.contents;

import io.pnger.tally.GuiInventory;
import io.pnger.tally.item.IntelligentItem;
import io.pnger.tally.slot.InventorySlotIterator;

public interface InventoryPagination {

    /**
     * Returns an array of items that are currently in this page.
     *
     * @return the page
     */

    IntelligentItem[] getItemsInPage();

    /**
     * This method is used to set the new pagination of the {@link GuiInventory}.
     *
     * @param page the page
     * @return the pagination
     */

    InventoryPagination setPage(int page);

    /**
     * Returns the current page a viewer is on.
     *
     * @return the current page
     */

    int getPage();

    /**
     * Returns whether the pagination is the first.
     * <p>
     * If an inventory only has 1 page, then this is still true.
     *
     * @return if it's the first
     */

    boolean isFirst();

    /**
     * Returns whether the pagination is the last.
     * <p>
     * If an inventory only has 1 page, then this is still true.
     *
     * @return if it's the last
     */

    boolean isLast();

    /**
     * This method sets the current page to the first page, if not already at first.
     *
     * @return the first page
     */

    InventoryPagination firstPage();

    /**
     * This method sets the current page to the last page of the pagination.
     *
     * @return the last page
     */

    InventoryPagination lastPage();

    /**
     * This method decreases the current pagination by 1, if possible.
     *
     * @return the pagination
     */

    InventoryPagination previousPage();

    /**
     * This method increases the current pagination by 1, if possible.
     *
     * @return the pagination
     */

    InventoryPagination nextPage();

    /**
     * This method adds all items from {@link #getItemsInPage()} to the appropriate
     * {@link InventorySlotIterator} provided in the arguments.
     *
     * @param iterator the iterator
     * @return the inventory pagination
     */

    InventoryPagination addToIterator(InventorySlotIterator iterator);

    /**
     * This method sets all items that are cached within all paginations.
     *
     * @param items the items
     * @return the pagination
     */

    InventoryPagination setItems(IntelligentItem... items);

    /**
     * Set how many of items set in the method {@link #setItems(IntelligentItem...)} will be cached per page.
     *
     * @param itemsPerPage the number of items
     * @return the pagination
     */

    InventoryPagination setItemsPerPage(int itemsPerPage);



}
