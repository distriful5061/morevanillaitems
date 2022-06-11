package morevanillaitems.distriful5061.github.com.morevanillaitems.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EnderChest {
    private boolean result;
    private final CommandSender sender;
    private final Command command;
    private final String label;
    private final String[] args;

    public EnderChest(CommandSender sender, Command command, String label, String[] args) {
        this.sender = sender;
        this.command =  command;
        this.label = label;
        this.args = args;
        try{

            result = true;
        } catch (Exception e){
            result = false;
        }
    }

    public boolean getResult(){
        return this.result;
    }

    public String getCommandName(){
        return this.command.getName();
    }

    public CommandSender getCommandSender(){
        return this.sender;
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
