package de.miinoo.factions.configuration.configurations;

import de.miinoo.factions.configuration.Configuration;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Mino
 * 10.05.2020
 */
public class BankConfiguration extends Configuration {

    private Map<Material, Integer> items = new TreeMap<>();
    private Map<String, Integer> spawners = new TreeMap<>();

    public BankConfiguration(Plugin plugin, String fileName, String pluginDirectory) {
        super(plugin, fileName, pluginDirectory);
        loadItems();
    }

    @Override
    protected void setupConfig() {
        config.options().copyDefaults(true);
        config.addDefault("Materials.HOPPER", 1);
        config.addDefault("Spawners.CREEPER_SPAWNER.displayName", "&aCreeper Spawner");
        config.addDefault("Spawners.CREEPER_SPAWNER.value", 10000);
        save();
    }

    public void loadItems() {
        ConfigurationSection materials = config.getConfigurationSection("Materials");
        ConfigurationSection spawner = config.getConfigurationSection("Spawners");
        if (materials != null) {
            for (String key : materials.getKeys(false)) {
                Material material = Material.getMaterial(key);
                if (material == null) {
                    continue;
                }
                items.put(material, materials.getInt(key));
            }
            for(String key : spawner.getKeys(false)) {
                spawners.put(key, spawner.getInt(key + ".value"));
            }
        }
    }

    public Map<String, Integer> getSpawners() {
        return spawners;
    }

    public Map<Material, Integer> getMaterials() {
        return items;
    }

    public Collection<Material> getMaterialsCollection() {
        return items.keySet();
    }

    public String getSpawnerName(String spawner) {
        return config.getString(spawner + ".displayName");
    }

    public int getPrice(Material material) {
        return items.get(material);
    }
}
