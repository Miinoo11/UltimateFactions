package de.miinoo.factions.model;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Region implements ConfigurationSerializable {

    private UUID uuid;
    private String name;
    private Location location1;
    private Location location2;
    private Cuboid cuboid;

    public Region(UUID uuid, String name, Location location1, Location location2) {
        this.uuid = uuid;
        this.name = name;
        this.location1 = location1;
        this.location2 = location2;

        cuboid = new Cuboid(location1, location2);
    }

    public Region(Map<String, Object> args) {
        uuid = UUID.fromString((String) args.get("uuid"));
        name = (String) args.get("name");
        location1 = (Location) args.get("location1");
        location2 = (Location) args.get("location2");
        cuboid = (Cuboid) args.get("cuboid");
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Location getLocation1() {
        return location1;
    }

    public Location getLocation2() {
        return location2;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("uuid", uuid.toString());
        result.put("name", name);
        result.put("location1", location1);
        result.put("location2", location2);
        result.put("cuboid", cuboid);
        return result;
    }
}
