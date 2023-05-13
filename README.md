# Gui
An improved version of SmartInventories that manages Minecraft Inventories quickly and easily.

## What exactly is the Gui plugin?
**Gui** is a library written in Java which is designed to remove the need for bothering \
with the API provided by Spigot. 

With this library, you will be able to create custom inventories within seconds and use them in real-time.

This plugin is inspired by SmartInventories and most of the code was rewritten from this [repository.](https://github.com/MinusKube/SmartInvs)

## Getting Started
To install this repository, you should follow the next steps:

1. Clone this repository: `git clone https://github.com/ITSPINGER/Gui.git`
2. Enter into the directory folder: `cd Gui`
3. Build the project using Maven: `mvn clean install`

After the project has finished building, you may now use the project in your projects.

### Maven
```xml
<dependency>
  <groupId>io.pnger.gui</groupId>
  <artifactId>gui</artifactId>
  <version>2.0.0</version> <!-- At time of writing, 2.0.0 is latest version. See the pom.xml for the latest version -->
  <scope>compile</scope> <!-- Change scope to 'provided' if you are running Gui as a plugin -->
</dependency>
```

## Creating an Inventory
In order to be able to create an inventory using this library, you must do the following:

1. Initialize the `GuiManager` class which takes in a `JavaPlugin` instance
2. Implement the `GuiProvider` class where you will be able to fill the inventory
3. Create an `GuiInventory` instance using the `GuiBuilder` \
and open it using `GuiInventory#open(Player p, int page)`

## Example
Down below, we will be recreating this simple gui.

![GUI EXAMPLE](https://gyazo.com/109f9b14111cdddb2c672f1bab38aef3.gif)

## Creating the Gui Manager
The following class is an example of how you can create an inventory with the specified provider and use it.

It is advised to keep all inventory objects in this kind of class.

```java
import io.pnger.gui.GuiBuilder;
import io.pnger.gui.GuiInventory;
import io.pnger.gui.manager.GuiManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ExampleInventoryManager {

    private final GuiManager manager;

    public ExampleInventoryManager(JavaPlugin plugin) {
        this.manager = new GuiManager(plugin);
    }

    public GuiInventory getCarrotInventory() {
        return GuiBuilder.of()
                .manager(this.manager) // Remember to set the manager
                .provider(new CarrotProvider()) // Required as well as the manager, this is your custom provider. This should be unique for each inventory
                .title(ChatColor.DARK_GRAY + "Carrot Transformer") // Set the title of the inventory
                .closeable(true) // Set whether the inventory should be closable, default is true
                .size(6, 9) // Set the size of the inventory
                .parent(null) // Set the parent of the inventory
                .build();
    }
}
```

## Implementing the GuiProvider
In combination with the class above, you will be able to now use the custom gui.

```java
import io.pnger.gui.contents.GuiContents;
import io.pnger.gui.item.GuiItem;
import io.pnger.gui.provider.GuiProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class CarrotProvider implements GuiProvider {

    @Override
    public void initialize(Player p, GuiContents contents) {
        // The initialize method is called when the inventory is first opened for the player
        // Here we want to fill the whole inventory with CYAN_STAINED_GLASS_PANE

        // A GuiItem is an object which is used to handle inventory clicks
        // Although not all GuiItem's need to handle them, like this one
        GuiItem item = GuiItem.of(new ItemStack(Material.CYAN_STAINED_GLASS_PANE));

        // Fill the whole inventory
        contents.fill(item);

        // Calling GuiItem.of(ItemStack, boolean) where the boolean is true
        // will allow for any item to be dragged into this slot
        GuiItem empty = GuiItem.of(new ItemStack(Material.AIR), true);
        contents.setItem(2, 4, empty);
    }

    @Override
    public void update(Player p, GuiContents contents) {
        // The update method is called once every tick, so 20 times in 1 second
        // The item below is the confirmation button, when clicked on the item in the middle transforms
        // to a carrot
        GuiItem confirm = GuiItem.of(new ItemStack(Material.GREEN_SHULKER_BOX), e -> {
            // Get the item at the certain position
            Optional<ItemStack> stack = contents.getItemStack(2, 4);

            // It can be null, so we have to check for it
            if (!stack.isPresent())
                return;

            contents.setItem(2, 4, GuiItem.of(new ItemStack(Material.CARROT)));
        });

        // Add the confirm button to the bottom left of the inventory
        contents.setItem(4, 0, confirm);
    }
}
```

## Opening the inventory
After you have done all of the above steps, you can now easily open the inventory by accessing the manager class.

```java
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CarrotCommand implements CommandExecutor {

    private final ExampleInventoryManager exampleInventoryManager;

    public CarrotCommand(ExampleInventoryManager exampleInventoryManager) {
        this.exampleInventoryManager = exampleInventoryManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // We can't open it to non players!!
        if (!(commandSender instanceof Player))
            return true;
        
        this.exampleInventoryManager.getCarrotInventory().open((Player) commandSender);
        return true;
    }
}
```

## License
This project is licensed under GNU v3.0 license.

