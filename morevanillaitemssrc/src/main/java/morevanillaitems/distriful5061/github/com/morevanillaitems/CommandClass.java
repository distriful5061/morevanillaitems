package morevanillaitems.distriful5061.github.com.morevanillaitems;

import org.bukkit.Bukkit;
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
        switch(commandname.toLowerCase()){
            case "craft":
                Morevanillaitems.displayMenu((Player) sender);
                return true;
            case "hp":
                Player p = Bukkit.getPlayer(sender.getName());
                sender.sendMessage(String.valueOf(Objects.requireNonNull(p).getHealth()));
        }
        return false;
    }
}
