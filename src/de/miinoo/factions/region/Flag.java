package de.miinoo.factions.region;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flag implements ConfigurationSerializable {

    private String identifier;
    private String name;
    private boolean enabled;
    private Material material;

    public Flag(String identifier, String name, boolean enabledByDefault, Material material) {
        this.identifier = identifier;
        this.name = name;
        this.enabled = enabledByDefault;
        this.material = material;
    }

    public Flag(Map<String, Object> args) {
        this.identifier = (String) args.get("identifier");
        this.name = (String) args.get("name");
        this.enabled = (boolean) args.get("enabled");
        this.material = Material.getMaterial(String.valueOf(args.get("material")));
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    public void toggleValue() {
        enabled = !enabled;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("identifier", identifier);
        result.put("name", name);
        result.put("enabled", enabled);
        result.put("material", material.toString());
        return result;
    }
}
