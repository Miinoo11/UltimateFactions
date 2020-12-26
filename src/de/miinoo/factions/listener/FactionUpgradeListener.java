package de.miinoo.factions.listener;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.Faction;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Miinoo_
 * 08.09.2020
 **/

public class FactionUpgradeListener implements Listener {

    // Mob Drop Multiplier

    private Factions factions = FactionsSystem.getFactions();

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        Faction faction = factions.getFaction(event.getEntity().getLocation().getChunk());
        if(faction == null) { return; }
        if(event.getEntity().getType() != EntityType.PLAYER) {
            if(event.getEntity().getKiller() != null) {
                if(factions.getFaction(event.getEntity().getKiller()) != null && factions.getFaction(event.getEntity().getKiller()).equals(faction)) {
                    for(ItemStack stack : event.getDrops()) {
                        stack.setAmount((int) (stack.getAmount() * FactionsSystem.getFactionLevels().getMobDropMultiplier(faction.getLevel())));
                        if(stack.getType() != Material.AIR) {
                            event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), stack);
                        }
                    }
                }
            }
        }
    }

}