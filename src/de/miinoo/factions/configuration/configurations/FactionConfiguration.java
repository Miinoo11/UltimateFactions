package de.miinoo.factions.configuration.configurations;

import de.miinoo.factions.api.configuration.Configuration;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.Faction;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Mino
 * 10.04.2020
 */
public class FactionConfiguration extends Configuration {

    public FactionConfiguration() {
        super(FactionsSystem.getPlugin().getDataFolder() + "/factions.json");
    }

    public void saveFaction(Faction faction) {
        set(faction.getId().toString(), faction);
    }

    public void removeFaction(Faction faction) { set(faction.getId().toString(), null);}

    public Faction getFaction(UUID id) {
        return get(id.toString(), Faction.class);
    }

    public Faction getFaction(String name) {
        return get(name, Faction.class);
    }

    public Collection<Faction> getFactions() {
        return (Collection) getValues().values();
    }

    public Collection getFactionsByUUID() {
        return (Collection) getValues().values();
    }
}
