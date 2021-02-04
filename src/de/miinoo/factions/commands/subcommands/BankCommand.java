package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.guis.BankGUI;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 10.05.2020
 */
public class BankCommand extends PlayerCommand {

    public BankCommand() {
        super("bank", new CommandDescription(OtherMessages.Help_BankCommand.getMessage(), OtherMessages.Help_BankCommandSyntax.getMessage()));
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        if(FactionsSystem.getSettings().townhallIsEnabled()) {
            player.sendMessage(ErrorMessage.TownHall_Enabled_Error.getMessage());
            return true;
        }

        Faction faction = factions.getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        new BankGUI(player, faction, null).open();

        return true;
    }

}
