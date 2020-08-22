package de.miinoo.factions.listener;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.commands.subcommands.SiegeCMD;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.model.*;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * @author Mino
 * 11.04.2020
 */
public class BuildListener implements Listener {

    private Factions factions = FactionsSystem.getFactions();


    /*
        FactionsSystem.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(FactionsSystem.getPlugin(), () -> {

        });
     */

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = event.getBlockAgainst().getChunk();

        if (FactionsSystem.getFactions().isClaimedChunk(chunk)) {
            Faction faction = FactionsSystem.getFactions().getFaction(new FactionChunk(chunk.getWorld(), chunk.getX(), chunk.getZ()));
            check(faction, player, ErrorMessage.Place_Error.getMessage(), event);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();

        if (FactionsSystem.getFactions().isClaimedChunk(chunk)) {
            Faction faction = FactionsSystem.getFactions().getFaction(new FactionChunk(chunk.getWorld(), chunk.getX(), chunk.getZ()));
            check(faction, player, ErrorMessage.Break_Error.getMessage(), event);
        }
    }


    private void check(Faction faction, Player player, String message, BlockPlaceEvent event) {
        Faction siege = factions.getFaction(player);
        if (SiegeCMD.siege.containsKey(siege)) {
            event.setCancelled(false);
            return;
        }

        if (faction.getPlayers().contains(player.getUniqueId())) {
            if (faction.hasPermission(player.getUniqueId(), RankPermission.BUILD)) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
                player.sendMessage(message);
            }
        } else {
            if ((faction.getPower() > 0)) {
                for (UUID uuid : faction.getAlliesRelation()) {
                    Relation relation = faction.getRelation(uuid);
                    Faction ally = factions.getFaction(uuid);
                    if (ally.getPlayers().contains(player.getUniqueId())) {
                        if (!faction.relationIsPermitted(relation, RelationPermission.BUILD)) {
                            event.setCancelled(true);
                            player.sendMessage(message);
                        } else {
                            event.setCancelled(false);
                            return;
                        }
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(message);
                    }
                }
                if (faction.getAlliesRelation().isEmpty()) {
                    event.setCancelled(true);
                    player.sendMessage(message);
                }
            } else {
                event.setCancelled(false);
            }
        }
    }
    private void check(Faction faction, Player player, String message, BlockBreakEvent event) {
        Faction siege = factions.getFaction(player);
        if (SiegeCMD.siege.containsKey(siege)) {
            event.setCancelled(false);
            return;
        }

        if (faction.getPlayers().contains(player.getUniqueId())) {
            if (faction.hasPermission(player.getUniqueId(), RankPermission.BREAK)) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
                player.sendMessage(message);
            }
        } else {
            if ((faction.getPower() > 0)) {
                for (UUID uuid : faction.getAlliesRelation()) {
                    Relation relation = faction.getRelation(uuid);
                    Faction ally = factions.getFaction(uuid);
                    if (ally.getPlayers().contains(player.getUniqueId())) {
                        if (!faction.relationIsPermitted(relation, RelationPermission.BREAK)) {
                            event.setCancelled(true);
                            player.sendMessage(message);
                        } else {
                            event.setCancelled(false);
                            return;
                        }
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(message);
                    }
                }
                if (faction.getAlliesRelation().isEmpty()) {
                    event.setCancelled(true);
                    player.sendMessage(message);
                }
            } else {
                event.setCancelled(false);
            }
        }
    }

}
