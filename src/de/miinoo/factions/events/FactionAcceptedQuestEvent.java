package de.miinoo.factions.events;

import de.miinoo.factions.model.Faction;
import de.miinoo.factions.quest.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionAcceptedQuestEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Player player;
    Faction faction;
    Quest quest;

    /**
     * @param faction that accepted the quest
     * @param quest   that was accepted
     * @param player that accepted the quest
     */
    public FactionAcceptedQuestEvent(Player player, Faction faction, Quest quest) {
        this.player = player;
        this.faction = faction;
        this.quest = quest;
    }

    public Faction getFaction() {
        return faction;
    }

    public Player getPlayer() {
        return player;
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