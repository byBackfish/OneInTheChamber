package de.bybackfish.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private  File file;
    private FileConfiguration configuration;

    public Config(String  path) {
        file = new File(path);
        configuration = YamlConfiguration.loadConfiguration(file);

        configuration.addDefault("minplayer", 2);
        configuration.addDefault("maxplayer", 6);
        configuration.addDefault("world", "world");
        configuration.addDefault("countdown", 60);
        configuration.addDefault("message.countdown", "The Game is Starting in %0% Seconds!");
        configuration.addDefault("messages.win", "The Player %0% has Won the Game!");
        configuration.addDefault("message.kill", "You killed %0%!");
        configuration.addDefault("message.death", "%0% killed you! Bad!");
        configuration.addDefault("message.join", "[+] %0%");
        configuration.addDefault("message.leave", "[-] %0%");
        configuration.addDefault("message.stop", "The Game stopped, because there are not enough players.");
        configuration.options().copyDefaults(true);

        save();


    }


    public int getInt(String path){
        return configuration.getInt(path);
    }

    public String getString(String path, String... args){

        if(configuration.getString(path) == null) return "The String " + path + " could not be Found!";

        String out = configuration.getString(path);

        for(int i = 0; i < args.length; i++){
            out = out.replace("%" + i + "%", args[i]);
        }

        return out;
    }


    public void save(){
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
