package io.pnger.tally.contents.entity;

import com.google.common.collect.Maps;
import io.pnger.tally.GuiInventory;
import io.pnger.tally.contents.InventoryContents;
import io.pnger.tally.contents.InventoryPagination;
import io.pnger.tally.slot.InventorySlotIterator;
import io.pnger.tally.contents.IteratorType;
import io.pnger.tally.item.IntelligentItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class IntelligentInventoryContents implements InventoryContents {

    private final UUID id;
    private final GuiInventory inventory;

    private final Map<Object, Object> properties = Maps.newConcurrentMap();
    private final Map<String, InventorySlotIterator> iterators = Maps.newConcurrentMap();

    private final IntelligentItem[][] items;
    private final InventoryPagination pagination = new IntelligentInventoryPagination();

    public IntelligentInventoryContents(GuiInventory inventory, UUID id) {
        this.id = id;
        this.inventory = inventory;
        this.items = new IntelligentItem[inventory.getRows()][inventory.getColumns()];
    }

    @Override
    public GuiInventory getInventory() {
        return this.inventory;
    }

    @Override
    public InventoryPagination getPagination() {
        return this.pagination;
    }

    @Override
    public IntelligentItem[][] getItems() {
        return this.items;
    }

    @Override
    public InventorySlotIterator newIterator(String id, IteratorType type, int startRow, int startColumn) {
        return this.iterators.put(id, new IntelligentSlotIterator(this.inventory, type, startRow, startColumn));
    }

    @Override
    public InventorySlotIterator newIterator(IteratorType type, int startRow, int startColumn) {
        return new IntelligentSlotIterator(this.inventory, type, startRow, startColumn);
    }

    @Override
    public Optional<IntelligentItem> getItem(int row, int column) {
        if (row >= this.items.length || column >= this.items[row].length)
            return Optional.empty();

        return Optional.ofNullable(this.items[row][column]);
    }

    @Override
    public Optional<ItemStack> getItemStack(int row, int column) {
        return Optional.ofNullable(this.inventory.getInventory().getItem(row * this.inventory.getColumns() + column));
    }

    @Override
    public Optional<ItemStack> getItemStack(int slot) {
        return Optional.ofNullable(this.inventory.getInventory().getItem(slot));
    }

    @Override
    public void setItem(int row, int column, IntelligentItem item) {
        if (row >= this.items.length || column >= this.items[row].length) {
            return;
        }

        this.items[row][column] = item;
        this.update(row, column, item != null ? item.getItem() : null);
    }

    @Override
    public void addItem(IntelligentItem item) {
        for (int row = 0; row < this.items.length; row++) {
            for (int column = 0; column < this.items[row].length; column++) {
                if (this.items[row][column] == null) {
                    this.setItem(row, column, item);
                }
            }
        }
    }

    @Override
    public void fill(IntelligentItem item) {
        for (int row = 0; row < this.items.length; row++) {
            for (int column = 0; column < this.items[row].length; column++) {
                this.setItem(row, column, item);
            }
        }
    }

    @Override
    public void fillRow(int row, IntelligentItem item) {
        if (row >= items.length)
            return;

        for (int column = 0; column < this.items[row].length; column++)
            this.setItem(row, column, item);
    }

    @Override
    public void fillColumn(int column, IntelligentItem item) {
        for (int row = 0; row < this.items.length; row++) {
            this.setItem(row, column, item);
        }
    }

    @Override @SuppressWarnings("unchecked")
    public <V> V getProperty(String key, V def) {
        return (V) this.properties.getOrDefault(key, def);
    }

    @Override
    public <V> void setProperty(String key, V value) {
        this.properties.put(key, value);
    }

    @Override
    public Map<Object, Object> getProperties() {
        return this.properties;
    }

    private void update(int row, int column, ItemStack item) {
        Player player = Bukkit.getPlayer(this.id);

        if (!this.inventory.getManager().getOpened(this.inventory).contains(player))
            return;

        Inventory topInventory = player.getOpenInventory().getTopInventory();
        topInventory.setItem(this.inventory.getColumns() * row + column, item);
    }
}
