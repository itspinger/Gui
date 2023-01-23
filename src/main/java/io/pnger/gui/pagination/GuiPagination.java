package io.pnger.gui.pagination;

import io.pnger.gui.contents.GuiContents;
import io.pnger.gui.item.GuiItem;
import io.pnger.gui.slot.InventorySlotIterator;

/**
 * This type is used for placing x amount of items in an inventory,
 * which usually exceeds the amount of items you can put in a single gui.
 * <p>
 * This interface handles all stuff mentioned above. The methods to look for are:
 * <ul>
 *     <li>{@link #setItems(int, GuiItem...)} - which will add all items to the pagination handle, and where you limit how many of these you want per page</li>
 *     <li>{@link #addToIterator(InventorySlotIterator)} - to set from which slot this iterator will start</li>
 * </ul>
 *
 * @since 2.0
 */

public interface GuiPagination {

    /**
     * This method adds the items set using the {@link #setItems(int, GuiItem...)} method
     * to an iterator.
     * <p>
     * This is often used for when we want to start paginating from a different slot,
     * not necessarily the first item (with index (0, 0)). Use the {@link GuiContents type}
     * to easily create iterators.
     *
     * @param iterator the iterator to set
     * @return this pagination
     */

    GuiPagination addToIterator(InventorySlotIterator iterator);

    /**
     * This method sets items that will be paginated through this pagination
     * handler, alongside with how many of these items should be displayed
     * per page.
     *
     * @param itemsPerPage how many items to display per page
     * @param items the items to paginate
     * @return this pagination
     */

    GuiPagination setItems(int itemsPerPage, GuiItem... items);

    /**
     * This method switches the page of this pagination.
     * <p>
     * Do note that by calling this method as of 1-23-2023, it
     * will not manually update the page for you. It is in plans that
     * when you call this method it automatically refills your inventory,
     * rather than opening it every time, but as of now, it's not working.
     *
     * @param page the page to set
     * @return this pagination
     */

    GuiPagination setPage(int page);

    /**
     * This method returns the page that is currently set
     * within this handler.
     * <p>
     * To compare it to the start or the end of the pagination,
     * use the {@link #isFirst()} or {@link #isLast()} methods.
     *
     * @return the current page
     */

    int getPage();

    /**
     * This method returns the amount of pages that are created
     * for this pagination.
     * <p>
     * This is automatically refreshed when the {@link #setItems(int, GuiItem...)}
     * is called.
     *
     * @return the amount of pages
     */

    int getPages();

    /**
     * This method returns whether the current page is
     * currently equal to the first page of the pagination.
     *
     * @return whether it is the first page
     */

    boolean isFirst();

    /**
     * This method returns whether the current page is the last page.
     * <p>
     * When it's the last page, calling {@link #next()} will have no
     * effect on this pagination.
     *
     * @return whether it is the last page
     */

    boolean isLast();

    /**
     * This method toggles the current page, by setting the handler to the
     * first page in the pagination.
     * <p>
     * If the pagination is already on the first page, calling
     * this will have no effect on the gui.
     *
     * @return this pagination
     */

    GuiPagination first();

    /**
     * This method toggles the current page, by setting the handler to the
     * last page in the pagination.
     * <p>
     * If the pagination is already on the last page, calling
     * this will have no effect on the gui.
     *
     * @return this pagination
     */

    GuiPagination last();

    /**
     * This method toggles the current page, by switching to the previous page
     * of the gui, if possible.
     * <p>
     * This is often needed when having to toggle between pages, by using
     * previous and next buttons, where each click will either go to the previous
     * or the next page, assuming conditions are met.
     *
     * @return this pagination
     */

    GuiPagination previous();

    /**
     * This method toggles the current page, by switching to the next page
     * of the gui, if possible.
     * <p>
     * This if often used for having to toggle between pages, by using
     * the previous and next buttons, where each click will either go the previous
     * or the next page, assuming specific conditions are met.
     *
     * @return this pagination
     */

    GuiPagination next();

    /**
     * This method returns all items in the current page.
     * <p>
     * It is possible that this method returns null, if the {@link #setItems(int, GuiItem...)}
     * has not been used, or the item parameters are null, or the itemsPerPage parameter
     * is set to 0.
     *
     * @return the items per in this page
     */

    GuiItem[] getItemsInPage();

}
