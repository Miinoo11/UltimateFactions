package de.miinoo.factions.configuration.configurations;

import de.miinoo.factions.core.configuration.Configuration;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.Faction;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
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

    public boolean enableTablist() { return configuration.getBoolean("Settings.Tab.enabled"); }
    public boolean enableScoreboard() { return configuration.getBoolean("Settings.Game.enableScoreboard");}
    public int getScoreboardUpdateCount() { return configuration.getInt("Settings.Game.scoreboardUpdateCount"); }

    // Map Settings
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

    //  Wild settings
    public boolean wildIsEnabled() { return configuration.getBoolean("Settings.Game.Wild.enabled"); }
    public World getWildTeleportWorld() { return Bukkit.getWorld(configuration.getString("Settings.Game.Wild.teleport-world"));}
    public double wildCosts() {return configuration.getDouble("Settings.Game.Wild.costs"); }
    public int wildDelay() { return configuration.getInt("Settings.Game.Wild.delay"); }
    public boolean wildTeleportSafe() { return configuration.getBoolean("Settings.Game.Wild.teleport-safe"); }
    public int wildMaxX() { return configuration.getInt("Settings.Game.Wild.wildMaxX"); }
    public int wildMaxZ() { return configuration.getInt("Settings.Game.Wild.wildMaxZ"); }
    public List<String> wildDisabledWorlds() { return configuration.getStringList("Settings.Game.Wild.disabled-worlds"); }
    public List<String> wildDisabledBiomes() { return configuration.getStringList("Settings.Game.Wild.disabled-biomes"); }

    // Townhall Settings
    public boolean townhallIsEnabled() { return configuration.getBoolean("Settings.Game.TownHall.enabled"); }
    public int getMoveCooldown() { return configuration.getInt("Settings.Game.TownHall.moveCooldown");}
    public double getDefaultHealth() { return configuration.getDouble("Settings.Game.TownHall.defaultHealth");}

    // Claim Settings
    public List<String> getClaimDisabledWorlds() { return configuration.getStringList("Settings.Game.Claims.disabled-worlds");}
    public boolean connectedClaims() { return configuration.getBoolean("Settings.Game.Claims.connected"); }
    public int getEnergyTimer() { return configuration.getInt("Settings.Game.Claims.energyTimer");}
    public int getClaimDefaultPrice() { return configuration.getInt("Settings.Game.Claims.defaultPrice");}
    public int getClaimPriceStreak() { return configuration.getInt("Settings.Game.Claims.PriceStreak");}
    public double getClaimPriceMultiplier() { return configuration.getDouble("Settings.Game.Claims.PriceMultiplier");}
    public int getEnergyMultiplier() { return configuration.getInt("Settings.Game.Claims.energyMultiplier"); }
    public boolean usePermissionInsteadOfUpgrade() {return configuration.getBoolean("Settings.Game.Claims.flyCommand.usePermissionInsteadOfUpgrade");}
    public String flyCommandPermission() { return configuration.getString("Settings.Game.Claims.flyCommand.permission");}
    public boolean isTeleportBlocker() { return configuration.getBoolean("Settings.Game.Ckaims.teleport-blocker"); }

    // Power Settings
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

    // Warpiece Settings
    public int getMinPlayerToSiege() { return configuration.getInt("Settings.Game.WarPieces.siegeMinPlayerOnline"); }
    public int getSiegePieceNeeded() { return configuration.getInt("Settings.Game.WarPieces.siegePieceNeeded");}
    public int getSiegeCount() { return configuration.getInt("Settings.Game.WarPieces.siegeCount");}
    public int getMinWarPiece() {
        return configuration.getInt("Settings.Game.WarPieces.minWarPiece");
    }
    public int getMaxWarPiece() {
        return configuration.getInt("Settings.Game.WarPieces.maxWarPiece");
    }
    public List<String> getWarPieceDisabledWorlds() { return configuration.getStringList("Settings.Game.WarPieces.disabledWorlds"); }

    public boolean canSetWarpOutSideFactionChunk() {
        return configuration.getBoolean("Settings.Warps.outsideFactionChunk");
    }

    // Interact Settings
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

    public boolean enableWarps() { return configuration.getBoolean("Settings.Game.enableWarps");}
    public boolean enableHome() { return configuration.getBoolean("Settings.Game.enableHome");}

    // Other Settings
    public boolean onlyCharacter() { return configuration.getBoolean("Settings.Other.onlyCharacter"); }
    public int getFactionNameMaximalLength() { return configuration.getInt("Settings.Other.factionNameLenght"); }
    public boolean enableChatSystem() { return configuration.getBoolean("Settings.Other.chat-system.enabled"); }
    public boolean useExtendedChatFormat() { return configuration.getBoolean("Settings.Other.extendedChatFormat.enabled"); }
    public boolean enableShop() {return configuration.getBoolean("Settings.Other.enableShop"); }
    public boolean sendTerritoryEnterMessages() {return configuration.getBoolean("Settings.Other.territory.enterMessage"); }

    public String getFactionColor() { return configuration.getString("Settings.Other.extendedChatFormat.factionColor"); }
    public String getAllyColor() { return configuration.getString("Settings.Other.extendedChatFormat.allyColor"); }
    public String getEnemyColor() { return configuration.getString("Settings.Other.extendedChatFormat.enemyColor"); }
    public String getTruceColor() { return configuration.getString("Settings.Other.extendedChatFormat.truceColor"); }
    public String getDefaultColor() { return configuration.getString("Settings.Other.extendedChatFormat.defaultColor"); }
    public String getExtendedFormat() { return configuration.getString("Settings.Other.extendedChatFormat.format"); }
    public String getExtendedFormat2() { return configuration.getString("Settings.Other.extendedChatFormat.format2"); }

    public boolean enableCreateCost() { return configuration.getBoolean("Settings.Other.creationCost.enabled");}
    public float getCreationCost() { return configuration.getInt("Settings.Other.creationCost.costs");}

    // Hook Settings
    public boolean dynMapHookIsEnabled() { return configuration.getBoolean("Settings.Hooks.DynMap.enabled"); }
    public boolean createAreaOnClaim() { return configuration.getBoolean("Settings.Hooks.DynMap.createArea"); }

    // Language Settings
    public String getLanguageIdentifier() { return configuration.getString("Settings.Language.identifier");}

    public String tabHeader(Player player) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if(FactionsSystem.isPlaceHolderAPIFound) {
            if (faction != null) {
               return PlaceholderAPI.setPlaceholders(player, configuration.getString("Settings.Tab.header")
                        .replace("%faction%", faction.getName())
                        .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("%power%", "" + faction.getPower())
                        .replace("%claims%", "" + faction.getClaimed().size())
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("\\n", "\n"));
            } else {
                return PlaceholderAPI.setPlaceholders(player, configuration.getString("Settings.Tab.header")
                        .replace("%faction%", "N/A")
                        .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("%power%", "N/A")
                        .replace("%claims%", "N/A")
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("\\n", "\n"));
            }
        } else {
            if (faction != null) {
                return configuration.getString("Settings.Tab.header")
                        .replace("%faction%", faction.getName())
                        .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("%power%", "" + faction.getPower())
                        .replace("%claims%", "" + faction.getClaimed().size())
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("\\n", "\n");
            } else {
                return configuration.getString("Settings.Tab.header")
                        .replace("%faction%", "N/A")
                        .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("%power%", "N/A")
                        .replace("%claims%", "N/A")
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("\\n", "\n");
            }
        }
    }

    public String tabFooter(Player player) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if(FactionsSystem.isPlaceHolderAPIFound) {
            if (faction != null) {
                return PlaceholderAPI.setPlaceholders(player, configuration.getString("Settings.Tab.footer")
                        .replace("%faction%", faction.getName())
                        .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("%power%", "" + faction.getPower())
                        .replace("%claims%", "" + faction.getClaimed().size())
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("\\n", "\n"));
            } else {
                return PlaceholderAPI.setPlaceholders(player, configuration.getString("Settings.Tab.footer")
                        .replace("%faction%", "N/A")
                        .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("%power%", "N/A")
                        .replace("%claims%", "N/A")
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("\\n", "\n"));
            }
        } else {
            if (faction != null) {
                return configuration.getString("Settings.Tab.footer")
                        .replace("%faction%", faction.getName())
                        .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("%power%", "" + faction.getPower())
                        .replace("%claims%", "" + faction.getClaimed().size())
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("\\n", "\n");
            } else {
                return configuration.getString("Settings.Tab.footer")
                        .replace("%faction%", "N/A")
                        .replace("%count%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("%power%", "N/A")
                        .replace("%claims%", "N/A")
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size())
                        .replace("\\n", "\n");
            }
        }
    }

}
