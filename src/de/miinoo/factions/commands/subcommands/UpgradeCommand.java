package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.guis.UpgradeGUI;
import org.bukkit.entity.Player;

/**
 * @author Miinoo_
 * 06.09.2020
 **/

public class UpgradeCommand extends PlayerCommand {

    private static Factions factions = FactionsSystem.getFactions();

    public UpgradeCommand() {
        super("upgrade", new CommandDescription(OtherMessages.Help_UpgradeCommand.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if(faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        new UpgradeGUI(player, faction).open();

        return true;
    }
}
