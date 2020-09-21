package de.miinoo.factions.model;

import de.miinoo.factions.FactionsSystem;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.yaml.snakeyaml.events.AliasEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Miinoo_
 * 08.09.2020
 **/

public class FactionLevel implements ConfigurationSerializable {

    int level;
    boolean hasFly;
    boolean hasFill;
    int maxMember;
    int maxClaims;
    int teleportDelay;
    int maxWarps;

    public FactionLevel(int level) {
        this.level = level;

        this.hasFly = FactionsSystem.getFactionLevels().getFly(level);
        this.hasFill = FactionsSystem.getFactionLevels().getFill(level);
        this.maxMember = FactionsSystem.getFactionLevels().getMaxMember(level);
        this.maxWarps = FactionsSystem.getFactionLevels().getMaxWarps(level);
        this.maxClaims = FactionsSystem.getFactionLevels().getMaxClaims(level);
        this.teleportDelay = FactionsSystem.getFactionLevels().getWarpCooldown(level);
    }

    public FactionLevel(Map<String, Object> args) {
        level = ((Number) args.get("level")).intValue();
        hasFly = (boolean) args.get("hasFly");
        hasFill = (boolean) args.get("hasFill");
        maxMember = ((Number) args.get("maxMember")).intValue();
        maxClaims = ((Number) args.get("maxClaims")).intValue();
        teleportDelay = ((Number) args.get("teleportDelay")).intValue();
        maxWarps = ((Number) args.get("maxWarps")).intValue();
    }

    public int nextLevel() {
        if (level + 1 <= FactionsSystem.getFactionLevels().maxLevel()) {
            return level + 1;
        }
        return -1;
    }

    public FactionLevel getNextLevel() {
        return new FactionLevel(nextLevel());
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean isHasFly() {
        return hasFly;
    }

    public boolean isHasFill() {
        return hasFill;
    }

    public int getMaxMember() {
        return maxMember;
    }

    public int getMaxClaims() {
        return maxClaims;
    }

    public int getTeleportDelay() {
        return teleportDelay;
    }

    public int getMaxWarps() {
        return maxWarps;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("level", level);
        result.put("hasFly", hasFly);
        result.put("hasFill", hasFill);
        result.put("maxMember", maxMember);
        result.put("maxClaims", maxClaims);
        result.put("teleportDelay", teleportDelay);
        result.put("maxWarps", maxWarps);
        return result;
    }
}
