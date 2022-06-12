package morevanillaitems.distriful5061.github.com.morevanillaitems.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerServerEventListener implements Listener{
    private final BowEventListener BOWEVENTLISTNER = new BowEventListener();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        BOWEVENTLISTNER.addBowLeftClicked(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        BOWEVENTLISTNER.removeBowLeftClicked(e.getPlayer());
    }
}
