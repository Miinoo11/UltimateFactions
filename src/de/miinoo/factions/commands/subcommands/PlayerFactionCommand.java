package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.model.Faction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerFactionCommand extends PlayerCommand {


    public PlayerFactionCommand() {
        super("player", new CommandDescription(OtherMessages.Help_PlayerFactionCommand.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {

        if(args.length() != 1) {
            player.sendMessage(OtherMessages.Help_PlayerFactionCommandSyntax.getMessage());
            return true;
        }

        Player target = Bukkit.getPlayer(args.get(0));
        if(target == null) {
            player.sendMessage(ErrorMessage.Player_Not_Found.getMessage());
            return true;
        }

        Faction faction = FactionsSystem.getFactions().getFaction(target);
        if(faction == null) {
            player.sendMessage(OtherMessages.Player_No_Faction.getMessage());
        } else {
            player.sendMessage(OtherMessages.Player_Faction.getMessage().replace("%faction%", faction.getName()));
        }

        return true;
    }
}
