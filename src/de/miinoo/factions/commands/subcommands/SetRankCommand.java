package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.Rank;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.model.guis.SetRankGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mino
 * 13.04.2020
 */
public class SetRankCommand extends PlayerCommand {

    public SetRankCommand() {
        super("setrank", new CommandDescription("Sets a rank to a player", "<player> <rank>"), RankPermission.ASSIGN_ROLES);
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if(faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(args.hasExactly(1)) {
            Player target = Bukkit.getPlayer(args.get(0));

            if(!(target != null && target.isOnline())) {
                player.sendMessage(ErrorMessage.Player_Not_Found.getMessage());
                return true;
            }

            if(target.getUniqueId().equals(player.getUniqueId())) {
                player.sendMessage(ErrorMessage.Rank_Set_Self_Error.getMessage());
                return true;
            }

            if(!faction.getPlayers().contains(target.getUniqueId())) {
                player.sendMessage(ErrorMessage.Target_Not_In_Faction.getMessage());
                return true;
            }

            new SetRankGUI(player, faction, target).open();
        }

        if(args.hasExactly(2)) {
            Player target = Bukkit.getPlayer(args.get(0));

            if(!(target != null && target.isOnline())) {
                player.sendMessage(ErrorMessage.Player_Not_Found.getMessage());
                return true;
            }

            if(target.getUniqueId().equals(player.getUniqueId())) {
                player.sendMessage(ErrorMessage.Rank_Set_Self_Error.getMessage());
                return true;
            }

            if(!faction.getPlayers().contains(target.getUniqueId())) {
                player.sendMessage(ErrorMessage.Target_Not_In_Faction.getMessage());
                return true;
            }

            if(!faction.getRank(args.get(1)).isPresent()) {
                player.sendMessage(ErrorMessage.Rank_Not_Found.getMessage());
                return true;
            }

            if(!faction.isHigherRank(player, target)) {
                player.sendMessage(ErrorMessage.Rank_Set_Higher_Error.getMessage());
                return true;
            }

            Rank rank = faction.getRank(args.get(1)).get();
            factions.setPlayer(faction, target, rank);
            factions.saveFaction(faction);

            player.sendMessage(SuccessMessage.Successfully_Set_Rank.getMessage().replace("%player%", target.getName()).replace("%rank%", rank.getName()));
            target.sendMessage(OtherMessages.Player_Received_Rank.getMessage().replace("%rank%", rank.getName()));
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
