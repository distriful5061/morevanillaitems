package morevanillaitems.distriful5061.github.com.morevanillaitems;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;


public class CommandClass implements CommandExecutor {
    public static ArrayList<String> commandlist = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandname = command.getName();
        if(!(commandlist.contains(commandname))) return false;
        switch(commandname.toLowerCase()){
            case "craft":
                Morevanillaitems.CraftdisplayMenu((Player) sender);
                return true;
            case "enderchest":
                return true;
            case "hp":
                Player p = Bukkit.getPlayer(sender.getName());
                AttributeInstance urself = Objects.requireNonNull(p).getAttribute(Attribute.valueOf("GENERIC_MAX_HEALTH"));
                double playermaxlife = Objects.requireNonNull(urself).getValue();
                sender.sendMessage(playermaxlife +":"+p.getHealth());
                return true;
            case "test":
                sender.sendMessage("test");
                return true;
        }
        return false;
    }
}
