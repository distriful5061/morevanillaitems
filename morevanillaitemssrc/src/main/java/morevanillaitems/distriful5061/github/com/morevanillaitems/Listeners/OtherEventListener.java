package morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

public class OtherEventListener implements Listener {
    public void checkItemNBT(ItemStack item){
        NBTItem itemA = new NBTItem(item);
        switch(Objects.requireNonNull(item.getItemMeta()).getDisplayName()) {
        }
    }

    @EventHandler
    public void onPlayerInterracted(PlayerInteractEvent e){//Test
        PlayerInventory playerinv = e.getPlayer().getInventory();
        ItemStack iteminmainhand = playerinv.getItemInMainHand();
        Material playeritemmaterial = iteminmainhand.getType();

        checkItemNBT(iteminmainhand);

        if(playeritemmaterial == Material.AIR) return;

        NBTItem playeritemnbt = new NBTItem(iteminmainhand);

        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){

        } else if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){

        }
    }
}
