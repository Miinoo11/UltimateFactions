package de.miinoo.factions.configuration.configurations;

import de.miinoo.factions.api.configuration.Configuration;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.Region;

import java.util.Collection;
import java.util.UUID;

public class RegionConfiguration extends Configuration {

    public RegionConfiguration() {
        super(FactionsSystem.getPlugin().getDataFolder() + "/regions.json");
    }

    public void saveRegion(Region region) {
        set(region.getUuid().toString(), region);
    }

    public void removeRegion(Region region) {
        set(region.getUuid().toString(), null);
    }

    public Region getRegion(UUID uuid) {
        return get(uuid.toString(), Region.class);
    }

    public Collection<Region> getRegions() {
        return (Collection) getValues().values();
    }

}
