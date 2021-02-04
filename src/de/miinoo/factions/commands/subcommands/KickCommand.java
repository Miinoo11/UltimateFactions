package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.ColorHelper;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mino
 * 12.04.2020
 */
public class KickCommand extends PlayerCommand {

    private Factions factions = FactionsSystem.getFactions();

    public KickCommand() {
        super("kick", new CommandDescription(OtherMessages.Help_KickCommand.getMessage(), OtherMessages.Help_KickCommandSyntax.getMessage()), RankPermission.KICK);
    }

    private static double minPower = FactionsSystem.getSettings().getMinPower();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        if (!args.hasAtLeast(1)) {
            player.sendMessage(ErrorMessage.Kick_Syntax.getMessage());
            return true;
        }

        Faction faction = factions.getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        OfflinePlayer target = Bukkit.getPlayer(args.get(0));
        if (target == null) {
            player.sendMessage(ErrorMessage.Target_Error.getMessage());
            return true;
        }

        if (!faction.getPlayers().contains(target.getUniqueId())) {
            player.sendMessage(ErrorMessage.Target_Not_In_Faction.getMessage());
            return true;
        }

        if (!faction.isHigherRank(player, target)) {
            player.sendMessage(ErrorMessage.Kick_Error_Higher_Rank.getMessage());
            return true;
        }

        if (player.getUniqueId().equals(target.getUniqueId())) {
            player.sendMessage(ErrorMessage.Kick_Self_Error.getMessage());
            return true;
        }

        if (faction.getPlayers().size() == 3) {
            if ((faction.getPowerCap() - FactionsSystem.getSettings().getPlayer3Power()) >= FactionsSystem.getSettings().getMinPower()) {
                faction.removePowerCap(FactionsSystem.getSettings().getPlayer3Power());
                if ((faction.getPower() - FactionsSystem.getSettings().getPlayer3Power()) >= minPower) {
                    faction.removePower(FactionsSystem.getSettings().getPlayer3Power());
                }
            } else {
                faction.setPowerCap(FactionsSystem.getSettings().getMinPower());
            }
        } else if (faction.getPlayers().size() == 2) {
            if ((faction.getPowerCap() - FactionsSystem.getSettings().getPlayer2Power()) >= FactionsSystem.getSettings().getMinPower()) {
                faction.removePowerCap(FactionsSystem.getSettings().getPlayer2Power());
                if ((faction.getPower() - FactionsSystem.getSettings().getPlayer2Power()) >= minPower) {
                    faction.removePower(FactionsSystem.getSettings().getPlayer2Power());
                }
            } else {
                faction.setPowerCap(FactionsSystem.getSettings().getMinPower());
            }
        } else {
            if ((faction.getPowerCap() - FactionsSystem.getSettings().getPowerLeaveDecrease()) >= FactionsSystem.getSettings().getMinPower()) {
                faction.removePowerCap(FactionsSystem.getSettings().getPowerLeaveDecrease());
                if((faction.getPower() - FactionsSystem.getSettings().getPowerLeaveDecrease()) >= minPower) {
                    faction.removePower(FactionsSystem.getSettings().getPowerLeaveDecrease());
                }
            } else {
                faction.setPowerCap(FactionsSystem.getSettings().getMinPower());
            }
        }

        factions.removePlayer(faction, target);
        factions.saveFaction(faction);

        if (target.isOnline()) {
            player.sendMessage(SuccessMessage.Successfully_Kicked.getMessage().replace("%player%", target.getPlayer().getName()));
            target.getPlayer().sendMessage(OtherMessages.Player_Get_Kicked.getMessage().replace("%faction%", faction.getName()));
        } else {
            player.sendMessage(SuccessMessage.Successfully_Kicked.getMessage().replace("%player%", target.getUniqueId().toString()));
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (FactionsSystem.getSettings().enableTablist()) {
                FactionsSystem.adapter.sendTabListHeaderFooter(all, ColorHelper.colorize(FactionsSystem.getSettings().tabHeader(all)),
                    ColorHelper.colorize(FactionsSystem.getSettings().tabFooter(all)));
            }
            FactionsSystem.adapter.sendScoreboard(all);
        }

        return true;
    }

    @Override
    public List<String> complete(Player player, ArgumentParser args) {
        List<String> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> players.add(p.getName()));
        return players;
    }
}
