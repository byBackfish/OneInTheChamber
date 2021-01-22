package de.bybackfish.listener;

import de.bybackfish.OneInTheChamber;
import de.bybackfish.config.Config;
import de.bybackfish.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class HitListener implements Listener {

    private Config config = OneInTheChamber.getInstance().getConf();

    @EventHandler
    public void onHit(EntityDamageEvent event){

        if(!(OneInTheChamber.getInstance().gameState == GameState.INGAME)){ event.setCancelled(true); return;}
        if(!(event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE)){ event.setCancelled(true); return;}

    }

    @EventHandler
    public void onKill(PlayerDeathEvent event){

        OneInTheChamber.getInstance().getGame().addKill(event.getEntity().getKiller());
        event.getEntity().sendMessage(config.getString("message.death", event.getEntity().getKiller().getName()));
        event.getEntity().getKiller().sendMessage(config.getString("message.kill", event.getEntity().getName()));

        event.getDrops().clear();




    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){

        event.getPlayer().getInventory().setItem(0, new ItemStack(Material.BOW));
        event.getPlayer().getInventory().setItem(8, new ItemStack(Material.ARROW));


        int random = new Random().nextInt(Bukkit.getOnlinePlayers().size());
        event.getPlayer().teleport(OneInTheChamber.getInstance().getLocations().getLocation("loc" + random));

    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof Arrow) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }



}
