package morevanillaitems.distriful5061.github.com.morevanillaitems;



import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
import org.bukkit.projectiles.ProjectileSource;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class lis implements Listener{
    /*
    Documents(NBT)
    bow:
        noreducearrowsprobability -矢が減らない確率(整数)
        arrowfirerate -矢が打てない時間(Tick)。これがあると左クリックで矢が撃てるようになります
        power -パワー(1Lv=0.5)
        flame -ただ燃えるだけ(1Lv=40Tick)
        damage -基礎ダメージ

        /give @s bow{noreducearrowsprobability:1,arrowfirerate:1,power:1,flame:1,damage:2}
     */
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
    public void onPlayerUsedBow(EntityShootBowEvent e){
        if(e.getProjectile().getType() == EntityType.ARROW){
            //Bukkit.broadcastMessage(e.getEntity().getName());
            NBTItem playerbow = new NBTItem(Objects.requireNonNull(e.getBow()));
            if(playerbow.hasKey("noreducearrowsprobability")){
                if(Math.ceil(Math.random() * 100) < playerbow.getInteger("noreducearrowsprobability")){
                    e.setConsumeItem(false);
                }
            }
            if(playerbow.hasKey("flame")){
                e.getProjectile().setFireTicks(40 * playerbow.getInteger("flame"));
                e.getProjectile().setVisualFire(true);
            }
            NBTEntity arrow = new NBTEntity(e.getProjectile());
            double nbt_damage = 2;
            if(playerbow.hasKey("damage")){
                nbt_damage = playerbow.getInteger("damage");
            }
            int nbt_power = 0;
            if(playerbow.hasKey("power")){
                nbt_power = playerbow.getInteger("power");
            }

            arrow.setDouble("damage",nbt_damage + (nbt_power * 0.5));
        }
    }

    @EventHandler
    public void onHitArrow(EntityDamageByEntityEvent e){
        if(e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) && e.getDamager().getType() == EntityType.ARROW) {
            ///*
            Bukkit.broadcastMessage(e.getEntity().getName() + ":" + e.getDamage() + ":" + e.getEntity().getFireTicks() + ":" + e.getDamager().getFireTicks());
            e.getEntity().setFireTicks(e.getDamager().getFireTicks());
            //*/
        }
    }

    @EventHandler
    public void onArrowHitBlocksOrEntity(ProjectileHitEvent e){
        if(e.getEntity().getType() == EntityType.ARROW && e.getHitBlock() != null && e.getHitEntity() == null){
            e.getEntity().remove();
        }
    }

    @EventHandler
    public void onPlayerInterracted(PlayerInteractEvent e){
        PlayerInventory playerinv = e.getPlayer().getInventory();
        ItemStack iteminmainhand = playerinv.getItemInMainHand();
        Material playeritemmaterial = iteminmainhand.getType();
        if(playeritemmaterial == Material.AIR) return;
        NBTItem playeritemnbt = new NBTItem(iteminmainhand);
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
            if(playeritemmaterial == Material.BOW && playeritemnbt.hasKey("arrowfirerate")) {
                if(BowLeftClicked.get(e.getPlayer())) return;
                if( !(playerinv.contains(Material.ARROW)) && e.getPlayer().getGameMode() != GameMode.CREATIVE) return;
                if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    if(playeritemnbt.hasKey("noreducearrowsprobability")) {
                        if(Math.ceil(Math.random() * 100) > playeritemnbt.getInteger("noreducearrowsprobability")){
                            playerinv.removeItem(new ItemStack(Material.ARROW, 1));
                        }
                    } else {
                        playerinv.removeItem(new ItemStack(Material.ARROW, 1));
                    }
                }
                int nbt_power = 0;
                if(playeritemnbt.hasKey("power")){
                    nbt_power = playeritemnbt.getInteger("power");
                }
                int nbt_damage = 2;
                if(playeritemnbt.hasKey("damage")) {
                    nbt_damage = playeritemnbt.getInteger("damage");
                }
                Arrow arrow = e.getPlayer().launchProjectile(Arrow.class);
                //Bukkit.broadcastMessage(String.valueOf(arrow.getDamage()));
                //int enchant_power = iteminmainhand.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
                arrow.setDamage(nbt_damage + (nbt_power * 0.5));
                if(playeritemnbt.hasKey("flame")){
                    arrow.setFireTicks(40 * playeritemnbt.getInteger("flame"));
                    arrow.setVisualFire(true);
                }
                //Bukkit.broadcastMessage(String.valueOf(2.0 + (a * 1.5)));
                BowLeftClicked.replace(e.getPlayer(),true);
                Bukkit.getScheduler().runTaskLater(Morevanillaitems.getPlugin(), () -> {
                    BowLeftClicked.replace(e.getPlayer(),false);
                }, playeritemnbt.getInteger("arrowfirerate"));
                return;
            }
        } else if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(playeritemmaterial == Material.BOW && playeritemnbt.hasKey("arrowfirerate")) {
                e.setCancelled(true);
                if(BowLeftClicked.get(e.getPlayer())) return;
                if( !(playerinv.contains(Material.ARROW)) && e.getPlayer().getGameMode() != GameMode.CREATIVE) return;
                if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    if(playeritemnbt.hasKey("noreducearrowsprobability")) {
                        if(Math.ceil(Math.random() * 100) > playeritemnbt.getInteger("noreducearrowsprobability")){
                            playerinv.removeItem(new ItemStack(Material.ARROW, 1));
                        }
                    } else {
                        playerinv.removeItem(new ItemStack(Material.ARROW, 1));
                    }
                }
                int nbt_power = 0;
                if(playeritemnbt.hasKey("power")){
                    nbt_power = playeritemnbt.getInteger("power");
                }
                Arrow arrow = e.getPlayer().launchProjectile(Arrow.class);
                //Bukkit.broadcastMessage(String.valueOf(arrow.getDamage()));
                //int enchant_power = iteminmainhand.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
                arrow.setDamage(2.0 + (nbt_power * 0.5));
                if(playeritemnbt.hasKey("flame")){
                    arrow.setFireTicks(40 * playeritemnbt.getInteger("flame"));
                    arrow.setVisualFire(true);
                }
                //Bukkit.broadcastMessage(String.valueOf(2.0 + (a * 1.5)));
                BowLeftClicked.replace(e.getPlayer(),true);
                Bukkit.getScheduler().runTaskLater(Morevanillaitems.getPlugin(), () -> {
                    BowLeftClicked.replace(e.getPlayer(),false);
                }, playeritemnbt.getInteger("arrowfirerate"));
                return;
            }
        }
    }
}
