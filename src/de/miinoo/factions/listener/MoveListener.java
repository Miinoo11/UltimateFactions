package de.miinoo.factions.listener;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.commands.subcommands.*;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.events.FactionTerritoryEnterEvent;
import de.miinoo.factions.events.FactionTerritoryLeaveEvent;
import de.miinoo.factions.events.RegionEnterEvent;
import de.miinoo.factions.events.RegionLeaveEvent;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.region.Region;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author Mino
 * 11.04.2020
 */
public class MoveListener implements Listener {

    private HashMap<Player, Boolean> autoclaim = AutoClaimCommand.autoClaimEnabled;
    private HashMap<Player, Chunk> autoclaimStorage = new HashMap<>();
    private Factions factions = FactionsSystem.getFactions();

    private double multiplier = FactionsSystem.getSettings().getClaimPriceMultiplier();
    private double price = FactionsSystem.getSettings().getClaimDefaultPrice() * multiplier;

    private List<UUID> flyMessageStorage = new ArrayList<>();

    private HashMap<Player, String> move = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = player.getLocation().getChunk();
        Faction faction = FactionsSystem.getFactions().getFaction(chunk);

        // Territory Messages
        if (factions.isClaimedChunk(chunk)) {
            if (move.containsKey(player)) {
                String string = move.get(player);
                if (!string.equals(faction.getName())) {
                    move.remove(player);
                    move.put(player, faction.getName());
                    if(FactionsSystem.getSettings().sendTerritoryEnterMessages()) {
                        player.sendMessage(OtherMessages.Entering_Territory.getMessage().replace("%faction%", faction.getName()));
                    }
                    Bukkit.getPluginManager().callEvent(new FactionTerritoryEnterEvent(player, chunk));
                }
            } else {
                move.put(player, faction.getName());
                if(FactionsSystem.getSettings().sendTerritoryEnterMessages()) {
                    player.sendMessage(OtherMessages.Entering_Territory.getMessage().replace("%faction%", faction.getName()));
                }
                Bukkit.getPluginManager().callEvent(new FactionTerritoryEnterEvent(player, chunk));
            }
        } else if (FactionsSystem.getRegionUtil().isInRegion(player)) {
            Region region = FactionsSystem.getRegionUtil().getRegion(player.getLocation());
            if (move.containsKey(player)) {
                String string = move.get(player);
                if (!string.equals(region.getName())) {
                    move.remove(player);
                    move.put(player, region.getName());
                    if(FactionsSystem.getSettings().sendTerritoryEnterMessages()) {
                        player.sendMessage(OtherMessages.Entering_Region.getMessage().replace("%region%", region.getName()));
                    }
                    Bukkit.getPluginManager().callEvent(new RegionEnterEvent(player, FactionsSystem.getRegionUtil().getRegion(player.getLocation())));
                }
            } else {
                move.put(player, region.getName());
                if(FactionsSystem.getSettings().sendTerritoryEnterMessages()) {
                    player.sendMessage(OtherMessages.Entering_Region.getMessage().replace("%region%", region.getName()));
                }
                Bukkit.getPluginManager().callEvent(new RegionEnterEvent(player, FactionsSystem.getRegionUtil().getRegion(player.getLocation())));
            }
        } else {
            if (move.containsKey(player)) {
                String string = move.get(player);
                if (!string.equals("wilderness")) {
                    for (Region region : FactionsSystem.getRegions().getRegions()) {
                        if (move.get(player).equals(region.getName())) {
                            Bukkit.getPluginManager().callEvent(new RegionLeaveEvent(player));
                        }
                    }
                    move.remove(player);
                    move.put(player, "wilderness");
                    if(FactionsSystem.getSettings().sendTerritoryEnterMessages()) {
                        player.sendMessage(OtherMessages.Entering_Wilderness.getMessage());
                    }
                    Bukkit.getPluginManager().callEvent(new FactionTerritoryLeaveEvent(player, chunk));
                }
            } else {
                move.put(player, "wilderness");
                if(FactionsSystem.getSettings().sendTerritoryEnterMessages()) {
                    player.sendMessage(OtherMessages.Entering_Wilderness.getMessage());
                }
            }

            if (FlyCommand.flyList.contains(player.getUniqueId())) {
                if (!flyMessageStorage.contains(player.getUniqueId())) {
                    flyMessageStorage.add(player.getUniqueId());
                    player.sendMessage(OtherMessages.Fly_Left_Chunk.getMessage());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.setAllowFlight(false);
                            FlyCommand.flyList.remove(player.getUniqueId());
                            player.sendMessage(OtherMessages.Disabled_Fly.getMessage());
                            flyMessageStorage.remove(player.getUniqueId());
                        }
                    }.runTaskLaterAsynchronously(FactionsSystem.getPlugin(), 20 * 3);
                }
            }
        }

        // Teleport delays
        runAsync(() -> {
            if (WarpCommand.teleportDelay.contains(player.getUniqueId())) {
                if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
                    return;
                }
                WarpCommand.teleportDelay.remove(player.getUniqueId());
            }
            if (HomeCommand.delay.containsKey(player)) {
                if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
                    return;
                }
                HomeCommand.delay.remove(player);
            }
            if (WildCommand.teleportDelay.containsKey(player)) {
                if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
                    return;
                }
                WildCommand.teleportDelay.remove(player);
            }
            // AutoClaim
            if (autoclaim.containsKey(player)) {
                if (!autoclaimStorage.containsKey(player)) {
                    autoclaimStorage.put(player, chunk);
                }

                // Check if Chunk changed
                if (autoclaimStorage.get(player) != chunk) {
                    autoclaimStorage.remove(player);
                    autoclaimStorage.put(player, chunk);
                    Faction chunkFaction = factions.getFaction(chunk);
                    if (chunkFaction == null) {
                        FactionsSystem.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(FactionsSystem.getPlugin(), () -> {
                            player.performCommand("f claim");
                        });
                    } else {
                        player.sendMessage(ErrorMessage.AutoClaim_Error.getMessage());
                    }
                } else {
                    player.sendMessage("still in same chunk");
                }
            }
        });
    }

    private void runAsync(final Runnable runnable) {
        BukkitRunnable r = new BukkitRunnable() {
            public void run() {
                runnable.run();
            }
        };
        r.runTaskAsynchronously(FactionsSystem.getPlugin());
    }

}
