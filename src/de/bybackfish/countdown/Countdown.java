package de.bybackfish.countdown;

import de.bybackfish.OneInTheChamber;
import de.bybackfish.config.Config;
import de.bybackfish.game.Game;
import de.bybackfish.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Countdown {

    private final ScheduledExecutorService service = Executors
            .newSingleThreadScheduledExecutor();

    int countdown;
    Config config = OneInTheChamber.getInstance().getConf();

    public void start(){

        System.out.println("Countdown is now Starting!");
        countdown = OneInTheChamber.getInstance().getConf().getInt("countdown");

        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                if(countdown == 60 || countdown == 30 || countdown == 20 || countdown == 10 || (countdown < 6 && countdown > 0)){

                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.sendMessage(config.getString("message.countdown", String.valueOf(countdown)));
                    }

                }

                if(countdown <= 0){

                    OneInTheChamber.getInstance().getGame().create();
                    stop();

                }

                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.setLevel(countdown);
            player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                }

                countdown--;

            }


        }, 1, 1, TimeUnit.SECONDS);

    }

    public void stop() {
        service.shutdown();
    }
}
