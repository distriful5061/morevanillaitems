package morevanillaitems.distriful5061.github.com.morevanillaitems;



import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Objects;

public class lis implements Listener{
    HashMap<Player, Boolean> BowLeftClicked = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        BowLeftClicked.put(e.getPlayer(),false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        BowLeftClicked.remove(e.getPlayer());
    }

    @EventHandler
    public void onPlayerInterracted(PlayerInteractEvent e){
        PlayerInventory playerinv = e.getPlayer().getInventory();
        Material playeritemmaterial = playerinv.getItemInMainHand().getType();
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
            if(playeritemmaterial == Material.BOW) {
                if (playerinv.contains(Material.ARROW)) {
                    playerinv.removeItem(new ItemStack(Material.ARROW, 1));
                    e.getPlayer().launchProjectile(Arrow.class);
                }
                return;
            }
        } else if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){

        }
    }
}
