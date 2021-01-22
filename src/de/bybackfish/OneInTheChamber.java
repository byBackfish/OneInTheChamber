package de.bybackfish;

import com.mojang.authlib.GameProfile;
import de.bybackfish.command.SetLocationCommand;
import de.bybackfish.config.Config;
import de.bybackfish.config.Locations;
import de.bybackfish.countdown.Countdown;
import de.bybackfish.game.Game;
import de.bybackfish.game.GameState;
import de.bybackfish.listener.HitListener;
import de.bybackfish.listener.JoinQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class OneInTheChamber extends JavaPlugin {

    private static OneInTheChamber instance;
    private Config config;
    public GameState gameState;
    private Countdown countdown;
    private Game game;
    private Locations locations;

    @Override
    public void onEnable() {
        System.out.println("Plugin Starting");
            instance = this;
            config = new Config( "plugins/OneInTheChamber/config.yml");
            locations = new Locations("plugins/OneInTheChamber/location.yml");
            countdown = new Countdown();
            gameState = GameState.LOBBY;
            game = new Game();


        registerCommands();
        registerListeners();

    }

    @Override
    public void onDisable() {
        countdown.stop();

        for(Player player : Bukkit.getOnlinePlayers()){
            player.kickPlayer("Server is Restarting you kek");
        }


    }


    private void registerCommands(){

        getCommand("setloc").setExecutor(new SetLocationCommand());

    }

    private void registerListeners(){

        Bukkit.getServer().getPluginManager().registerEvents(new JoinQuitListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new HitListener(), this);

    }

    public static OneInTheChamber getInstance(){return instance;}
    public Config getConf(){return config;}
    public Countdown getCountdown(){return countdown;}
    public Game getGame(){return game;}
    public Locations getLocations(){return locations;}
}
