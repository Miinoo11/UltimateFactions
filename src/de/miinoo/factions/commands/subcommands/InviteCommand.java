package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Mino
 * 10.04.2020
 */
public class InviteCommand extends PlayerCommand {

    public static HashMap<OfflinePlayer, Faction> invited = new HashMap<>();

    public InviteCommand() {
        super("invite", new CommandDescription("Invites a player to the faction", "<player>"), RankPermission.INVITE);
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        if(!args.hasAtLeast(1)) {
            player.sendMessage(ErrorMessage.Invite_Syntax.getMessage());
            return true;
        }

        if(FactionsSystem.getFactions().getFaction(player) == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        Player target = Bukkit.getPlayer(args.get(0));
        if(!(target != null && target.isOnline())) {
            player.sendMessage(ErrorMessage.Player_Not_Found.getMessage());
            return true;
        }

        if(FactionsSystem.getFactions().getFaction(target) != null) {
            player.sendMessage(ErrorMessage.Player_Invite_Error.getMessage());
            return true;
        }

        if(target.getUniqueId().equals(player.getUniqueId())) {
            player.sendMessage(ErrorMessage.Player_Invite_Self_Error.getMessage());
            return true;
        }

        if(invited.containsKey(target)) {
            player.sendMessage(ErrorMessage.Player_Already_Invited.getMessage());
            return true;
        }

        Faction faction = FactionsSystem.getFactions().getFaction(player);

        invited.put(target, faction);
        player.sendMessage(SuccessMessage.Successfully_Invited.getMessage().replace("%player%", target.getName()));
        target.sendMessage(OtherMessages.Player_Get_Invite.getMessage().replace("%faction%", faction.getName()));
        return true;
    }

    @Override
    public List<String> complete(Player player, ArgumentParser args) {
        List<String> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> players.add(p.getName()));
        return players;
    }
}
