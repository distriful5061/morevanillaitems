package morevanillaitems.distriful5061.github.com.morevanillaitems;

import morevanillaitems.distriful5061.github.com.morevanillaitems.Command.EnderChest;
import morevanillaitems.distriful5061.github.com.morevanillaitems.Command.hp;
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

    public double getMaxHealth(Player p){
        AttributeInstance urself = Objects.requireNonNull(p).getAttribute(Attribute.valueOf("GENERIC_MAX_HEALTH"));
        double playermaxlife = Objects.requireNonNull(urself).getValue();
        return playermaxlife;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandname = command.getName();
        if(!(commandlist.contains(commandname))) return false;
        switch(commandname.toLowerCase()){
            case "enderchest":
                EnderChest enderchest = new EnderChest(sender,command,label,args);
                return enderchest.getResult();
            case "hp":
                hp hp = new hp(sender,command,label,args);
                return hp.getResult();
        }
        return false;
    }
}
