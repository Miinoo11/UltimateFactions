package de.miinoo.factions.region;

import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;
import java.util.stream.Collectors;

public class Region implements ConfigurationSerializable {

    private UUID uuid;
    private String name;
    private Location location1;
    private Location location2;
    private Cuboid cuboid;
    private List<Flag> flags;

    public Region(UUID uuid, String name, Location location1, Location location2) {
        this.uuid = uuid;
        this.name = name;
        this.location1 = location1;
        this.location2 = location2;

        cuboid = new Cuboid(location1, location2);
        this.flags = new ArrayList<>();

        flags.add(new Flag("disable-pvp", GUITags.Region_Flags_DisablePVP.getMessage(), false, XMaterial.IRON_SWORD.parseMaterial()));
        flags.add(new Flag("disable-build", GUITags.Region_Flags_DisableBreak.getMessage(), false, XMaterial.IRON_PICKAXE.parseMaterial()));
        flags.add(new Flag("disable-place", GUITags.Region_Flags_DisablePlace.getMessage(), false, XMaterial.GRASS_BLOCK.parseMaterial()));
    }

    public Region(Map<String, Object> args) {
        uuid = UUID.fromString((String) args.get("uuid"));
        name = (String) args.get("name");
        location1 = (Location) args.get("location1");
        location2 = (Location) args.get("location2");
        cuboid = (Cuboid) args.get("cuboid");
        flags = ((List<Flag>) args.get("flags"));
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

    public List<Flag> getFlags() {
        return flags;
    }

    public boolean hasFlag(String identifier) {
        return flags.stream().anyMatch(flag -> flag.getIdentifier().equals(identifier));
    }

    public void toggleFlagValue(Flag flag) {
        flag.toggleValue();
    }

    public boolean getFlagValue(String identifier) {
        return flags.stream().filter(flag -> flag.getIdentifier().equals(identifier)).findFirst().orElse(null).isEnabled();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("uuid", uuid.toString());
        result.put("name", name);
        result.put("location1", location1);
        result.put("location2", location2);
        result.put("cuboid", cuboid);
        result.put("flags", flags);
        return result;
    }
}
