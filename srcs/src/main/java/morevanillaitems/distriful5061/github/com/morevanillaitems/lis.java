package morevanillaitems.distriful5061.github.com.morevanillaitems;



import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
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
    public void onHitArrow(EntityDamageByEntityEvent e){
        ///*
        if(e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
            Bukkit.broadcastMessage(e.getEntity().getName() + ":" + e.getDamage());
        }
        //*/
    }

    @EventHandler
    public void onArrowHitBlocks(ProjectileHitEvent e){
        if(e.getEntity().getType() == EntityType.ARROW && e.getHitBlock() != null && e.getHitEntity() == null){
            e.getEntity().remove();
        }
    }

    @EventHandler
    public void onPlayerInterracted(PlayerInteractEvent e){
        PlayerInventory playerinv = e.getPlayer().getInventory();
        ItemStack iteminmainhand = playerinv.getItemInMainHand();
        ItemStack iteminoffhand = playerinv.getItemInOffHand();
        Material playeritemmaterial = iteminmainhand.getType();
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
            if(playeritemmaterial == Material.BOW) {
                if(BowLeftClicked.get(e.getPlayer())) return;
                if (playerinv.contains(Material.ARROW) && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    playerinv.removeItem(new ItemStack(Material.ARROW, 1));
                }
                NBTItem playerbow = new NBTItem(iteminmainhand);
                int nbt_power = 0;
                if(playerbow.hasKey("power")){
                    nbt_power = playerbow.getInteger("power");
                }
                Arrow arrow = e.getPlayer().launchProjectile(Arrow.class);
                //Bukkit.broadcastMessage(String.valueOf(arrow.getDamage()));
                //int enchant_power = iteminmainhand.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
                arrow.setDamage(2.0 + (nbt_power * 0.5));
                if(playerbow.hasKey("flame")){
                    arrow.setFireTicks(20 * (4 + (playerbow.getInteger("flame") * 5)));
                    arrow.setVisualFire(true);
                }
                //Bukkit.broadcastMessage(String.valueOf(2.0 + (a * 1.5)));
                BowLeftClicked.replace(e.getPlayer(),true);
                Bukkit.getScheduler().runTaskLater(Morevanillaitems.getPlugin(), () -> {
                    BowLeftClicked.replace(e.getPlayer(),false);
                }, 2L);
                return;
            }
        } else if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){

        }
    }
}
