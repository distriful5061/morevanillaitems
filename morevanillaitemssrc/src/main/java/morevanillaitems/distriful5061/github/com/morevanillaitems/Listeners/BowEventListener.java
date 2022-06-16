package morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners;

import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import morevanillaitems.distriful5061.github.com.morevanillaitems.Morevanillaitems;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class BowEventListener implements Listener{
    /*
    Documents(NBT)
    bow:
        noreducearrowsprobability -矢が減らない確率(整数)
        arrowfirerate -矢が打てない時間(Tick)。これがあると左クリックで矢が撃てるようになります
        power -パワー(1Lv=0.5)
        flame -ただ燃えるだけ(1Lv=40Tick)
        damage -基礎ダメージ
        lifesteal -回復(1Lv=ダメージの1%)

        /give @s bow{noreducearrowsprobability:1,arrowfirerate:1,power:1,flame:1,damage:2,lifesteal:50}
     */
    HashMap<Player, Boolean> BowLeftClicked = new HashMap<>();
    HashMap<UUID, Player> ArrowShooter = new HashMap<>();
    HashMap<UUID, Integer> LifeStealLevel = new HashMap<>();

    public void addBowLeftClicked(Player p){
        BowLeftClicked.put(p,false);
    }

    public void removeBowLeftClicked(Player p){
        BowLeftClicked.remove(p);
    }

    public void checkItemNBT(ItemStack item){
        NBTItem itemA = new NBTItem(item);
        switch(Objects.requireNonNull(item.getItemMeta()).getDisplayName()) {
        }
    }

    @EventHandler
    public void onPlayerUsedBow(EntityShootBowEvent e){
        if(e.getProjectile().getType() == EntityType.ARROW){
            checkItemNBT(e.getBow());

            //Bukkit.broadcastMessage(e.getEntity().getName());
            NBTItem playerbow = new NBTItem(Objects.requireNonNull(e.getBow()));
            if(playerbow.hasKey("noreducearrowsprobability")){
                if(Math.ceil(Math.random() * 100) <= playerbow.getInteger("noreducearrowsprobability")){
                    e.setConsumeItem(false);
                }
            }
            if(playerbow.hasKey("flame")){
                e.getProjectile().setFireTicks(40 * playerbow.getInteger("flame"));
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

            arrow.setDouble("damage",nbt_damage + nbt_power);

            if(e.getEntity().getType() == EntityType.PLAYER) {
                int nbt_lifesteal = 0;//nbtarrow系の処理
                if(playerbow.hasKey("lifesteal")){
                    nbt_lifesteal = playerbow.getInteger("lifesteal");
                }
                arrow.setInteger("lifesteallevel",nbt_lifesteal);
                arrow.setString("shootbyplayername",e.getEntity().getName());
            }
        }
    }

    @EventHandler
    public void onHitArrow(EntityDamageByEntityEvent e){
        if(e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) && e.getDamager().getType() == EntityType.ARROW) {
            UUID arrowuuid = e.getDamager().getUniqueId();
            ///* For Debug
            Bukkit.broadcastMessage("--------");
            Bukkit.broadcastMessage(e.getEntity().getName() + ":" + e.getDamage() + ":" + e.getEntity().getFireTicks() + ":" + e.getDamager().getFireTicks());
            e.getEntity().setFireTicks(e.getDamager().getFireTicks());
            Bukkit.broadcastMessage("Ok");
            Bukkit.broadcastMessage(String.valueOf(ArrowShooter.containsKey(arrowuuid)));
            //*/
            if(!(ArrowShooter.containsKey(arrowuuid))) return;

            Player p = ArrowShooter.get(arrowuuid);
            double arrdmg = e.getDamage();
            double lifesteallvl = LifeStealLevel.get(arrowuuid);
            double damagecnt = arrdmg * (lifesteallvl / 100);

            ///* For Debug
            Bukkit.broadcastMessage("damagecnt:"+damagecnt+":"+lifesteallvl+":"+arrdmg+":"+lifesteallvl / 100);
            Bukkit.broadcastMessage("--------");
            //*/
            AttributeInstance urself = Objects.requireNonNull(p).getAttribute(Attribute.valueOf("GENERIC_MAX_HEALTH"));
            double playermaxlife = Objects.requireNonNull(urself).getValue();
            p.setHealth(Math.min(p.getHealth() + damagecnt, playermaxlife));
            ArrowShooter.remove(arrowuuid);
            LifeStealLevel.remove(arrowuuid);
        }
    }

    @EventHandler
    public void onArrowHitBlocks(ProjectileHitEvent e){
        if(e.getEntity().getType() == EntityType.ARROW && e.getHitBlock() != null && e.getHitEntity() == null){
            e.getEntity().remove();
        }
    }

    @EventHandler
    public void onPlayerInterracted(PlayerInteractEvent e){//処理が終わった後のreturn;は保険なので気にしないでください。
        PlayerInventory playerinv = e.getPlayer().getInventory();
        ItemStack iteminmainhand = playerinv.getItemInMainHand();
        Material playeritemmaterial = iteminmainhand.getType();

        checkItemNBT(iteminmainhand);

        if(playeritemmaterial != Material.BOW) return;
        NBTItem playeritemnbt = new NBTItem(iteminmainhand);
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
            if(playeritemnbt.hasKey("arrowfirerate")) {
                e.setCancelled(true);
                if(BowLeftClicked.get(e.getPlayer())) return;
                if( !(playerinv.contains(Material.ARROW)) && e.getPlayer().getGameMode() != GameMode.CREATIVE) return;
                if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    if(playeritemnbt.hasKey("noreducearrowsprobability")) {
                        if(Math.ceil(Math.random() * 100) >= playeritemnbt.getInteger("noreducearrowsprobability")){
                            playerinv.removeItem(new ItemStack(Material.ARROW, 1));
                        }
                    } else {
                        playerinv.removeItem(new ItemStack(Material.ARROW, 1));
                    }
                }
                Arrow arrow = e.getPlayer().launchProjectile(Arrow.class);
                //Bukkit.broadcastMessage(String.valueOf(arrow.getDamage()));
                //int enchant_power = iteminmainhand.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);

                double nbt_power = 0;
                if(playeritemnbt.hasKey("power")){//パワーレベル 1Lv=0.5ダメージ
                    nbt_power = playeritemnbt.getDouble("power");
                }
                double nbt_damage = 2;
                if(playeritemnbt.hasKey("damage")) {//基礎ダメージ 小数点まで可
                    nbt_damage = playeritemnbt.getDouble("damage");
                }

                arrow.setDamage(nbt_damage + nbt_power);//パワーレベル 1Lv=0.5ダメージ
                if(playeritemnbt.hasKey("flame")){
                    arrow.setFireTicks(40 * playeritemnbt.getInteger("flame"));//2秒(40Tick) * flameレベル
                }

                int nbt_lifesteal = 0;//nbtarrow系の処理
                if(playeritemnbt.hasKey("lifesteal")){
                    nbt_lifesteal = playeritemnbt.getInteger("lifesteal");
                }
                ArrowShooter.put(arrow.getUniqueId(),e.getPlayer());
                LifeStealLevel.put(arrow.getUniqueId(),nbt_lifesteal);

                //Bukkit.broadcastMessage(String.valueOf(2.0 + (a * 1.5)));

                // クールダウン処理。矢に関して処理をする場合はここより上記に書いてください
                BowLeftClicked.replace(e.getPlayer(),true);
                Bukkit.getScheduler().runTaskLater(Morevanillaitems.getPlugin(), () -> {
                    BowLeftClicked.replace(e.getPlayer(),false);
                }, playeritemnbt.getInteger("arrowfirerate"));
                return;
            }
        } else if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(playeritemnbt.hasKey("arrowfirerate")) {
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
                Arrow arrow = e.getPlayer().launchProjectile(Arrow.class);
                //Bukkit.broadcastMessage(String.valueOf(arrow.getDamage()));
                //int enchant_power = iteminmainhand.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);

                double nbt_power = 0;
                if(playeritemnbt.hasKey("power")){//パワーレベル 1Lv=0.5ダメージ
                    nbt_power = playeritemnbt.getDouble("power");
                }
                double nbt_damage = 2;
                if(playeritemnbt.hasKey("damage")) {//基礎ダメージ 小数点まで可
                    nbt_damage = playeritemnbt.getDouble("damage");
                }

                arrow.setDamage(nbt_damage + nbt_power);//パワーレベル 1Lv=0.5ダメージ
                if(playeritemnbt.hasKey("flame")){
                    arrow.setFireTicks(40 * playeritemnbt.getInteger("flame"));//2秒(40Tick) * flameレベル
                }

                int nbt_lifesteal = 0;//nbtarrow系の処理
                if(playeritemnbt.hasKey("lifesteal")){
                    nbt_lifesteal = playeritemnbt.getInteger("lifesteal");
                }
                ArrowShooter.put(arrow.getUniqueId(),e.getPlayer());
                LifeStealLevel.put(arrow.getUniqueId(),nbt_lifesteal);

                //Bukkit.broadcastMessage(String.valueOf(2.0 + (a * 1.5)));

                // クールダウン処理。矢に関して処理をする場合はここより上記に書いてください
                BowLeftClicked.replace(e.getPlayer(),true);
                Bukkit.getScheduler().runTaskLater(Morevanillaitems.getPlugin(), () -> {
                    BowLeftClicked.replace(e.getPlayer(),false);
                }, playeritemnbt.getInteger("arrowfirerate"));
                return;
            }
        }
    }
}
