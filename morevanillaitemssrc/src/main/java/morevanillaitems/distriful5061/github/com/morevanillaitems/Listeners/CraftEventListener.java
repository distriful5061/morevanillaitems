package morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Objects;

public class CraftEventListener implements Listener{
    @EventHandler
    public void onCraftItem(CraftItemEvent e){
        ItemStack resultitem = e.getInventory().getResult();
        NBTItem item = new NBTItem(Objects.requireNonNull(resultitem));
        switch(Objects.requireNonNull(Objects.requireNonNull(e.getInventory().getResult()).getItemMeta()).getDisplayName()){
            case "§aEmerald Sword":
                if(resultitem.getType() == Material.DIAMOND_SWORD){
                    item.setInteger("emsw",1);
                }
                break;
            default:
                break;
        }
    }
}
