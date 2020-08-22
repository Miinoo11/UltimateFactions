package de.miinoo.factions.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public abstract class Configuration {
    protected Plugin plugin;
    protected String fileName;
    protected String pluginDirectory;
    protected File file;
    protected FileConfiguration config;

    public Configuration(Plugin plugin, String fileName, String pluginDirectory){
        this.plugin = plugin;
        this.fileName = fileName;
        this.pluginDirectory = pluginDirectory;
        this.file = new File(plugin.getDataFolder()+pluginDirectory,fileName);
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    protected abstract void setupConfig();

    public void save(){
        try{
            config.save(file);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void initFile(){
        setupConfig();
        save();
    }

}
