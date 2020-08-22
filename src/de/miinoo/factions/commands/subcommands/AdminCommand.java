package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.model.guis.AdminGUI;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 03.05.2020
 */
public class AdminCommand extends PlayerCommand {

    public AdminCommand() {
        super("admin");
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        if(!player.hasPermission("ultimatefactions.admin")) {
            player.sendMessage(ErrorMessage.Player_Not_Permitted.getMessage());
            return true;
        }

        new AdminGUI(player).open();

        return true;
    }
}
