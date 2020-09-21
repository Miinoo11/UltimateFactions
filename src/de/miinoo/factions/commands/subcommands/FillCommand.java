package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Miinoo_
 * 07.09.2020
 **/

public class FillCommand extends PlayerCommand {

    public static List<UUID> fillList = new ArrayList<>();
    private Factions factions = FactionsSystem.getFactions();

    public FillCommand() {
        super("fill", new CommandDescription("Fills storage container with tnt"), RankPermission.FILL);
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if(faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(!faction.hasFill()) {
            player.sendMessage(ErrorMessage.Upgrade_Needed_Error.getMessage());
            return true;
        }

        if(fillList.contains(player.getUniqueId())) {
            fillList.remove(player.getUniqueId());
            player.sendMessage(OtherMessages.Disabled_Fill.getMessage());
        } else {
            fillList.add(player.getUniqueId());
            player.sendMessage(OtherMessages.Enabled_Fill.getMessage());
        }
        return true;
    }
}
