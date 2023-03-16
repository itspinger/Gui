package io.pnger.gui.slot;

import io.pnger.gui.pair.Pair;

/**
 * This type represents an inventory slot in the inventory. It holds the most basic
 * information for the targeted slot: the row, and the column the slot is at.
 *
 * @since 2.0.0
 * @author Pinger
 */

public class GuiSlot implements Pair<Integer, Integer> {

    private final int row;
    private final int column;

    private GuiSlot(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static GuiSlot of(int row, int column) {
        return new GuiSlot(row, column);
    }

    public static GuiSlot of(int index) {
        return GuiSlot.of(index / 9, index % 9);
    }

    @Override
    public Integer getKey() {
        return this.row;
    }

    @Override
    public Integer getValue() {
        return this.column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        // If the classes aren't equal return false
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        GuiSlot that = (GuiSlot) o;
        return this.row == that.row && this.column == that.column;
    }

}
