package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.guis.RanksGUI;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 10.04.2020
 */
public class RanksCommand extends PlayerCommand {

    public RanksCommand() {
        super("ranks", new CommandDescription(OtherMessages.Help_RanksCommand.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        if(FactionsSystem.getFactions().getFaction(player) == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        Faction faction = FactionsSystem.getFactions().getFaction(player);
        RanksGUI gui = new RanksGUI(player, faction);
        gui.open();

        return true;
    }
}
