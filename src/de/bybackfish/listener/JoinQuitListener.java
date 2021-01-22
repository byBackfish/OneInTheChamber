package de.bybackfish.listener;

import de.bybackfish.OneInTheChamber;
import de.bybackfish.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {


    Config config = OneInTheChamber.getInstance().getConf();
    int needed = config.getInt("minplayer");

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        event.setJoinMessage(config.getString("message.join", event.getPlayer().getDisplayName()));

        if(Bukkit.getOnlinePlayers().size() == needed)
            OneInTheChamber.getInstance().getCountdown().start();


        player.teleport(OneInTheChamber.getInstance().getLocations().getLocation("spawn"));
        player.setHealthScale(2);
        player.setHealthScaled(true);
        player.setMaxHealth(2);
        player.setHealth(2);

        player.setLevel(2020);


        player.setFoodLevel(20);
        player.getInventory().clear();

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){



      event.setQuitMessage(config.getString("message.leave", event.getPlayer().getDisplayName()));

        System.out.println(Bukkit.getOnlinePlayers().size());

        if(Bukkit.getOnlinePlayers().size() == needed) {
            OneInTheChamber.getInstance().getCountdown().stop();

            Bukkit.broadcastMessage(config.getString("message.stopped"));
        }



    }


}
