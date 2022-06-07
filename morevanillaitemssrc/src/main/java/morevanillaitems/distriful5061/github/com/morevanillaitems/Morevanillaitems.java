package morevanillaitems.distriful5061.github.com.morevanillaitems;

import de.tr7zw.nbtinjector.NBTInjector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.*;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

public final class Morevanillaitems extends JavaPlugin {
    private static Morevanillaitems plugin;


    public static Morevanillaitems getPlugin(){
        return plugin;
    }

    public void loggerinfo(String content){
        getLogger().info(content);
    }

    private static Menu createMenu(String title, Integer rows) {
        return ChestMenu.builder(rows)
                .title(title)
                .build();
    }

    public static void CraftdisplayMenu(Player player){
        Menu menu = createMenu("Craft",5);
        menu.setCloseHandler((p, menu1) -> {
            ItemStack a = menu1.getSlot(1).getItem(p);
            player.getInventory().addItem(a);
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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
