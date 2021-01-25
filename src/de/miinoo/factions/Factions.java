package de.miinoo.factions;

import de.miinoo.factions.configuration.configurations.FactionChestsConfiguration;
import de.miinoo.factions.configuration.configurations.FactionConfiguration;
import de.miinoo.factions.factionchest.FactionChest;
import de.miinoo.factions.model.*;
import org.bukkit.*;

import java.util.*;

/**
 * @author Mino
 * 10.04.2020
 */
public class Factions {

    private final FactionsSystem system;
    private final FactionConfiguration configuration = new FactionConfiguration();
    private final Set<Faction> factions;
    private final Map<FactionChunk, Faction> factionChunks = new HashMap<>();
    private final Map<UUID, Faction> players = new HashMap<>();

    public Factions(FactionsSystem system) {
        this.system = system;
        factions = new HashSet<>();
        factions.addAll(configuration.getFactions());
        for (Faction faction : factions) {
            for (UUID uuid : faction.getPlayers()) {
                players.put(uuid, faction);
            }
            for (FactionChunk chunk : faction.getClaimed()) {
                factionChunks.put(chunk, faction);
            }
        }
    }

    public Collection<Faction> getFactions() {
        return factions;
    }

    public FactionConfiguration getConfiguration() {
        return configuration;
    }

    public Faction getFaction(OfflinePlayer player) {
        return players.get(player.getUniqueId());
    }

    public Faction getFaction(FactionChunk chunk) {
        return factionChunks.get(chunk);
    }

    public Faction getFaction(Chunk chunk) {
        return factionChunks.get(new FactionChunk(chunk.getWorld(), chunk.getX(), chunk.getZ()));
    }

    public Faction getFaction(String name) {
        return factions.stream().filter(faction -> faction.getName().equalsIgnoreCase(name)).findFirst().orElseGet(() -> null);
    }

    public Faction getFactionByTownHallID(UUID entityUUID) {
        return factions.stream().filter(faction -> faction.getTownHall().getEntityUUID().equals(entityUUID)).findFirst().orElseGet(() -> null);
    }

    public Faction getFaction(UUID uuid) {
        return factions.stream().filter(fac -> fac.getId().equals(uuid)).findFirst().get();
    }

    public Faction getFactionByUUID(UUID uuid) {
        return factions.stream().filter(fac -> fac.getId().equals(uuid)).findFirst().get();
    }

    public void claimChunk(Faction faction, FactionChunk chunk) {
        factionChunks.put(chunk, faction);
        faction.getClaimed().add(chunk);
        saveFaction(faction);
    }

    public void unClaim(Faction faction, FactionChunk chunk) {
        if (!faction.getWarps().isEmpty()) {
            FactionWarp warp1 = null;
            for (FactionWarp warp : faction.getWarps()) {
                if (warp.getLocation().getChunk().equals(chunk.getBukkitChunk())) {
                    warp1 = warp;
                }
            }
            faction.removeWarp(warp1);
        }
        if(faction.getTownHall() != null) {
            if(faction.getTownHall().getLocation().getChunk().equals(chunk.getBukkitChunk())) {
                if(Bukkit.getEntity(faction.getTownHall().getEntityUUID()) != null) {Bukkit.getEntity(faction.getTownHall().getEntityUUID()).remove();}
                faction.removeTownHall();
            }
        }
        factionChunks.remove(chunk);
        faction.getClaimed().remove(chunk);
    }

    public void unClaimAll(Faction faction) {
        FactionChunk chunk = null;
        FactionWarp warp1 = null;
        while (!faction.getClaimed().isEmpty()) {
            for (FactionChunk chunk1 : faction.getClaimed()) {
                chunk = chunk1;
            }
            if (!faction.getWarps().isEmpty()) {
                for (FactionWarp warp : faction.getWarps()) {
                    if (warp.getLocation().getChunk().equals(chunk.getBukkitChunk())) {
                        warp1 = warp;
                    }
                }
            }
            faction.removeWarp(warp1);
            if(faction.getTownHall() != null) {
                if(faction.getTownHall().getLocation().getChunk().equals(chunk.getBukkitChunk())) {
                    if(Bukkit.getEntity(faction.getTownHall().getEntityUUID()) != null) {Bukkit.getEntity(faction.getTownHall().getEntityUUID()).remove();}
                    faction.removeTownHall();
                }
            }
            faction.getClaimed().remove(chunk);
            factionChunks.remove(chunk);
        }
    }

    public boolean isClaimedChunk(Chunk chunk) {
        return factionChunks.containsKey(new FactionChunk(chunk.getWorld(), chunk.getX(), chunk.getZ()));
    }

    public boolean isInChunk(Location location, Chunk chunk) {
        return (location.getChunk().equals(chunk));
    }

    public void removeAllChunks(Faction faction) {
        for (FactionChunk chunk : faction.getClaimed()) {
            factionChunks.remove(chunk);
        }
    }

    public void removeChunk(Faction faction, FactionChunk chunk) {
        factionChunks.remove(chunk, faction);
    }

    public void removeFaction(Faction faction) {
        configuration.removeFaction(faction);
        if (factions.contains(faction)) {
            factions.remove(faction);
        }
        for (UUID uuid : faction.getPlayers()) {
            players.remove(uuid);
        }
        for (FactionChunk chunk : faction.getClaimed()) {
            factionChunks.remove(chunk);
        }
    }

    public boolean exists(String name) {
        return factions.stream().anyMatch(faction -> faction.getName().equalsIgnoreCase(name));
    }

    public void saveFaction(Faction faction) {
        factions.add(faction);
        configuration.saveFaction(faction);
    }

    public void setPlayer(Faction faction, OfflinePlayer player, Rank rank) {
        faction.addPlayer(player.getUniqueId(), rank);
        players.put(player.getUniqueId(), faction);
    }

    public void removePlayer(Faction faction, OfflinePlayer player) {
        faction.removePlayer(player.getUniqueId());
        players.remove(player.getUniqueId());
    }

    public void removeRelation(Faction faction, UUID id) {
        Relation relation = faction.getRelation(id);
        faction.getRelations().remove(relation);
        saveFaction(faction);
    }


    // Map colors

    public static ChatColor getColor(final Faction faction, final Faction target) {
        if (target == null) {
            return ChatColor.valueOf(FactionsSystem.getSettings().getChatColor("empty"));
        }
        if (target.getEnemyRelation().contains(faction.getName())) {
            return ChatColor.valueOf(FactionsSystem.getSettings().getChatColor("enemy"));
        }
        if (target.getTrucesRelation().contains(faction.getId())) {
            return ChatColor.valueOf(FactionsSystem.getSettings().getChatColor("truce"));
        }
        if (!faction.getId().equals(target.getId())) {
            return target.getAlliesRelation().contains(faction.getId()) ? ChatColor.valueOf(FactionsSystem.getSettings().getChatColor("ally")) : ChatColor.valueOf(FactionsSystem.getSettings().getChatColor("hostile"));
        }
        return ChatColor.GREEN;
    }

}
