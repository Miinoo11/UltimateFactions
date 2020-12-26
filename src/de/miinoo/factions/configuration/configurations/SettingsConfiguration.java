package de.miinoo.factions.configuration.configurations;

import de.miinoo.factions.api.configuration.Configuration;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.Faction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

/**
 * @author Mino
 * 14.04.2020
 */
public class SettingsConfiguration extends Configuration {

    public SettingsConfiguration(File file) {
        super(file);
    }

    @Override
    protected void loadConfiguration() {
        if(isVirgin()) {
            try {
                InputStream inputStream = getClass().getResourceAsStream("./settings.yml");
                Files.copy(inputStream, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // @Override
   // protected void setupconfiguration() {
   //     configuration.options().copyDefaults(true);
   //     configuration.addDefault("Settings.Warps.outsideFactionChunk", false);
   //     configuration.addDefault("Settings.Map.Height", 11);
   //     configuration.addDefault("Settings.Map.Width", 15);
   //     configuration.addDefault("Settings.Map.Colors.Indicators.player", "AQUA");
   //     configuration.addDefault("Settings.Map.Colors.Indicators.border", "BLACK");
   //     configuration.addDefault("Settings.Map.Colors.empty", "BLUE");
   //     configuration.addDefault("Settings.Map.Colors.enemy", "DARK_RED");
   //     configuration.addDefault("Settings.Map.Colors.truce", "GOLD");
   //     configuration.addDefault("Settings.Map.Colors.ally", "LIGHT_PURPLE");
   //     configuration.addDefault("Settings.Map.Colors.ownFaction", "GREEN");
   //     configuration.addDefault("Settings.Map.Colors.hostile", "RED");
   //     configuration.addDefault("Settings.Game.teleportDelay", 6);
   //     configuration.addDefault("Settings.Game.TownHall.defaultHealth", 100.0);
   //     configuration.addDefault("Settings.Game.TownHall.moveCooldown", 24);
   //     configuration.addDefault("Settings.Game.TownHall.maxLevel", 5);
   //     configuration.addDefault("Settings.Game.Claims.maxClaims", 100);
   //     configuration.addDefault("Settings.Game.Claims.defaultPrice", 100);
   //     configuration.addDefault("Settings.Game.Claims.PriceStreak", 5);
   //     configuration.addDefault("Settings.Game.Claims.PriceMultiplier", 1.5);
   //     configuration.addDefault("Settings.Game.Claims.energyTimer", 15);
   //     configuration.addDefault("Settings.Game.Power.powerJoinIncrease", 10);
   //     configuration.addDefault("Settings.Game.Power.powerKillIncrease", 5);
   //     configuration.addDefault("Settings.Game.Power.powerDecrease", 5);
   //     configuration.addDefault("Settings.Game.Power.maxPower", 200);
   //     configuration.addDefault("Settings.Game.Power.minPower", -25);
   //     configuration.addDefault("Settings.Game.Power.player1Power", 35);
   //     configuration.addDefault("Settings.Game.Power.player2Power", 25);
   //     configuration.addDefault("Settings.Game.Power.player3Power", 15);
   //     configuration.addDefault("Settings.Game.Power.powerRegenCount", 600);
   //     configuration.addDefault("Settings.Game.Power.powerRegenValue", 15);
   //     configuration.addDefault("Settings.Game.WarPieces.maxWarPiece", 25);
   //     configuration.addDefault("Settings.Game.WarPieces.minWarPiece", -25);
   //     configuration.addDefault("Settings.Game.WarPieces.siegeCount", 1800);
   //     configuration.addDefault("Settings.Game.WarPieces.siegePieceNeeded", 25);
   //     configuration.addDefault("Settings.Game.InteractAll.chest", false);
   //     configuration.addDefault("Settings.Game.InteractAll.trapped_chest", false);
   //     configuration.addDefault("Settings.Game.InteractAll.ender_chest", false);
   //     configuration.addDefault("Settings.Game.InteractAll.shulker", false);
   //     configuration.addDefault("Settings.Game.InteractAll.trapdoor", false);
   //     configuration.addDefault("Settings.Game.InteractAll.door", false);
   //     configuration.addDefault("Settings.Game.InteractAll.button", false);
   //     configuration.addDefault("Settings.Game.InteractAll.lever", false);
   //     configuration.addDefault("Settings.Game.InteractAll.fence_gate", false);
   //     configuration.addDefault("Settings.Game.InteractAll.oven", false);
   //     configuration.addDefault("Settings.Game.InteractAll.dispenser", false);
   //     configuration.addDefault("Settings.Game.InteractAll.dropper", false);
   //     configuration.addDefault("Settings.Game.InteractAll.anvil", false);
   //     configuration.addDefault("Settings.Game.InteractAll.comparator", false);
   //     configuration.addDefault("Settings.Game.InteractAll.repeater", false);
   //     configuration.addDefault("Settings.Game.InteractAll.hopper", false);
   //     configuration.addDefault("Settings.Game.InteractAll.barrel", false);
   //     configuration.addDefault("Settings.Game.InteractAll.fillBucket", false);
   //     configuration.addDefault("Settings.Game.InteractAll.emptyBucket", false);
   //     configuration.addDefault("Settings.Tab.header", "&5&lThis is a &e&lHeader\\n&9Edit everything!");
   //     configuration.addDefault("Settings.Tab.footer", "&5&lThis is a &e&lFooter\\n&9Online: &e%count%\\n&9Power: &e%power%!");
   //     save();
   // }
    public boolean enableTablist() { return configuration.getBoolean("Settings.Tab.enabled"); }
    public int getFactionNameMaximalLength() { return configuration.getInt("Settings.Other.factionNameLenght");}
    public boolean enableScoreboard() { return configuration.getBoolean("Settings.Game.enableScoreboard");}
    public String getChatColor(String name) {
        return configuration.getString("Settings.Map.Colors." + name);
    }
    public String getPlayerIndicatorColor() {
        return configuration.getString("Settings.Map.Colors.Indicators.player");
    }
    public String getBorderIndicatorColor() {
        return configuration.getString("Settings.Map.Colors.Indicators.border");
    }
    public int getHeight() {
        return configuration.getInt("Settings.Map.Height");
    }
    public int getWidth() {
        return configuration.getInt("Settings.Map.Width");
    }
    public int getTopUpdater() { return configuration.getInt("Settings.Game.TopFactions.updaterCount"); }
    public boolean wildIsEnabled() { return configuration.getBoolean("Settings.Game.Wild.enabled"); }
    public double wildCosts() {return configuration.getDouble("Settings.Game.Wild.costs"); }
    public int wildDelay() { return configuration.getInt("Settings.Game.Wild.delay"); }
    public boolean wildTeleportSafe() { return configuration.getBoolean("Settings.Game.Wild.teleport-safe"); }
    public int wildMaxX() { return configuration.getInt("Settings.Game.Wild.wildMaxX"); }
    public int wildMaxZ() { return configuration.getInt("Settings.Game.Wild.wildMaxZ"); }
    public List<String> wildDisabledWorlds() { return configuration.getStringList("Settings.Game.Wild.disabled-worlds"); }
    public List<String> wildDisabledBiomes() { return configuration.getStringList("Settings.Game.Wild.disabled-biomes"); }
    public boolean townhallIsEnabled() { return configuration.getBoolean("Settings.Game.TownHall.enabled"); }
    public int getMoveCooldown() { return configuration.getInt("Settings.Game.TownHall.moveCooldown");}
    public double getDefaultHealth() { return configuration.getDouble("Settings.Game.TownHall.defaultHealth");}
    public int getEnergyTimer() { return configuration.getInt("Settings.Game.Claims.energyTimer");}
    public int getClaimDefaultPrice() { return configuration.getInt("Settings.Game.Claims.defaultPrice");}
    public int getClaimPriceStreak() { return configuration.getInt("Settings.Game.Claims.PriceStreak");}
    public double getClaimPriceMultiplier() { return configuration.getDouble("Settings.Game.Claims.PriceMultiplier");}
    public int getEnergyMultiplier() { return configuration.getInt("Settings.Game.Claims.energyMultiplier"); }
    public double getPowerJoinIncrease() {
        return configuration.getDouble("Settings.Game.Power.powerJoinIncrease");
    }
    public double getPowerLeaveDecrease() { return configuration.getDouble("Settings.Game.Power.powerLeaveDecrease"); }
    public double getPowerKillIncrease() {
        return configuration.getDouble("Settings.Game.Power.powerKillIncrease");
    }
    public double getPowerKillDecrease() { return configuration.getDouble("Settings.Game.Power.powerKillDecrease"); }
    public double getMaxPower() {
        return configuration.getDouble("Settings.Game.Power.maxPower");
    }
    public double getMinPower() {
        return configuration.getDouble("Settings.Game.Power.minPower");
    }
    public double getPlayer1Power() {
        return configuration.getDouble("Settings.Game.Power.player1Power");
    }
    public double getPlayer2Power() {
        return configuration.getDouble("Settings.Game.Power.player2Power");
    }
    public double getPlayer3Power() {
        return configuration.getDouble("Settings.Game.Power.player3Power");
    }
    public int getPowerRegenValue() { return configuration.getInt("Settings.Game.Power.powerRegenValue");}
    public int getPowerRegenCount() { return configuration.getInt("Settings.Game.Power.powerRegenCount");}
    public int getSiegePieceNeeded() { return configuration.getInt("Settings.Game.WarPieces.siegePieceNeeded");}
    public int getSiegeCount() { return configuration.getInt("Settings.Game.WarPieces.siegeCount");}
    public int getMinWarPiece() {
        return configuration.getInt("Settings.Game.WarPieces.minWarPiece");
    }
    public int getMaxWarPiece() {
        return configuration.getInt("Settings.Game.WarPieces.maxWarPiece");
    }

    public boolean canSetWarpOutSideFactionChunk() {
        return configuration.getBoolean("Settings.Warps.outsideFactionChunk");
    }
    public boolean canUseChest() {
        return configuration.getBoolean("Settings.Game.InteractAll.chest");
    }
    public boolean canUseTrappedChest() {
        return configuration.getBoolean("Settings.Game.InteractAll.trapped_chest");
    }
    public boolean canUseEnderChest() {
        return configuration.getBoolean("Settings.Game.InteractAll.ender_chest");
    }
    public boolean canUseShulker() {
        return configuration.getBoolean("Settings.Game.InteractAll.shulker");
    }
    public boolean canUseTrapDoor() {
        return configuration.getBoolean("Settings.Game.InteractAll.trapdoor");
    }
    public boolean canUseDoor() {
        return configuration.getBoolean("Settings.Game.InteractAll.door");
    }
    public boolean canUseButton() {
        return configuration.getBoolean("Settings.Game.InteractAll.button");
    }
    public boolean canUseLever() {
        return configuration.getBoolean("Settings.Game.InteractAll.lever");
    }
    public boolean canUseFenceGate() {
        return configuration.getBoolean("Settings.Game.InteractAll.fence_gate");
    }
    public boolean canUseBarrel() {
        return configuration.getBoolean("Settings.Game.InteractAll.barrel");
    }
    public boolean canUseOven() {
        return configuration.getBoolean("Settings.Game.InteractAll.oven");
    }
    public boolean canUseDispenser() {
        return configuration.getBoolean("Settings.Game.InteractAll.dispenser");
    }
    public boolean canUseDropper() {
        return configuration.getBoolean("Settings.Game.InteractAll.dropper");
    }
    public boolean canUseAnvil() {
        return configuration.getBoolean("Settings.Game.InteractAll.anvil");
    }
    public boolean canUseComparator() {
        return configuration.getBoolean("Settings.Game.InteractAll.comparator");
    }
    public boolean canUseRepeater() {
        return configuration.getBoolean("Settings.Game.InteractAll.repeater");
    }
    public boolean canUseHopper() {
        return configuration.getBoolean("Settings.Game.InteractAll.hopper");
    }
    public boolean canFillBucket() {
        return configuration.getBoolean("Settings.Game.InteractAll.fillBucket");
    }
    public boolean canEmptyBucket() {
        return configuration.getBoolean("Settings.Game.InteractAll.emptyBucket");
    }

    public boolean onlyCharacter() { return configuration.getBoolean("Settings.Other.onlyCharacter"); }
    public boolean useExtendedChatFormat() { return configuration.getBoolean("Settings.Other.extendedChatFormat.enabled"); }
    public boolean enableShop() {return configuration.getBoolean("Settings.Other.enableShop"); }

    public String getFactionColor() { return configuration.getString("Settings.Other.extendedChatFormat.factionColor"); }
    public String getAllyColor() { return configuration.getString("Settings.Other.extendedChatFormat.allyColor"); }
    public String getEnemyColor() { return configuration.getString("Settings.Other.extendedChatFormat.enemyColor"); }
    public String getTruceColor() { return configuration.getString("Settings.Other.extendedChatFormat.truceColor"); }
    public String getDefaultColor() { return configuration.getString("Settings.Other.extendedChatFormat.defaultColor"); }
    public String getExtendedFormat() { return configuration.getString("Settings.Other.extendedChatFormat.format"); }
    public String getExtendedFormat2() { return configuration.getString("Settings.Other.extendedChatFormat.format2"); }


    public String tabHeader(Player player) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if (faction != null) {
            return configuration.getString("Settings.Tab.header")
                    .replace("%faction%", faction.getName())
                    .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                    .replace("%power%", "" + faction.getPower())
                    .replace("%claims%", "" + faction.getClaimed().size())
                    .replace("\\n", "\n");
        } else {
            return configuration.getString("Settings.Tab.header")
                    .replace("%faction%", "N/A")
                    .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                    .replace("%power%", "N/A")
                    .replace("%claims%", "N/A")
                    .replace("\\n", "\n");
        }
    }
    //
    public String tabFooter(Player player) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if (faction != null) {
            return configuration.getString("Settings.Tab.footer")
                    .replace("%faction%", faction.getName())
                    .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                    .replace("%power%", "" + faction.getPower())
                    .replace("%claims%", "" + faction.getClaimed().size())
                    .replace("\\n", "\n");
        } else {
            return configuration.getString("Settings.Tab.footer")
                    .replace("%faction%", "N/A")
                    .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                    .replace("%power%", "N/A")
                    .replace("%claims%", "N/A")
                    .replace("\\n", "\n");
        }
    }

}
