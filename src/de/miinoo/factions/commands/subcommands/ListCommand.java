package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.guis.FactionsGUI;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 14.05.2020
 */
public class ListCommand extends PlayerCommand {

    public ListCommand() {
        super("list", new CommandDescription(OtherMessages.Help_ListCommand.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        new FactionsGUI(player).open();
        return true;
    }
}
