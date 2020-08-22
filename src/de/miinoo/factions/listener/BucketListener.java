package de.miinoo.factions.listener;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.commands.subcommands.SiegeCMD;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.model.Relation;
import de.miinoo.factions.model.RelationPermission;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import java.util.UUID;

/**
 * @author Mino
 * 12.05.2020
 */
public class BucketListener implements Listener {

    private Factions factions = FactionsSystem.getFactions();

    @EventHandler
    public void onFill(PlayerBucketFillEvent event) {
        Chunk chunk = event.getBlockClicked().getChunk();

        if(factions.getFaction(chunk) != null && !factions.getFaction(chunk).getPlayers().contains(event.getPlayer().getUniqueId())) {
            if(!FactionsSystem.getSettings().canFillBucket()) {
                checkFill(factions.getFaction(chunk), event.getPlayer(), ErrorMessage.Fill_Bucket_Error.getMessage(), event);
            }
        }

    }

    @EventHandler
    public void onEmpty(PlayerBucketEmptyEvent event) {
        Chunk chunk = event.getBlockClicked().getChunk();

        if(factions.getFaction(chunk) != null && !factions.getFaction(chunk).getPlayers().contains(event.getPlayer().getUniqueId())) {
            if(!FactionsSystem.getSettings().canEmptyBucket()) {
                checkEmpty(factions.getFaction(chunk), event.getPlayer(), ErrorMessage.Empty_Bucket_Error.getMessage(), event);
            }
        }
    }

    private void checkEmpty(Faction faction, Player player, String message, PlayerBucketEmptyEvent event) {
        Faction siege = factions.getFaction(player);
        if (SiegeCMD.siege.containsKey(siege)) {
            event.setCancelled(false);
            return;
        }

        if (faction.getPlayers().contains(player.getUniqueId())) {
            if (faction.hasPermission(player.getUniqueId(), RankPermission.EMPTY_BUCKET)) {
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
                        if (!faction.relationIsPermitted(relation, RelationPermission.EMPTY_BUCKET)) {
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

    private void checkFill(Faction faction, Player player, String message, PlayerBucketFillEvent event) {
        Faction siege = factions.getFaction(player);
        if (SiegeCMD.siege.containsKey(siege)) {
            event.setCancelled(false);
            return;
        }

        if (faction.getPlayers().contains(player.getUniqueId())) {
            if (faction.hasPermission(player.getUniqueId(), RankPermission.FILL_BUCKET)) {
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
                        if (!faction.relationIsPermitted(relation, RelationPermission.FILL_BUCKET)) {
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
