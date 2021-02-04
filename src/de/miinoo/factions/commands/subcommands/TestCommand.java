package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.quest.Quest;
import de.miinoo.factions.quest.QuestReward;
import de.miinoo.factions.quest.rewardtypes.MoneyReward;
import de.miinoo.factions.quest.types.KillQuest;
import org.bukkit.entity.Player;

import java.util.Arrays;

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
        Quest quest = new KillQuest("Schnitzel", 50, XMaterial.PORKCHOP.parseMaterial(), Arrays.asList(new MoneyReward(500)));
        FactionsSystem.getQuests().saveQuest(quest);
        player.sendMessage("Saved: " + quest.getName());
        return true;
    }
}
