package io.pnger.tally.contents.entity;

import io.pnger.tally.slot.InventorySlotIterator;
import io.pnger.tally.item.GuiItem;
import io.pnger.tally.pagination.GuiPagination;

import java.util.Arrays;

public class IntelligentInventoryPagination implements GuiPagination {

    private int currentPage;
    private int itemsPerPage = 5;
    private GuiItem[] items = new GuiItem[0];

    @Override
    public GuiItem[] getItemsInPage() {
        return Arrays.copyOfRange(this.items, this.currentPage * this.itemsPerPage, (this.currentPage + 1) * this.itemsPerPage);
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
    public boolean isFirst() {
        return this.currentPage == 0;
    }

    @Override
    public boolean isLast() {
        int pageCount = (int) Math.ceil((double) this.items.length / this.itemsPerPage);
        return this.currentPage >= pageCount - 1;
    }

    @Override
    public GuiPagination getFirstPage() {
        this.currentPage = 0;
        return this;
    }

    @Override
    public GuiPagination getLastPage() {
        this.currentPage = this.items.length / this.itemsPerPage;
        return this;
    }

    @Override
    public GuiPagination previous() {
        if (!isFirst())
            this.currentPage--;

        return this;
    }

    @Override
    public GuiPagination next() {
        if (!isLast())
            this.currentPage++;

        return this;
    }

    @Override
    public GuiPagination addToIterator(InventorySlotIterator iterator) {
        for (GuiItem item : getItemsInPage()) {
            iterator.next().setItem(item);

            if (iterator.isLast())
                break;
        }

        return this;
    }

    @Override
    public GuiPagination setItems(GuiItem... items) {
        this.items = items;
        return this;
    }

    @Override
    public GuiPagination setItemsPerPage(int itemsPerPage) {
        if (itemsPerPage == 0)
            return this;

        this.itemsPerPage = itemsPerPage;
        return this;
    }
}
