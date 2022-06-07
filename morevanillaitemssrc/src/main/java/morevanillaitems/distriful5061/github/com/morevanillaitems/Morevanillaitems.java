package morevanillaitems.distriful5061.github.com.morevanillaitems;

import de.tr7zw.nbtinjector.NBTInjector;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.*;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.paginate.PaginatedMenuBuilder;
import org.ipvp.canvas.slot.ClickOptions;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.Arrays;
import java.util.List;

public final class Morevanillaitems extends JavaPlugin {
    private static Morevanillaitems plugin;
    /*
    * Recipes
    */
    public static Material[] BowRecipe;


    public static Morevanillaitems getPlugin(){
        return plugin;
    }

    public void loggerinfo(String content){
        getLogger().info(content);
    }

    public static void CraftingRecipes(Material[] items, Slot resultslot){
        if (Arrays.equals(BowRecipe, items)) {
            resultslot.setItem(new ItemStack(Material.BOW,1));
            return;
        }
        resultslot.setItem(new ItemStack(Material.AIR,1));
    }

    public static void recipecheck(Player p,ClickInformation info){
        Material[] nowcraftmaterials = new Material[9];
        int offset = 11;
        int offset2 = 0;
        for(int i=0;i < 3;i++) {
            try{
                Material thisslotitem = info.getClickedMenu().getSlot(i+offset).getItem(p).getType();
                nowcraftmaterials[i+offset2] = thisslotitem;
            } catch(NullPointerException | IllegalArgumentException ignored){
                nowcraftmaterials[i+offset2] = Material.AIR;
            }
        }
        offset = 20;
        offset2 += 3;
        for(int i=0;i < 3;i++) {
            try{
                Material thisslotitem = info.getClickedMenu().getSlot(i+offset).getItem(p).getType();
                nowcraftmaterials[i+offset2] = thisslotitem;
            } catch(NullPointerException | IllegalArgumentException ignored){
                nowcraftmaterials[i+offset2] = Material.AIR;
            }
        }
        offset = 29;
        offset2 += 3;
        for(int i=0;i < 3;i++) {
            try{
                Material thisslotitem = info.getClickedMenu().getSlot(i+offset).getItem(p).getType();
                nowcraftmaterials[i+offset2] = thisslotitem;
            } catch(NullPointerException | IllegalArgumentException ignored){
                nowcraftmaterials[i+offset2] = Material.AIR;
            }
        }
        CraftingRecipes(nowcraftmaterials,info.getClickedMenu().getSlot(24));
    }

    public static void CraftdisplayMenu(Player player){
        Menu menu = ChestMenu.builder(5)
                .title("§0Craft")
                .redraw(true)
                .build();
        Mask mask = BinaryMask.builder(menu)
                .item(new ItemStack(Material.WHITE_STAINED_GLASS_PANE))
                .pattern("111111111")
                .pattern("110001111")
                .pattern("110001011")
                .pattern("110001111")
                .pattern("111111111")
                .build();
        /* For Debug
        Mask mask2 = BinaryMask.builder(menu)
                .item(new ItemStack(Material.WHITE_STAINED_GLASS_PANE))
                .pattern("111111111")
                .pattern("111111111")
                .pattern("111111111")
                .pattern("111111111")
                .pattern("111111111")
                .build();
        */
        mask.apply(menu);
        ClickOptions options = ClickOptions.builder()
                .allow(ClickType.LEFT, ClickType.RIGHT)
                .allow(InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME)
                .allow(InventoryAction.PICKUP_ALL, InventoryAction.PICKUP_ONE, InventoryAction.PICKUP_SOME, InventoryAction.PICKUP_HALF)
                .build();
        menu.getSlot(11).setClickOptions(options);
        menu.getSlot(12).setClickOptions(options);
        menu.getSlot(13).setClickOptions(options);
        menu.getSlot(20).setClickOptions(options);
        menu.getSlot(21).setClickOptions(options);
        menu.getSlot(22).setClickOptions(options);
        menu.getSlot(29).setClickOptions(options);
        menu.getSlot(30).setClickOptions(options);
        menu.getSlot(31).setClickOptions(options);
        menu.getSlot(24).setClickHandler((p, info) -> {
            try{
                ItemStack resultitem = info.getClickedSlot().getItem(p);
                p.getInventory().addItem(resultitem);
                ItemStack air = new ItemStack(Material.AIR,1);
                info.getClickedSlot().setItem(air);
                Menu clickedmenu = info.getClickedMenu();
                clickedmenu.getSlot(11).setItem(air);
                clickedmenu.getSlot(12).setItem(air);
                clickedmenu.getSlot(13).setItem(air);
                clickedmenu.getSlot(20).setItem(air);
                clickedmenu.getSlot(21).setItem(air);
                clickedmenu.getSlot(22).setItem(air);
                clickedmenu.getSlot(29).setItem(air);
                clickedmenu.getSlot(30).setItem(air);
                clickedmenu.getSlot(31).setItem(air);
            } catch(IllegalArgumentException | NullPointerException ignored){}
        });
        for(int k = 0;k < 3;k++) {
            menu.getSlot(k+11).setClickHandler((p, info) -> {
                Bukkit.broadcastMessage(p.getName() + ":" + p.getItemOnCursor().getType().name());
            });
        }
        for(int k = 0;k < 3;k++) {
            menu.getSlot(k+20).setClickHandler((p, info) -> {
                Bukkit.broadcastMessage(p.getName() + ":" + p.getItemOnCursor().getType().name());
            });
        }
        for(int k = 0;k < 3;k++) {
            menu.getSlot(k+29).setClickHandler((p, info) -> {
                Bukkit.broadcastMessage(p.getName() + ":" + p.getItemOnCursor().getType().name());
            });
        }
        menu.setCloseHandler((p, menu1) -> {
            int offset = 11;
            Player pl = Bukkit.getPlayer(p.getName());
            for(int i=0;i<3;i++){
                try{
                    ItemStack item = menu1.getSlot(i+offset).getItem(pl);
                    Bukkit.broadcastMessage(String.valueOf(item == null));
                    p.getInventory().addItem(item);
                } catch(IllegalArgumentException | NullPointerException ignored){
                    Bukkit.broadcastMessage("illegalarg");
                }
            }
            offset = 20;
            for(int i=0;i<3;i++){
                try{
                    ItemStack item = menu1.getSlot(i+offset).getItem(pl);
                    Bukkit.broadcastMessage(String.valueOf(item == null));
                    p.getInventory().addItem(item);
                } catch(IllegalArgumentException | NullPointerException ignored){
                    Bukkit.broadcastMessage("illegalarg");
                }
            }
            offset = 29;
            for(int i=0;i<3;i++){
                try{
                    ItemStack item = menu1.getSlot(i+offset).getItem(pl);
                    Bukkit.broadcastMessage(String.valueOf(item == null));
                    p.getInventory().addItem(item);
                } catch(IllegalArgumentException | NullPointerException ignored){
                    Bukkit.broadcastMessage("illegalarg");
                }
            }
        });
        menu.open(player);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        loggerinfo("Minigame plugin booting...");
        Bukkit.getServer().getPluginManager().registerEvents(new lis(), this);
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
        CommandClass.commandlist.add("craft");
        CommandClass.commandlist.add("enderchest");
        CommandClass.commandlist.add("hp");
        CommandClass.commandlist.add("test");
        for(String commandname : CommandClass.commandlist){
            Bukkit.getPluginCommand(commandname).setExecutor(new CommandClass());
        }
        NBTInjector.inject();

        Material AIR = Material.AIR;

        Material[] bowrecipe = new Material[9];
        bowrecipe[0] = AIR; bowrecipe[1] = Material.STICK; bowrecipe[2] = AIR;
        bowrecipe[3] = AIR; bowrecipe[4] = Material.STICK; bowrecipe[5] = AIR;
        bowrecipe[6] = AIR; bowrecipe[7] = Material.STICK; bowrecipe[8] = AIR;
        BowRecipe = bowrecipe;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
