package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.PlayerCommand;
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
        if(args.length() == 1) {
            player.sendMessage(FactionsSystem.getQuests().getQuests().toString());
        } else if(args.length() == 0) {

            //KillQuest quest = new KillQuest("Schnitzel", "Kill 50 Pigs" , Material.PORKCHOP,50, EntityType.PIG,
            //        new MoneyReward("Money", "&e500 Coins", 500.13));
            //FactionsSystem.getQuests().saveQuest(quest);
            //player.sendMessage("Erstellt: " + quest.getName());
        } else if(args.length() == 2) {
            Quest q = null;
            for(Quest quest : FactionsSystem.getQuests().getQuests()) {
                q = quest;
                q.addToCompleted(FactionsSystem.getFactions().getFaction(player));
            }
            FactionsSystem.getQuests().saveQuest(q);
            player.sendMessage("success!");
        }
        return true;
    }
}
