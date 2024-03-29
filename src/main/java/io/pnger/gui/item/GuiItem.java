package io.pnger.gui.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import io.pnger.gui.GuiInventory;

import java.util.Objects;
import java.util.function.Consumer;

public class GuiItem {

    private final ItemStack item;
    private Consumer<InventoryClickEvent> click;
    private boolean drag;

    /**
     * Creates a new instance of this class with the specified data.
     *
     * @param item the representing item
     * @param event the event being called when the item is clicked
     * @param drag whether we can do drag items on and off from this item, useful for trading plugins and such.
     */

    private GuiItem(ItemStack item, Consumer<InventoryClickEvent> event, boolean drag) {
        Objects.requireNonNull(item, "The ItemStack being set may not be null");

        this.item = item;
        this.click = event;
        this.drag = drag;
    }

    /**
     * Creates a new instance of this class with the specified data.
     *
     * @param item the representing item
     * @param event the event being called when the item is clicked
     * @param drag whether we can do drag items on and off from this item, useful for trading plugins and such.
     * @return the instance
     */

    public static GuiItem of(ItemStack item, Consumer<InventoryClickEvent> event, boolean drag) {
        return new GuiItem(item, event, drag);
    }

    /**
     * Creates a new instance of this class with the specified data.
     *
     * @param item the representing item
     * @param event the event being called when the item is clicked
     * @return the item
     */

    public static GuiItem of(ItemStack item, Consumer<InventoryClickEvent> event) {
        return new GuiItem(item, event, false);
    }

    /**
     * Creates a new instance of this class without handling the clicks or drags.
     *
     * @param item the item
     * @return the item
     */

    public static GuiItem of(ItemStack item) {
        return new GuiItem(item, e -> {}, false);
    }

    /**
     * Creates a new instance of this class without handling the clicks.
     *
     * @param item the item
     * @param drag whether we can do drag items on and off from this item, useful for trading plugins and such.
     * @return the item
     */

    public static GuiItem of(ItemStack item, boolean drag) {
        return new GuiItem(item, e -> {}, drag);
    }

    /**
     * This method applies the click method to the click handler.
     *
     * @param event the event
     */

    public void applyClick(InventoryClickEvent event) {
        this.click.accept(event);
    }

    /**
     * This method returns the item that represents this instance.
     *
     * @return the item
     */

    public ItemStack getItem() {
        return this.item;
    }

    /**
     * This method sets the {@link Consumer<InventoryClickEvent> click handler} that is called
     * when this item gets clicked.
     *
     * @param click the click handler
     */

    public void setClickHandler(Consumer<InventoryClickEvent> click) {
        this.click = click;
    }

    /**
     * This method sets whether players will be allowed to drag this item from a
     * {@link GuiInventory} inventory.
     *
     * @param drag whether the drag will be enabled
     */

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    /**
     * This method returns whether players will be allowed to drag this item
     * from a {@link GuiInventory} inventory.
     *
     * @return whether dragging is allowed for this item
     */

    public boolean isDrag() {
        return this.drag;
    }
}
