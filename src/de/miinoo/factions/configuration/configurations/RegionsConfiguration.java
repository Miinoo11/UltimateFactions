package de.miinoo.factions.configuration.configurations;

import com.google.gson.internal.$Gson$Preconditions;
import de.miinoo.factions.configuration.Configuration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mino
 * 19.05.2020
 */
public class RegionsConfiguration extends Configuration {

    public RegionsConfiguration(Plugin plugin, String fileName, String pluginDirectory) {
        super(plugin, fileName, pluginDirectory);
    }

    private static List<String> names = new ArrayList<>();

    @Override
    protected void setupConfig() {
        config.options().copyDefaults(true).copyHeader(true).header("In this file you can add every region name where the faction warpiece and power system should be disabled");
        save();

        if(!names.contains("defaultRegion")) {
            names.add("defaultRegion");
        }

        names.addAll(config.getStringList("Regions"));

        config.addDefault("Regions", names);
    }

    public List<String> getNames() {
        return names;
    }
}
