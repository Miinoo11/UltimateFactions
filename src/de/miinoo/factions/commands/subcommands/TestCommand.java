package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.model.Faction;
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

        Faction faction = FactionsSystem.getFactions().getFaction(player);
        faction.addWarPiece(faction, 1);
        player.sendMessage("JA!");
        return true;
    }
}
