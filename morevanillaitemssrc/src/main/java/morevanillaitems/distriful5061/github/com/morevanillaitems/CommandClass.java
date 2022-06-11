package morevanillaitems.distriful5061.github.com.morevanillaitems;

import morevanillaitems.distriful5061.github.com.morevanillaitems.Command.EnderChest;
import morevanillaitems.distriful5061.github.com.morevanillaitems.Command.hp;
import morevanillaitems.distriful5061.github.com.morevanillaitems.Command.test;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;


public class CommandClass implements CommandExecutor {
    public static ArrayList<String> commandlist = new ArrayList<>();
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
            case "test":
                test test = new test(sender,command,label,args);
                return test.getResult();
        }
        return false;
    }
}
