# IntelligentInventories
An improved version of SmartInventories that manages Minecraft Inventories quickly and easily.

## What exactly is a IntelligentInventory?
IntelligentInventories is a library written in Java which is designed to remove the need for bothering \
with the API provided by Spigot. 

With this library, you will be able to create custom GUIs' within seconds and use them in real-time.

This plugin is inspired by SmartInventories and most of the code was rewritten from this [repository.](https://github.com/MinusKube/SmartInvs)

## Getting Started
To install this repository, you should follow the next steps:

1. Clone this repository: `git clone https://github.com/ITSPINGER/IntelligentInventories.git`
2. Enter into the directory: `cd IntelligentInventories`
3. Build the project using Maven: `mvn clean install`

After the project has finished building, you may now use the project in your projects.

### Maven
```xml
<dependency>
  <groupId>org.intelligent.inventories</groupId>
  <artifactId>IntelligentInventories</artifactId>
  <version>1.0-SNAPSHOT</version> <!-- At time of writing, 1.0-SNAPSHOT is latest version. See the pom.xml for the latest version -->
  <scope>compile</scope> <!-- Change scope to 'provided' if you are running IntelligentInventories as a plugin -->
</dependency>
```

## Creating an Inventory
In order to be able to create an inventory using this library, you must do the following:

1. Initialize the `IntelligentManager` class which takes in a `JavaPlugin` instance
2. Implement the `IntelligentProvider` class where you will be able to fill the inventory
3. Create an `IntelligentInventory` instance using the `IntelligentInventoryBuilder` \
and open it using `IntelligentInventory#open(Player p, int page)`

## Example
Down below, we will be recreating this simple gui.

![GUI EXAMPLE](https://gyazo.com/109f9b14111cdddb2c672f1bab38aef3.gif)


## Creating the Inventory Manager
The following class is an example of how you can create an inventory with the specified provider and use it.

It is advised to keep all inventory objects in this kind of class.

```java
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.intelligent.inventories.IntelligentInventory;
import org.intelligent.inventories.IntelligentInventoryBuilder;
import org.intelligent.inventories.manager.IntelligentManager;

public class ExampleInventoryManager {

    private final IntelligentManager manager;

    public ExampleInventoryManager(JavaPlugin plugin) {
        // Initialize the manager
        this.manager = new IntelligentManager(plugin);
    }
    
    public IntelligentInventory getCarrotInventory() {
        return IntelligentInventoryBuilder.newBuilder()
                .setManager(this.manager) // Remember to set the manager
                .setProvider(new CarrotProvider()) // Required as well as the manager, this is your custom provider. This should be unique for each inventory
                .setTitle(ChatColor.DARK_GRAY + "Carrot Transformer") // Set the title of the inventory
                .setCloseable(true) // Set whether the inventory should be closable, default is true
                .setSize(6, 9) // Set the size of the inventory
                .setParent(null) // Set the parent of the inventory
                .build();
    }
}
```

## Implementing the IntelligentProvider
In combination with the class above, you will be able to now use the custom gui.

```java
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.intelligent.inventories.contents.InventoryContents;
import org.intelligent.inventories.item.IntelligentItem;
import org.intelligent.inventories.provider.IntelligentProvider;

import java.util.Optional;

public class CarrotProvider implements IntelligentProvider {

    @Override
    public void initialize(Player p, InventoryContents contents) {
        // The initialize method is called when the inventory is first opened for the player
        // Here we want to fill the whole inventory with CYAN_STAINED_GLASS_PANE

        // An IntelligentItem is an object which is used to handle inventory clicks
        // Although not all IntelligentItems need to handle them, like this one
        IntelligentItem item = IntelligentItem.createNew(new ItemStack(Material.CYAN_STAINED_GLASS_PANE));

        // Fill the whole inventory
        contents.fill(item);

        // Calling IntelligentItem.createNew(ItemStack, boolean) where the boolean is true
        // will allow for any item to be dragged into this slot
        IntelligentItem empty = IntelligentItem.createNew(new ItemStack(Material.AIR), true);
        contents.setItem(2, 4, empty);
    }

    @Override
    public void update(Player p, InventoryContents contents) {
        // The update method is called once every tick, so 20 times in 1 second
        // The item below is the confirmation button, when clicked on the item in the middle transforms
        // to a carrot
        IntelligentItem confirm = IntelligentItem.createNew(new ItemStack(Material.GREEN_SHULKER_BOX), e -> {
            // Get the item at the certain position
            Optional<ItemStack> stack = contents.getItemStack(2, 4);
            
            // It can be nullable, so we have to check for it
            if (!stack.isPresent())
                return;
            
            contents.setItem(2, 4, IntelligentItem.createNew(new ItemStack(Material.CARROT)));
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

