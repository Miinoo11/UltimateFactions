package de.miinoo.factions.util;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

/**
 * @author Mino
 * 20.05.2020
 */
public class ScoreboardUtil {

    private static Factions factions = FactionsSystem.getFactions();


    public static void sendScoreboard(Player player) {
        if (FactionsSystem.getSettings().enableScoreboard()) {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective objective = scoreboard.getObjective("sidebar");
            if(objective == null) {
                objective = scoreboard.registerNewObjective("sidebar", "score", OtherMessages.Scoreboard_Header.getMessage());
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line0.getMessage() + "§r")).setScore(8);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line1.getMessage() + "§r§r")).setScore(7);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line2.getMessage() + "§r§f")).setScore(6);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line3.getMessage() + "§r§e")).setScore(5);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line4.getMessage() + "§r§d")).setScore(4);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line5.getMessage() + "§r§c")).setScore(3);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line6.getMessage() + "§r§b")).setScore(2);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line7.getMessage() + "§r§a")).setScore(1);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line8.getMessage() + "§r§a")).setScore(0);

            player.setScoreboard(scoreboard);
        }
    }
    public static void sendLegacyScoreboard(Player player) {
        if (FactionsSystem.getSettings().enableScoreboard()) {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective objective = scoreboard.getObjective("sidebar");
            if(objective == null) {
                objective = scoreboard.registerNewObjective("sidebar", "score");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            objective.setDisplayName(OtherMessages.Scoreboard_Header.getMessage());
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line0.getMessage() + "§r")).setScore(8);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line1.getMessage() + "§r§r")).setScore(7);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line2.getMessage() + "§r§f")).setScore(6);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line3.getMessage() + "§r§e")).setScore(5);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line4.getMessage() + "§r§d")).setScore(4);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line5.getMessage() + "§r§c")).setScore(3);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line6.getMessage() + "§r§b")).setScore(2);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line7.getMessage() + "§r§a")).setScore(1);
            objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line8.getMessage() + "§r§a")).setScore(0);

            player.setScoreboard(scoreboard);
        }
    }

    private static String scoreboardLine(Player player, String message) {
        Faction faction = factions.getFaction(player);
        if(FactionsSystem.isPlaceHolderAPIFound) {
            if (faction != null) {
                message = PlaceholderAPI.setPlaceholders(player, message.replace("%faction%", faction.getName())
                        .replace("%power%", "" + faction.getPower())
                        .replace("%claims%", "" + faction.getClaimed().size())
                        .replace("%maxclaims%", "" + FactionsSystem.getFactionLevels().getMaxClaims(faction.getLevel()))
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size()));
            } else {
                message = PlaceholderAPI.setPlaceholders(player, message.replace("%faction%", "N/A")
                        .replace("%power%", "N/A")
                        .replace("%claims%", "N/A")
                        .replace("%maxclaims%", "N/A")
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size()));
            }
        } else {
            if (faction != null) {
                message = message.replace("%faction%", faction.getName())
                        .replace("%power%", "" + faction.getPower())
                        .replace("%claims%", "" + faction.getClaimed().size())
                        .replace("%maxclaims%", "" + FactionsSystem.getFactionLevels().getMaxClaims(faction.getLevel()))
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size());
            } else {
                message = message.replace("%faction%", "N/A")
                        .replace("%power%", "N/A")
                        .replace("%claims%", "N/A")
                        .replace("%maxclaims%", "N/A")
                        .replace("%online%", "" + Bukkit.getOnlinePlayers().size());
            }
        }

        return message;
    }

}
