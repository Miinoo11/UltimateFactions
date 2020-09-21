package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.guis.EnergyGUI;
import org.bukkit.entity.Player;

/**
 * @author Miinoo_
 * 08.09.2020
 **/

public class EnergyCommand extends PlayerCommand {

    public EnergyCommand() {
        super("energy", new CommandDescription("Opens the Energy GUI"));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        new EnergyGUI(player, faction).open();
        return true;
    }
}
