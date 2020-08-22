package de.miinoo.factions.listener;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.commands.subcommands.AutoClaimCommand;
import de.miinoo.factions.commands.subcommands.HomeCommand;
import de.miinoo.factions.commands.subcommands.WarpCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

/**
 * @author Mino
 * 11.04.2020
 */
public class MoveListener implements Listener {

    private HashMap<Player, String> move = new HashMap<>();
    private HashMap<Player, Boolean> autoclaim = AutoClaimCommand.autoClaimEnabled;
    private HashMap<Player, Chunk> autoclaimStorage = new HashMap<>();
    private Factions factions = FactionsSystem.getFactions();

    private double multiplier = FactionsSystem.getSettings().getClaimPriceMultiplier();
    private double price = FactionsSystem.getSettings().getClaimDefaultPrice() * multiplier;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = player.getLocation().getChunk();

        // Territory Messages
        if (factions.isClaimedChunk(chunk)) {
            Faction faction = factions.getFaction(chunk);
            if (move.containsKey(player)) {
                String string = move.get(player);
                if (!string.equals(faction.getName())) {
                    move.remove(player);
                    move.put(player, faction.getName());
                    player.sendMessage(OtherMessages.Entering_Territory.getMessage().replace("%faction%", faction.getName()));
                }
            } else {
                move.put(player, faction.getName());
                player.sendMessage(OtherMessages.Entering_Territory.getMessage().replace("%faction%", faction.getName()));
            }
        } else {
            if (move.containsKey(player)) {
                String string = move.get(player);
                if (!string.equals("wilderness")) {
                    move.remove(player);
                    move.put(player, "wilderness");
                    player.sendMessage(OtherMessages.Entering_Wilderness.getMessage());
                }
            } else {
                move.put(player, "wilderness");
                player.sendMessage(OtherMessages.Entering_Wilderness.getMessage());
            }
        }
        runAsync(() -> {
            // Teleport delays
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
                        Faction faction = factions.getFaction(player);
                        if (faction != null) {
                            if (!((FactionsSystem.getEconomy().getBalance(player) - price) >= 0)) {
                                player.sendMessage(ErrorMessage.Not_Enough_Money.getMessage());
                                return;
                            }
                            FactionsSystem.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(FactionsSystem.getPlugin(), () -> {
                                player.performCommand("f claim");
                            });
                        }
                    } else {
                        player.sendMessage(ErrorMessage.AutoClaim_Error.getMessage());
                    }
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
