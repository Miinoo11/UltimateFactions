package de.miinoo.factions.configuration.configurations;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.configuration.Configuration;
import de.miinoo.factions.factionchest.FactionChest;
import de.miinoo.factions.model.Faction;

import java.util.Collection;

public class FactionChestsConfiguration extends Configuration {

    public FactionChestsConfiguration() {
        super(FactionsSystem.getPlugin().getDataFolder() + "/faction_chests.json");
    }

    public void saveChest(FactionChest chest) {
        set(chest.getId().toString(), chest);
    }

    public void deleteChest(FactionChest chest) {
        set(chest.getId().toString(), null);
    }

    public FactionChest getChest(Faction faction) {
        return getChests().stream().filter(chest -> chest.getFaction().equals(faction)).findFirst().orElseGet(null);
    }

    public Collection<FactionChest> getChests() {
        return (Collection) getValues().values();
    }
}
