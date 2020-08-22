package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.guis.BankGUI;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 10.05.2020
 */
public class BankCommand extends PlayerCommand {

    public BankCommand() {
        super("bank", new CommandDescription("bank", "[deposit]"));
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

        new BankGUI(player, faction).open();

        return true;
    }

}