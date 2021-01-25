package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.util.ItemUtil;
import de.miinoo.factions.util.RegionUtil;
import org.bukkit.entity.Player;

public class WandCommand extends PlayerCommand {

    public WandCommand() {
        super("wand", new CommandDescription(OtherMessages.Help_WandCommand.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        if(!player.hasPermission("ultimatefactions.admin")) {
            player.sendMessage(ErrorMessage.Player_Not_Permitted.getMessage());
            return true;
        }
        if(!ItemUtil.hasAvailableSlot(player, 1)) {
            player.sendMessage(ErrorMessage.Inventory_Full_Error.getMessage());
            return true;
        }

        player.getInventory().addItem(FactionsSystem.getRegionUtil().wand);
        player.sendMessage(SuccessMessage.Successfully_Received_RegionWand.getMessage());

        return true;
    }
}
