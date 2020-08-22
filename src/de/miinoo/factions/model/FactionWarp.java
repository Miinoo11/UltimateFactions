package de.miinoo.factions.model;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mino
 * 10.05.2020
 */
public class FactionWarp implements ConfigurationSerializable {

    private String name;
    private String password;
    private Location location;

    public FactionWarp(String name, String password, Location location) {
        this.name = name;
        this.password = password;
        this.location = location;
    }

    public FactionWarp(Map<String, Object> args) {
        name = (String) args.get("name");
        password = (String) args.get("password");
        location = (Location) args.get("location");
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Location getLocation() {
        return location;
    }

    public boolean hasPassword() {
        return password != null;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> results = new HashMap<>();
        results.put("name", name);
        results.put("password", password);
        results.put("location", location);
        return results;
    }
}
