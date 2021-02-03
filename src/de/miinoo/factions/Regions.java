package de.miinoo.factions;

import de.miinoo.factions.configuration.configurations.RegionConfiguration;
import de.miinoo.factions.region.Region;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Regions {

    private final FactionsSystem system;
    private final RegionConfiguration configuration = new RegionConfiguration();
    private final Set<Region> regions;

    public Regions(FactionsSystem system) {
        this.system = system;
        regions = new HashSet<>();
        regions.addAll(configuration.getRegions());
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void saveRegion(Region region) {
        regions.add(region);
        configuration.saveRegion(region);
    }

    public void removeRegion(Region region) {
        configuration.removeRegion(region);
        if (regions.contains(region)) {
            regions.remove(region);
        }
    }

    public Region getRegion(UUID uuid) {
        return regions.stream().filter(region -> region.getUuid().equals(uuid)).findFirst().get();
    }

    public Region getRegion(String name) {
        return regions.stream().filter(region -> region.getName().equalsIgnoreCase(name)).findFirst().orElseGet(() -> null);
    }

    public boolean exists(String name) {
        return regions.stream().anyMatch(region -> region.getName().equalsIgnoreCase(name));
    }
}
