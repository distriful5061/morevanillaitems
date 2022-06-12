package morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners.SwordEvent;

import de.tr7zw.nbtapi.NBTItem;
import morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners.SwordEventListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Teleport {
    private final SwordEventListener SWORD_EVENT_LISTENER = new SwordEventListener();
    private boolean result;

    public Teleport(PlayerInteractEvent e){
        try {
            PlayerInventory playerinv = e.getPlayer().getInventory();
            ItemStack iteminmainhand = playerinv.getItemInMainHand();
            Material playeritemmaterial = iteminmainhand.getType();
            Player p = e.getPlayer();

            if (playeritemmaterial == Material.AIR) return;
            NBTItem playeritemnbt = new NBTItem(iteminmainhand);

            Location location = p.getLocation();
            final int TELEPORT_LEVEL = playeritemnbt.getInteger("teleport");
            double x = location.getX() * TELEPORT_LEVEL;
            double y = location.getY() * location.getYaw();
            double z = location.getZ() * TELEPORT_LEVEL;
            location.setX(x);
            location.setZ(z);

            final int Xreduce;
            final int Yreduce;
            final int Zreduce;

            if (SWORD_EVENT_LISTENER.IsMineus(x)) {
                Xreduce = 1;
            } else {
                Xreduce = -1;
            }

            if (SWORD_EVENT_LISTENER.IsMineus(y)) {
                Yreduce = 1;
            } else {
                Yreduce = -1;
            }

            if (SWORD_EVENT_LISTENER.IsMineus(y)) {
                Zreduce = 1;
            } else {
                Zreduce = -1;
            }

            while (location.getBlock().getType() != Material.AIR) {
                location.setY(location.getY() + Yreduce);//Y一つ減らしてAIRになるか試す
                if (location.getBlock().getType() == Material.AIR) {
                    break;
                }
                location.setY(location.getY() - Yreduce);

                location.setX(location.getX() + Xreduce);
                location.setZ(location.getZ() + Zreduce);
            }
            p.teleport(location);

            this.result = true;
        } catch (Exception ignored){
            this.result = false;
        }
    }

    public boolean getResult(){
        return this.result;
    }
}
