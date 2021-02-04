package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.events.FactionPlayerJoinEvent;
import de.miinoo.factions.model.ColorHelper;
import de.miinoo.factions.model.Faction;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Mino
 * 10.04.2020
 */
public class JoinCommand extends PlayerCommand {

    public JoinCommand() {
        super("join", new CommandDescription(OtherMessages.Help_JoinCommand.getMessage(), OtherMessages.Help_JoinCommandSyntax.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {

        if (!args.hasAtLeast(1)) {
            player.sendMessage(ErrorMessage.Join_Syntax.getMessage());
            return true;
        }

        if (!InviteCommand.invited.containsKey(player)) {
            player.sendMessage(ErrorMessage.Player_Has_No_Invites.getMessage());
            return true;
        }

        Faction faction = InviteCommand.invited.get(player);

        if (!faction.getName().equalsIgnoreCase(args.get(0))) {
            player.sendMessage(ErrorMessage.Invite_Faction_Not_Found.getMessage().replace("%faction%", args.get(0)));
            return true;
        }

        if(faction.getPlayers().size() == FactionsSystem.getFactionLevels().getMaxMember(faction.getLevel())) {
            player.sendMessage(ErrorMessage.Join_Error.getMessage());
            return true;
        }

        Bukkit.getPluginManager().callEvent(new FactionPlayerJoinEvent(player, faction));

        for (UUID uuid : faction.getPlayers()) {
            OfflinePlayer all = Bukkit.getOfflinePlayer(uuid);
            if (all.getPlayer() != null && all.isOnline()) {
                all.getPlayer().sendMessage(OtherMessages.Player_Joined_Faction.getMessage().replace("%player%", player.getName()));

                if (FactionsSystem.getSettings().enableTablist()) {
                    FactionsSystem.adapter.sendTabListHeaderFooter(all.getPlayer(), ColorHelper.colorize(FactionsSystem.getSettings().tabHeader(all.getPlayer())),
                            ColorHelper.colorize(FactionsSystem.getSettings().tabFooter(all.getPlayer())));
                }
            }
        }

        if (faction.getPower() <= FactionsSystem.getSettings().getMaxPower()) {
            if (faction.getPlayers().size() == 1) {
                if ((faction.getPowerCap() + FactionsSystem.getSettings().getPowerJoinIncrease()) < FactionsSystem.getSettings().getMaxPower()) {
                    faction.addPowerCap(FactionsSystem.getSettings().getPlayer2Power());
                    if ((faction.getPower() + FactionsSystem.getSettings().getPowerJoinIncrease()) <= FactionsSystem.getSettings().getMaxPower()) {
                        faction.addPower(FactionsSystem.getSettings().getPlayer2Power());
                    }
                } else {
                    faction.setPowerCap(FactionsSystem.getSettings().getMaxPower());
                }
            } else if (faction.getPlayers().size() == 2) {
                if ((faction.getPowerCap() + FactionsSystem.getSettings().getPowerJoinIncrease()) < FactionsSystem.getSettings().getMaxPower()) {
                    faction.addPowerCap(FactionsSystem.getSettings().getPlayer3Power());
                    if ((faction.getPower() + FactionsSystem.getSettings().getPowerJoinIncrease()) <= FactionsSystem.getSettings().getMaxPower()) {
                        faction.addPower(FactionsSystem.getSettings().getPlayer3Power());
                    }
                } else {
                    faction.setPowerCap(FactionsSystem.getSettings().getMaxPower());
                }
            } else {
                if ((faction.getPowerCap() + FactionsSystem.getSettings().getPowerJoinIncrease()) < FactionsSystem.getSettings().getMaxPower()) {
                    faction.addPowerCap(FactionsSystem.getSettings().getPowerJoinIncrease());
                    if ((faction.getPower() + FactionsSystem.getSettings().getPowerJoinIncrease()) <= FactionsSystem.getSettings().getMaxPower()) {
                        faction.addPower(FactionsSystem.getSettings().getPowerJoinIncrease());
                    }
                } else {
                    faction.setPowerCap(FactionsSystem.getSettings().getMaxPower());
                }
            }
        }
        FactionsSystem.getFactions().setPlayer(faction, player, faction.getLastRank());
        FactionsSystem.getFactions().saveFaction(faction);
        InviteCommand.invited.remove(player);

        for (Player all : Bukkit.getOnlinePlayers()) {
            FactionsSystem.adapter.sendScoreboard(all);
        }

        if (FactionsSystem.getSettings().enableTablist()) {
            FactionsSystem.adapter.sendTabListHeaderFooter(player, ColorHelper.colorize(FactionsSystem.getSettings().tabHeader(player)),
                    ColorHelper.colorize(FactionsSystem.getSettings().tabFooter(player)));
        }
        player.sendMessage(SuccessMessage.Successfully_Joined.getMessage().replace("%faction%", faction.getName()));


        return true;
    }
}
