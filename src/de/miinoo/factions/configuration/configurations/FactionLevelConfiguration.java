package de.miinoo.factions.configuration.configurations;

import de.miinoo.factions.core.configuration.Configuration;

import java.io.File;
import java.util.List;

/**
 * @author Miinoo_
 * 06.09.2020
 **/

public class FactionLevelConfiguration extends Configuration {

    public FactionLevelConfiguration(File file) {
        super(file);
    }

    public int getCost(int level) { return configuration.getInt(level + ".cost");}
    public boolean hasFly(int level) { return configuration.getBoolean(level + ".fly");}
    public boolean hasFill(int level) { return configuration.getBoolean(level + ".fill");}
    public int getMaxMember(int level) { return configuration.getInt(level + ".member");}
    public int getMaxWarps(int level) { return configuration.getInt(level + ".warps");}
    public int getWarpCooldown(int level) { return configuration.getInt(level + ".warp-cooldown");}
    public int getMaxClaims(int level) { return configuration.getInt(level + ".claims"); }
    public double getMobDropMultiplier(int level) { return configuration.getDouble(level + ".mobdrop-multiplier"); }
    public int getMaxActiveQuests(int level) { return configuration.getInt(level + ".max-active-quests"); }
    public double getGrowSpeedMultiplier(int level) { return configuration.getDouble(level + ".grow-speed"); }

    public int maxLevel() { return configuration.getValues(false).size() - 1; }

    public boolean isEffectsEnabled(int level) { return configuration.getBoolean(level + ".potion-effects.enabled"); }
    public List<String> getEffects(int level) { return configuration.getStringList(level + ".potion-effects.effects");}
}
