package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.region.guis.RegionsGUI;
import org.bukkit.entity.Player;

public class RegionsCommand extends PlayerCommand {

    public RegionsCommand() {
        super("regions", new CommandDescription(OtherMessages.Help_RegionsCommand.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        if(!player.hasPermission("ultimatefactions.admin")) {
            player.sendMessage(ErrorMessage.Player_Not_Permitted.getMessage());
            return true;
        }

        new RegionsGUI(player).open();
        return true;
    }
}
