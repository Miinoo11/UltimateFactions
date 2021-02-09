package de.miinoo.factions.events;

import de.miinoo.factions.model.Faction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class TopFactionUpdateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Collection<Faction> topFactions;

    public TopFactionUpdateEvent(Collection<Faction> topFactions) {
        this.topFactions = topFactions;
    }

    public Collection<Faction> getTopFactions() {
        return topFactions;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
