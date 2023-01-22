package io.pnger.tally.contents.entity;

import io.pnger.tally.slot.InventorySlotIterator;
import io.pnger.tally.item.IntelligentItem;
import io.pnger.tally.contents.InventoryPagination;

import java.util.Arrays;

public class IntelligentInventoryPagination implements InventoryPagination {

    private int currentPage;
    private int itemsPerPage = 5;
    private IntelligentItem[] items = new IntelligentItem[0];

    @Override
    public IntelligentItem[] getItemsInPage() {
        return Arrays.copyOfRange(this.items, this.currentPage * this.itemsPerPage, (this.currentPage + 1) * this.itemsPerPage);
    }

    @Override
    public InventoryPagination setPage(int page) {
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
    public InventoryPagination firstPage() {
        this.currentPage = 0;
        return this;
    }

    @Override
    public InventoryPagination lastPage() {
        this.currentPage = this.items.length / this.itemsPerPage;
        return this;
    }

    @Override
    public InventoryPagination previousPage() {
        if (!isFirst())
            this.currentPage--;

        return this;
    }

    @Override
    public InventoryPagination nextPage() {
        if (!isLast())
            this.currentPage++;

        return this;
    }

    @Override
    public InventoryPagination addToIterator(InventorySlotIterator iterator) {
        for (IntelligentItem item : getItemsInPage()) {
            iterator.next().setItem(item);

            if (iterator.isLast())
                break;
        }

        return this;
    }

    @Override
    public InventoryPagination setItems(IntelligentItem... items) {
        this.items = items;
        return this;
    }

    @Override
    public InventoryPagination setItemsPerPage(int itemsPerPage) {
        if (itemsPerPage == 0)
            return this;

        this.itemsPerPage = itemsPerPage;
        return this;
    }
}
