package morevanillaitems.distriful5061.github.com.morevanillaitems;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;


public class CommandClass implements CommandExecutor {
    public static ArrayList<String> commandlist = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandname = sender.getName();
        if (commandname.equalsIgnoreCase("craft")) {
            Morevanillaitems.displayMenu((Player) sender);
            return true;
        }
        return false;
    }
}
