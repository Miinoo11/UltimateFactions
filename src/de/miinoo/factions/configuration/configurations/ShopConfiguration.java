package de.miinoo.factions.configuration.configurations;


import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.configuration.Configuration;
import de.miinoo.factions.shop.ShopCategory;

import java.util.Collection;
import java.util.UUID;

public class ShopConfiguration extends Configuration {

    public ShopConfiguration() {
        super(FactionsSystem.getPlugin().getDataFolder() + "/shop.json");
    }

    public void saveCategory(ShopCategory category) {
        set(category.getId().toString(), category);
    }

    public void removeCategory(ShopCategory category) {
        set(category.getId().toString(), null);
    }

    public ShopCategory getCategory(UUID id) {
        return get(id.toString(), ShopCategory.class);
    }

    public ShopCategory getCategory(String name) {
        return get(name, ShopCategory.class);
    }

    public Collection<ShopCategory> getCategories() {
        return (Collection) getValues().values();
    }

}
