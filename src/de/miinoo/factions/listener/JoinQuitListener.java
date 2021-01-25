package de.miinoo.factions.listener;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.commands.subcommands.ChatCommand;
import de.miinoo.factions.model.ColorHelper;
import de.miinoo.factions.util.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Mino
 * 14.04.2020
 */
public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!ChatCommand.chatMode.containsKey(player)) {
            ChatCommand.chatMode.put(player, "public");
        }

        FactionsSystem.adapter.sendScoreboard(player);

        if(FactionsSystem.getSettings().enableTablist()) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if(FactionsSystem.getSettings().enableTablist()) {FactionsSystem.adapter.sendTabListHeaderFooter(all, ColorHelper.colorize(FactionsSystem.getSettings().tabHeader(all)),
                        ColorHelper.colorize(FactionsSystem.getSettings().tabFooter(all)));
                }
                FactionsSystem.adapter.sendScoreboard(all);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(FactionsSystem.getSettings().enableTablist()) {FactionsSystem.adapter.sendTabListHeaderFooter(all, ColorHelper.colorize(FactionsSystem.getSettings().tabHeader(all)),
                            ColorHelper.colorize(FactionsSystem.getSettings().tabFooter(all)));
                    }
                }
            }.runTaskLaterAsynchronously(FactionsSystem.getPlugin(), 5);
            FactionsSystem.adapter.sendScoreboard(all);
        }
    }

}
