package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.model.guis.AlliesGUI;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 22.04.2020
 */
public class AlliesCommand extends PlayerCommand {

    public AlliesCommand() {
        super("allies", new CommandDescription(OtherMessages.Help_AlliesCommand.getMessage()), RankPermission.ALLY);
    }

    private Factions factions = FactionsSystem.getFactions();
    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if(faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        new AlliesGUI(player, faction).open();

        return true;
    }
}
