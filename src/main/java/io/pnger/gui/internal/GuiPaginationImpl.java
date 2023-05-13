package io.pnger.gui.internal;

import io.pnger.gui.item.GuiItem;
import io.pnger.gui.pagination.GuiPagination;
import io.pnger.gui.slot.GuiSlotIterator;

import java.util.Arrays;

public class GuiPaginationImpl implements GuiPagination {

    private int currentPage;
    private int itemsPerPage = 36;
    private GuiItem[] items = new GuiItem[0];

    @Override
    public GuiPagination addToIterator(GuiSlotIterator iterator) {
        for (GuiItem item : this.getItemsInPage()) {
            iterator.next().setItem(item);

            // Escape if it is the last slot in the iterator
            if (iterator.isLast()) {
                break;
            }
        }

        return this;
    }

    @Override
    public GuiPagination setItems(int itemsPerPage, GuiItem... items) {
        if (itemsPerPage == 0) {
            throw new IllegalArgumentException("Cannot set items to 0");
        }

        this.itemsPerPage = itemsPerPage;
        this.items = items;
        return this;
    }

    @Override
    public GuiPagination setPage(int page) {
        this.currentPage = page;
        return this;
    }

    @Override
    public int getPage() {
        return this.currentPage;
    }

    @Override
    public int getPages() {
        return (int) Math.ceil((double) this.items.length / this.itemsPerPage);
    }

    @Override
    public boolean isFirst() {
        return this.currentPage == 0;
    }

    @Override
    public boolean isLast() {
        // Pages start from index 0
        // So the page count is always +1 of the current page
        // So for example: If the gui has 7 pages, the page with index 6 will be last
        return this.currentPage == this.getPages() - 1;
    }

    @Override
    public GuiPagination first() {
        return this.setPage(0);
    }

    @Override
    public GuiPagination last() {
        return this.setPage(this.getPages() - 1);
    }

    @Override
    public GuiPagination previous() {
        if (!this.isFirst()) {
            this.currentPage--;
        }

        return this;
    }

    @Override
    public GuiPagination next() {
        if (!this.isLast()) {
            this.currentPage++;
        }

        return this;
    }

    @Override
    public GuiItem[] getItemsInPage() {
        return Arrays.copyOfRange(
            this.items,
            this.currentPage * this.itemsPerPage,
            (this.currentPage + 1) * this.itemsPerPage
        );
    }
}
