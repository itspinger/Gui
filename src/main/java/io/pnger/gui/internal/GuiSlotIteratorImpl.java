package io.pnger.gui.internal;

import io.pnger.gui.GuiInventory;
import io.pnger.gui.slot.GuiIteratorType;
import io.pnger.gui.item.GuiItem;
import io.pnger.gui.slot.GuiSlot;
import io.pnger.gui.slot.GuiSlotIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GuiSlotIteratorImpl implements GuiSlotIterator {

    private final GuiInventory inventory;
    private final GuiIteratorType type;

    private int row, column;
    private boolean override = true, started;

    private final List<GuiSlot> blacklisted;

    public GuiSlotIteratorImpl(GuiInventory inventory, GuiIteratorType type, int row, int column) {
        this.inventory = inventory;
        this.type = type;
        this.row = row;
        this.column = column;
        this.blacklisted = new ArrayList<>();
    }

    public GuiSlotIteratorImpl(GuiInventory inventory, GuiIteratorType type) {
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
    public Optional<GuiItem> getItem() {
        return this.inventory.getContents().getItem(this.row, this.column);
    }

    @Override
    public GuiSlotIterator setItem(GuiItem item) {
        if (!this.canPlace()) {
            return this;
        }

        this.inventory.getContents().setItem(this.row, this.column, item);
        return this;
    }

    @Override
    public GuiSlotIterator next() {
        if (this.isLast()) {
            this.started = true;
            return this;
        }

        do {
            if (!this.started) {
                this.started = true;
            }

            else {
                switch (this.type) {
                    case HORIZONTAL:
                        this.column = ++this.column % this.inventory.getColumns();
                        if (this.column == 0)
                            this.row++;
                        break;
                    case VERTICAL:
                        this.row = ++this.row % this.inventory.getRows();
                        if (this.row == 0)
                            this.column++;
                        break;
                }
            }
        } while (!this.canPlace() && !this.isLast());
        
        return this;
    }

    @Override
    public GuiSlotIterator previous() {
        if (this.row == 0 && this.column == 0) {
            this.started = true;
            return this;
        }

        do {
            if (!this.started) {
                this.started = true;
            } else {
                switch (type) {
                    case HORIZONTAL:
                        this.column--;
                        if (this.column == 0) {
                            this.column = this.inventory.getColumns() - 1;
                            this.row--;
                        }
                        break;
                    case VERTICAL:
                        this.row--;
                        if (this.row == 0) {
                            this.row = this.inventory.getRows() - 1;
                            this.column--;
                        }
                }
            }
        } while (!this.canPlace() && (this.row != 0 || this.column != 0));
        return this;
    }

    @Override
    public void blacklist(int row, int column) {
        this.blacklisted.add(GuiSlot.of(row, column));
    }

    @Override
    public void blacklistRow(int row) {
        for (int i = 0; i < this.inventory.getColumns(); i++) {
            this.blacklisted.add(GuiSlot.of(row, i));
        }
    }

    @Override
    public void blacklistColumn(int column) {
        for (int i = 0; i < this.inventory.getRows(); i++) {
            this.blacklisted.add(GuiSlot.of(i, column));
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
        return this.row == this.inventory.getRows() - 1 && this.column == this.inventory.getColumns() - 1;
    }

    private boolean isBlacklisted(int row, int column) {
        // Construct the slot of the row and column
        GuiSlot gs = GuiSlot.of(row, column);

        // Check if it's blacklisted
        for (GuiSlot slot : this.blacklisted) {
            if (slot.equals(gs)) {
                return true;
            }
        }

        return false;
    }

    private boolean canPlace() {
        return !this.isBlacklisted(this.row, this.column) && (this.override || this.getItem().isPresent());
    }
}
