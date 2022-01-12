package org.intelligent.inventories.contents.entity;

import com.google.common.collect.Lists;
import org.intelligent.inventories.IntelligentInventory;
import org.intelligent.inventories.contents.InventorySlotIterator;
import org.intelligent.inventories.contents.IteratorType;
import org.intelligent.inventories.item.IntelligentItem;

import java.util.AbstractMap;
import java.util.List;
import java.util.Optional;

public class IntelligentSlotIterator implements InventorySlotIterator {

    private final IntelligentInventory inventory;
    private final IteratorType type;

    private int row, column;
    private boolean override = true, started;

    private final List<AbstractMap.SimpleEntry<Integer, Integer>> blacklisted = Lists.newArrayList();

    public IntelligentSlotIterator(IntelligentInventory inventory, IteratorType type, int row, int column) {
        this.inventory = inventory;
        this.type = type;
        this.row = row;
        this.column = column;
    }

    public IntelligentSlotIterator(IntelligentInventory inventory, IteratorType type) {
        this(inventory, type, 0, 0);
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public int getColumn() {
        return this.column;
    }

    @Override
    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public Optional<IntelligentItem> getItem() {
        return this.inventory.getContents().getItem(row, column);
    }

    @Override
    public InventorySlotIterator setItem(IntelligentItem item) {
        if (!canPlace())
            return this;

        this.inventory.getContents().setItem(this.row, this.column, item);
        return this;
    }

    @Override
    public InventorySlotIterator next() {
        if (isLast()) {
            this.started = true;
            return this;
        }

        do {
            if (!this.started) {
                this.started = true;
            }

            else {
                switch (type) {
                    case HORIZONTAL -> {
                        column = ++column % inventory.getColumns();
                        if (column == 0)
                            row++;
                    }
                    case VERTICAL -> {
                        row = ++row % inventory.getRows();
                        if (row == 0)
                            column++;
                    }
                }
            }
        }

        while(!canPlace() && !isLast());
        return this;
    }

    @Override
    public InventorySlotIterator previous() {
        if (row == 0 && column == 0) {
            this.started = true;
            return this;
        }

        do {
            if (!this.started) {
                this.started = true;
            }

            else {
                switch (type) {
                    case HORIZONTAL -> {
                        column--;
                        if (column == 0) {
                            column = inventory.getColumns() - 1;
                            row--;
                        }
                    }
                    case VERTICAL -> {
                        row--;
                        if (row == 0) {
                            row = inventory.getRows() - 1;
                            column--;
                        }
                    }
                }
            }
        }

        while(!canPlace() && (row != 0 || column != 0));
        return this;
    }

    @Override
    public void blacklist(int row, int column) {
        this.blacklisted.add(new AbstractMap.SimpleEntry<>(row, column));
    }

    @Override
    public void blacklistRow(int row) {
        for (int i = 0; i < this.inventory.getColumns(); i++) {
            this.blacklisted.add(new AbstractMap.SimpleEntry<>(row, i));
        }
    }

    @Override
    public void blacklistColumn(int column) {
        for (int i = 0; i < this.inventory.getRows(); i++) {
            this.blacklisted.add(new AbstractMap.SimpleEntry<>(i, column));
        }
    }

    @Override
    public boolean isOverride() {
        return this.override;
    }

    @Override
    public void setOverride(boolean override) {
        this.override = override;
    }

    @Override
    public boolean isLast() {
        return row == this.inventory.getRows() - 1
                && column == this.inventory.getColumns() - 1;
    }

    private boolean isBlacklisted(int row, int column) {
        for (AbstractMap.SimpleEntry<Integer, Integer> entry : this.blacklisted) {
            if (entry.getKey() == row && entry.getValue() == column)
                return true;
        }

        return false;
    }

    private boolean canPlace() {
        return !isBlacklisted(row, column) && this.override && this.getItem().isPresent();
    }
}
