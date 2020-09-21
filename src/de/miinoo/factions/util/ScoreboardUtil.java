package de.miinoo.factions.util;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
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

    //public static void sendLegacyScoreboard(Player player) {
    //    if(!FactionsSystem.getSettings().enableScoreboard()) {
    //        return;
    //    }
    //    scoreboard = scoreboard != null ? player.getScoreboard() : Bukkit.getScoreboardManager().getNewScoreboard();
    //    Objective objective = scoreboard.getObjective("aaa") != null ? scoreboard.getObjective("aaa") : scoreboard.registerNewObjective("aaa", "bbb");
    //    objective.setDisplayName(OtherMessages.Scoreboard_Header.getMessage());
    //    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
//
    //    Score l0 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line0.getMessage()));
    //    Score l1 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line1.getMessage()));
    //    Score l3 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line3.getMessage()));
    //    Score l4 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line4.getMessage()));
    //    Score l6 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line6.getMessage()));
    //    Score l7 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line7.getMessage()));
//
    //    Team online = scoreboard.getTeam("online") != null ? scoreboard.getTeam("online") : scoreboard.registerNewTeam("online");
    //    online.setPrefix(scoreboardLine(player, OtherMessages.Scoreboard_Line2.getMessage()));
    //    online.setSuffix("§k§e");
    //    online.addEntry(ChatColor.GREEN.toString());
//
    //    Team factionTeam = scoreboard.getTeam("faction") != null ? scoreboard.getTeam("faction") : scoreboard.registerNewTeam("faction");
    //    factionTeam.setPrefix(scoreboardLine(player, OtherMessages.Scoreboard_Line5.getMessage()));
    //    factionTeam.setSuffix("§k§a");
    //    factionTeam.addEntry(ChatColor.BLACK.toString());
//
    //    Team factionPower = scoreboard.getTeam("power") != null ? scoreboard.getTeam("power") : scoreboard.registerNewTeam("power");
    //    factionPower.setPrefix(scoreboardLine(player, OtherMessages.Scoreboard_Line8.getMessage()));
    //    factionPower.setSuffix("§k§l");
    //    factionPower.addEntry(ChatColor.AQUA.toString());
//
    //    l0.setScore(20);
    //    l1.setScore(19);
    //    objective.getScore(ChatColor.GREEN.toString()).setScore(18);
    //    l3.setScore(17);
    //    l4.setScore(16);
    //    objective.getScore(ChatColor.BLACK.toString()).setScore(15);
    //    l6.setScore(14);
    //    l7.setScore(13);
    //    objective.getScore(ChatColor.AQUA.toString()).setScore(12);
//
    //    player.setScoreboard(scoreboard);
    //}

    /*
    public static void sendScoreboard(Player player) {
        if(!FactionsSystem.getSettings().enableScoreboard()) {
            return;
        }

        scoreboard = scoreboard != null ? player.getScoreboard() : Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.getObjective("aaa") != null ? scoreboard.getObjective("aaa") : scoreboard.registerNewObjective("aaa", "bbb", OtherMessages.Scoreboard_Header.getMessage());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score l0 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line0.getMessage()));
        Score l1 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line1.getMessage()));
        Score l3 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line3.getMessage()));
        Score l4 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line4.getMessage()));
        Score l6 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line6.getMessage()));
        Score l7 = objective.getScore(scoreboardLine(player, OtherMessages.Scoreboard_Line7.getMessage()));

        Team online = scoreboard.getTeam("online") != null ? scoreboard.getTeam("online") : scoreboard.registerNewTeam("online");
        online.setPrefix(scoreboardLine(player, OtherMessages.Scoreboard_Line2.getMessage()));
        online.setSuffix("§k§e");
        online.addEntry(ChatColor.GREEN.toString());

        Team factionTeam = scoreboard.getTeam("faction") != null ? scoreboard.getTeam("faction") : scoreboard.registerNewTeam("faction");
        factionTeam.setPrefix(scoreboardLine(player, OtherMessages.Scoreboard_Line5.getMessage()));
        factionTeam.setSuffix("§k§a");
        factionTeam.addEntry(ChatColor.BLACK.toString());

        Team factionPower = scoreboard.getTeam("power") != null ? scoreboard.getTeam("power") : scoreboard.registerNewTeam("power");
        factionPower.setPrefix(scoreboardLine(player, OtherMessages.Scoreboard_Line8.getMessage()));
        factionPower.setSuffix("§k§l");
        factionPower.addEntry(ChatColor.AQUA.toString());

        l0.setScore(20);
        l1.setScore(19);
        objective.getScore(ChatColor.GREEN.toString()).setScore(18);
        l3.setScore(17);
        l4.setScore(16);
        objective.getScore(ChatColor.BLACK.toString()).setScore(15);
        l6.setScore(14);
        l7.setScore(13);
        objective.getScore(ChatColor.AQUA.toString()).setScore(12);

        player.setScoreboard(scoreboard);
    } */

    // public static void updateScoreboard(Player player) {
    //     if(!FactionsSystem.getSettings().enableScoreboard()) {
    //         return;
    //     }
    //     Scoreboard scoreboard = player.getScoreboard();
//
    //     if(scoreboard.getTeam("online") != null) {
    //         scoreboard.getTeam("online").setPrefix(scoreboardLine(player, OtherMessages.Scoreboard_Line2.getMessage()));
    //     }
    //     if(scoreboard.getTeam("faction") != null) {
    //         scoreboard.getTeam("faction").setPrefix(scoreboardLine(player, OtherMessages.Scoreboard_Line5.getMessage()));
    //     }
    //     if(scoreboard.getTeam("power") != null) {
    //         scoreboard.getTeam("power").setPrefix(scoreboardLine(player, OtherMessages.Scoreboard_Line8.getMessage()));
    //     }
    // }

    private static String scoreboardLine(Player player, String message) {
        Faction faction = factions.getFaction(player);
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
        return message;
    }

}
