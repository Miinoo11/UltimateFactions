package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.quest.Quest;
import de.miinoo.factions.quest.rewards.MoneyReward;
import de.miinoo.factions.quest.types.KillQuest;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
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
        Faction enemy = FactionsSystem.getFactions().getFaction(args.get(0));

        Faction faction = FactionsSystem.getFactions().getFaction(player);

        faction.setWarPieces(enemy, 0);
        enemy.setWarPieces(faction, 0);

        player.sendMessage("done");
        return true;
    }
}
