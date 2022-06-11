package morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SwordEventListener implements Listener{
    /*
    Documents(NBT)
    */

    @EventHandler
    public void onPlayerInterracted(PlayerInteractEvent e){
        PlayerInventory playerinv = e.getPlayer().getInventory();
        ItemStack iteminmainhand = playerinv.getItemInMainHand();
        Material playeritemmaterial = iteminmainhand.getType();

        //Morevanillaitems.checkItemNBT(iteminmainhand);

        if(playeritemmaterial == Material.AIR) return;
        NBTItem playeritemnbt = new NBTItem(iteminmainhand);
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){

        } else if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(playeritemmaterial == Material.DIAMOND_SWORD && playeritemnbt.hasKey("emsw")){
                Bukkit.broadcastMessage("emsw is valid");
                return;
            }
        }
    }
}
