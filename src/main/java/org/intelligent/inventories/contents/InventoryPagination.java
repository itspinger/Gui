package org.intelligent.inventories.contents;

public interface InventoryPagination {

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

    InventoryPagination lastPage();

    InventoryPagination nextPage();

    InventoryPagination previousPage();

    InventoryPagination firstPage();



}
