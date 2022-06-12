package morevanillaitems.distriful5061.github.com.morevanillaitems.Command;

import morevanillaitems.distriful5061.github.com.morevanillaitems.CommandClass;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class hp {
    private boolean result;
    private final CommandSender sender;
    private final Command command;
    private final String label;
    private final String[] args;

    private final CommandClass COMMANDCLASS = new CommandClass();

    public hp(CommandSender sender, Command command, String label, String[] args) {
        this.sender = sender;
        this.command =  command;
        this.label = label;
        this.args = args;
        try {
            //Do any
            Player p = Bukkit.getPlayer(sender.getName());
            double playermaxlife = COMMANDCLASS.getMaxHealth(p);
            sender.sendMessage(playermaxlife + ":" + Objects.requireNonNull(p).getHealth());
            result = true;
        } catch(Exception ignored){
            result = false;
        }
    }

    public boolean getResult(){
        return result;
    }

    public String getCommandName(){
        return command.getName();
    }

    public CommandSender getCommandSender(){
        return sender;
    }

    public Command getCommand(){
        return this.command;
    }

    public String getLabel(){
        return this.label;
    }

    public String[] getArgs(){
        return this.args;
    }
}
