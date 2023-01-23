package io.pnger.tally.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.function.Consumer;

public class GuiItem {

    // The item that is handling the event
    private final ItemStack item;

    // The event happening when the player clicks the item
    private final Consumer<InventoryClickEvent> click;

    // Whether a player may be to drag something to this slot, by default false.
    private final boolean drag;

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

    public static GuiItem newItem(ItemStack item, Consumer<InventoryClickEvent> event, boolean drag) {
        return new GuiItem(item, event, drag);
    }

    /**
     * Creates a new instance of this class with the specified data.
     *
     * @param item the representing item
     * @param event the event being called when the item is clicked
     * @return the item
     */

    public static GuiItem newItem(ItemStack item, Consumer<InventoryClickEvent> event) {
        return new GuiItem(item, event, false);
    }

    /**
     * Creates a new instance of this class without handling the clicks or drags.
     *
     * @param item the item
     * @return the item
     */

    public static GuiItem newItem(ItemStack item) {
        return new GuiItem(item, e -> {}, false);
    }

    /**
     * Creates a new instance of this class without handling the clicks.
     *
     * @param item the item
     * @param drag whether we can do drag items on and off from this item, useful for trading plugins and such.
     * @return the item
     */

    public static GuiItem newItem(ItemStack item, boolean drag) {
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
        return item;
    }

    /**
     * Whether players will be allowed to drag this item from a
     * {@link io.pnger.tally.GuiInventory} inventory.
     *
     * @return whether dragging is allowed for this item
     */

    public boolean isDrag() {
        return drag;
    }
}
