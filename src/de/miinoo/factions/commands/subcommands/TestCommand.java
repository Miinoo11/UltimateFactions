package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.PlayerCommand;
import org.bukkit.entity.Player;

/**
 * @author Miinoo_
 * 22.09.2020
 **/

public class TestCommand extends PlayerCommand {

    public TestCommand() {
        super("test");
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {

        //player.sendMessage("Success!");
        return true;
    }
}
