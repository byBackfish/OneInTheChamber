package de.bybackfish.command;

import de.bybackfish.OneInTheChamber;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLocationCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player)) return true;

        Player player = (Player) commandSender;

        if(!player.hasPermission("oitc.setloc"))return true;

        if(args.length != 1) return true;

        OneInTheChamber.getInstance().getLocations().setLocation("loc" + args[0], player.getLocation());
        player.sendMessage(OneInTheChamber.getInstance().getConf().getString("message.locset", args[0]));



        return false;
    }
}
