package de.miinoo.factions.events;

import de.miinoo.factions.model.Faction;
import de.miinoo.factions.quest.Quest;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionCompletedQuestEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Faction faction;
    Quest quest;

    /**
     * @param faction that has completed the quest
     * @param quest   that was completed
     */
    public FactionCompletedQuestEvent(Faction faction, Quest quest) {
        this.faction = faction;
        this.quest = quest;
    }

    public Faction getFaction() {
        return faction;
    }


    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Quest getQuest() {
        return quest;
    }
}