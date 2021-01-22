package de.bybackfish.game;

import de.bybackfish.OneInTheChamber;
import de.bybackfish.config.Config;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.Map.Entry;

import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;

public class Game {

    private Map<Player, Integer> kills = new HashMap<>();
    private Config config = OneInTheChamber.getInstance().getConf();

    public Game() {
        System.out.println("Game initialized");
    }

    public void create() {

        System.out.println("Game STarted!");

        int index = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            kills.put(player, 0);
            player.sendMessage(config.getString("start"));
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
            player.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 10);
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 3, 3);


            player.getInventory().setItem(0, new ItemStack(Material.BOW));
            player.getInventory().setItem(8, new ItemStack(Material.ARROW));
            player.teleport(OneInTheChamber.getInstance().getLocations().getLocation("loc" + index));
            index++;


        }



        OneInTheChamber.getInstance().gameState = GameState.INGAME;


    }

    public void end() {

        Map<Player, Integer> sorted = sort(kills);



        for (Player player : Bukkit.getOnlinePlayers()) {

            player.sendMessage(config.getString("end"));

            player.sendMessage(sorted.entrySet().toArray().toString());

            player.setGameMode(GameMode.SPECTATOR);
            player.getInventory().clear();
            player.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 10);
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 3, 3);

            System.out.println("Game Ended");
        }

        OneInTheChamber.getInstance().gameState = GameState.END;

    }

    public static Map<Player, Integer> sort(Map<Player, Integer> map){


        return map
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }

    public void addKill(Player player){

        player.getInventory().addItem(new ItemStack(Material.ARROW));
        kills.put(player, kills.get(player) +1 );

        if(kills.get(player) >= 50){
            end();
        }

    }
}
