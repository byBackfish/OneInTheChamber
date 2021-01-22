package de.bybackfish.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class Locations {

    private File file;
    private FileConfiguration configuration;

    public Locations(String path) {
        file = new File(path);
        configuration = YamlConfiguration.loadConfiguration(file);


    }

    public void setLocation(String name, Location loc) {
        configuration.set(name + ".world", loc.getWorld().getName());
        configuration.set(name + ".x", loc.getX());
        configuration.set(name + ".y", loc.getY());
        configuration.set(name + ".z", loc.getZ());
        configuration.set(name + ".yaw", loc.getYaw());
        configuration.set(name + ".pitch", loc.getPitch());
        save();
    }

    public Location getLocation(String name) {
        Location loc;
        try {
            World w = Bukkit.getWorld(configuration.getString(name + ".world"));
            double x = configuration.getDouble(name + ".x");
            double y = configuration.getDouble(name + ".y");
            double z = configuration.getDouble(name + ".z");
            loc = new Location(w, x, y, z);
            loc.setYaw(configuration.getInt(name + ".yaw"));
            loc.setPitch(configuration.getInt(name + ".pitch"));
        } catch (Exception ex) {
            loc = Bukkit.getWorld("world").getSpawnLocation();
        }
        return loc;
    }



    public void save(){
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
