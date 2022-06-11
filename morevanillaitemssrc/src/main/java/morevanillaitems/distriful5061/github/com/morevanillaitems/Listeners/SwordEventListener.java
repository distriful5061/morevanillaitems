package morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners;

import de.tr7zw.nbtapi.NBTItem;
import morevanillaitems.distriful5061.github.com.morevanillaitems.CommandClass;
import morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners.SwordEvent.Teleport;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

public class SwordEventListener implements Listener{
    /*
    Documents(NBT)
    Diamond_Sword:
        lifesteal 与えたダメージのLv%のhpを回復します
        teleport Lvマス先にテレポートします。着地先にブロックがある場合は、1つ前に着地します。

        /give @s diamond_sword{lifeseal:50,teleport:3}
    */

    private final CommandClass COMMANDCLASS = new CommandClass();

    public void checkItemNBT(ItemStack item){
        NBTItem itemA = new NBTItem(item);
        switch(Objects.requireNonNull(item.getItemMeta()).getDisplayName()) {
            case "§aEmerald Sword":
                itemA.setInteger("emsw", 1);
                break;
        }
    }

    public boolean IsMineus(double i){//マイナスかどうか判別
        return Math.abs(i) != i;
    }

    @EventHandler
    public void onEntityDamageByPlayer(EntityDamageByEntityEvent e){
        if(e.getDamager().getType() == EntityType.PLAYER){
            Player p = Bukkit.getPlayer(e.getDamager().getName());
            ItemStack iteminmainhand = Objects.requireNonNull(p).getInventory().getItemInMainHand();

            if(iteminmainhand.getType() == Material.AIR) return;

            NBTItem nbtitem = new NBTItem(iteminmainhand);
            if(nbtitem.hasKey("lifesteal")){
                double damage = e.getDamage();
                double heallife = damage * (nbtitem.getInteger("lifesteal") / 100);
                p.setHealth(Math.min(p.getHealth() + heallife, COMMANDCLASS.getMaxHealth(p)));
            }
        }
    }

    @EventHandler
    public void onPlayerInterracted(PlayerInteractEvent e){
        PlayerInventory playerinv = e.getPlayer().getInventory();
        ItemStack iteminmainhand = playerinv.getItemInMainHand();
        Material playeritemmaterial = iteminmainhand.getType();
        Player p = e.getPlayer();

        checkItemNBT(iteminmainhand);

        if(playeritemmaterial == Material.AIR) return;
        NBTItem playeritemnbt = new NBTItem(iteminmainhand);
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){

        } else if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(playeritemmaterial == Material.DIAMOND_SWORD && playeritemnbt.hasKey("emsw")){//Debug
                Bukkit.broadcastMessage("emsw is valid");
                return;
            } else if(playeritemmaterial == Material.DIAMOND_SWORD && playeritemnbt.hasKey("teleport")){
                Teleport teleport = new Teleport(e);
                return;
            }
        }
    }
}
