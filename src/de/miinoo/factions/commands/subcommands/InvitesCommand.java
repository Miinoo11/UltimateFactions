package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.guis.InvitesGUI;
import org.bukkit.entity.Player;

public class InvitesCommand extends PlayerCommand {

    public InvitesCommand() {
        super("invites", new CommandDescription(OtherMessages.Help_InvitesCommand.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        new InvitesGUI(player).open();

        return true;
    }
}
