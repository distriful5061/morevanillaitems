package morevanillaitems.distriful5061.github.com.morevanillaitems;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtinjector.NBTInjector;

import morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners.PlayerServerEventListener;
import morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners.BowEventListener;
import morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners.CraftEventListener;
import morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners.OtherEventListener;
import morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners.SwordEventListener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Morevanillaitems extends JavaPlugin {
    private static Morevanillaitems plugin;


    public static Morevanillaitems getPlugin(){
        return plugin;
    }

    public void loggerinfo(String content){
        getLogger().info(content);
    }

    public static ShapedRecipe addRecipe(Material itemmaterial, int amount, String itemname, List<String> itemlore, boolean unbreakable, String namespacekey, String shape1, String shape2, String shape3){
        ItemStack item = new ItemStack(itemmaterial,amount);
        ItemMeta meta = item.getItemMeta();
        if(meta == null){
            Morevanillaitems logger = new Morevanillaitems();
            logger.loggerinfo("ItemMeta is null");
        }
        Objects.requireNonNull(meta).setDisplayName(itemname);
        meta.setLore(itemlore);
        meta.setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        NamespacedKey key = new NamespacedKey(Morevanillaitems.getPlugin(), namespacekey);
        ShapedRecipe recipe = new ShapedRecipe(key,item);
        recipe.shape(shape1,shape2,shape3);
        return recipe;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        NBTInjector.inject();
        loggerinfo("Morevanillaitems plugin booting...");
        Bukkit.getServer().getPluginManager().registerEvents(new OtherEventListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BowEventListener(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new SwordEventListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CraftEventListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerServerEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
        loggerinfo("Listener registered");

        CommandClass.commandlist.add("enderchest");
        CommandClass.commandlist.add("hp");
        CommandClass.commandlist.add("test");
        for(String commandname : CommandClass.commandlist){
            Objects.requireNonNull(Bukkit.getPluginCommand(commandname)).setExecutor(new CommandClass());
        }
        loggerinfo("Command registered");


        ShapedRecipe recipe;
        List<String> itemlore = new ArrayList<>();


        itemlore.add("an Normal emerald sword");
        recipe = addRecipe(
                Material.DIAMOND_SWORD,
                1,
                "§aEmerald Sword",
                itemlore,
                false,
                "EmeraldSword",
                " E ",
                " E ",
                " S ");
        recipe.setIngredient('E',Material.EMERALD);
        recipe.setIngredient('S',Material.STICK);
        Bukkit.addRecipe(recipe);
        itemlore.clear();

        loggerinfo("Recipe registered");

        loggerinfo("All booting process ended.");
    }

    @Override
    public void onDisable() {
        loggerinfo("Morevanillaitems plugin are disabled...");
    }
}
