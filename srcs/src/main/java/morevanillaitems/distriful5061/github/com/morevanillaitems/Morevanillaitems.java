package morevanillaitems.distriful5061.github.com.morevanillaitems;

import org.bukkit.plugin.java.JavaPlugin;

public final class Morevanillaitems extends JavaPlugin {
    private Morevanillaitems plugin;

    public Morevanillaitems getPlugin(){
        return plugin;
    }

    public void loggerinfo(String content){
        getLogger().info(content);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        loggerinfo("Minigame plugin booting...");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
