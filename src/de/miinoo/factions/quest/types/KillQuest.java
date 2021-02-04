package de.miinoo.factions.quest.types;

import de.miinoo.factions.quest.Quest;
import de.miinoo.factions.quest.QuestDescription;
import de.miinoo.factions.quest.QuestReward;
import de.miinoo.factions.quest.QuestType;

import java.util.List;

public class KillQuest extends Quest {

    public KillQuest(String name, int amount, Object questObject, List<QuestReward> rewards) {
        super(name, QuestType.KILL, new QuestDescription("%type% %amount% %object%"), amount, questObject, rewards);
    }

}
