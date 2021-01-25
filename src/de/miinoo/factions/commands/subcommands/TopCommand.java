package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.guis.TopGUI;
import de.miinoo.factions.util.TopUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mino
 * 17.05.2020
 */
public class TopCommand extends PlayerCommand {

    public TopCommand() {
        super("top", new CommandDescription(OtherMessages.Help_TopCommand.getMessage(), OtherMessages.Help_TopCommandSyntax.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        if(args.hasExactly(0)) {
            new TopGUI(player).open();
            return true;
        } else {
            if(!player.hasPermission("ultimatefactions.updatetop")) {
                player.sendMessage(ErrorMessage.Player_Not_Permitted.getMessage());
                return true;
            }
            if(args.get(0).equalsIgnoreCase("update")) {
                TopUtil.calculate();
                player.sendMessage(SuccessMessage.Successfully_Updated_Top.getMessage());
            }
        }
        return true;
    }

    @Override
    public List<String> complete(Player player, ArgumentParser args) {
        List<String> list = new ArrayList<>();
        list.add("update");
        return list;
    }
}
