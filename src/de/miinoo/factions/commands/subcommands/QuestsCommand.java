package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.quest.guis.QuestsGUI;
import org.bukkit.entity.Player;

public class QuestsCommand extends PlayerCommand {

    public QuestsCommand() {
        super("quests", new CommandDescription(OtherMessages.Help_QuestsCommand.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if(faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }
        new QuestsGUI(player, faction).open();
        return true;
    }
}
