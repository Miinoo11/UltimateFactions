package de.miinoo.factions.listener;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XBlock;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
        if (faction == null) {
            return;
        }
        if (event.getEntity().getType() != EntityType.PLAYER) {
            // check if mob died by player
            if (event.getEntity().getKiller() != null) {
                if (factions.getFaction(event.getEntity().getKiller()) != null && factions.getFaction(event.getEntity().getKiller()).equals(faction)) {
                    for (ItemStack stack : event.getDrops()) {
                        if (stack != null) {
                            try {
                                stack.setAmount((int) (stack.getAmount() * FactionsSystem.getFactionLevels().getMobDropMultiplier(faction.getLevel())));
                                if(stack.getType() != Material.AIR) {
                                    event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), stack);
                                }
                            } catch (IllegalArgumentException e) {}
                        }
                    }
                }
            // check if mob died in faction chunk
            } else {
                for (ItemStack stack : event.getDrops()) {
                    if (stack != null) {
                        try {
                            stack.setAmount((int) (stack.getAmount() * FactionsSystem.getFactionLevels().getMobDropMultiplier(faction.getLevel())));
                            if(stack.getType() != Material.AIR) {
                                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), stack);
                            }
                        } catch (IllegalArgumentException e) {}
                    }
                }
            }
        }
    }

    //@EventHandler
    //public void onGrow(BlockGrowEvent event) {
    //    for(Faction faction : FactionsSystem.getFactions().getFactions()) {
    //        for(FactionChunk chunk : faction.getClaimed()) {
    //            if(event.getBlock().getLocation().getChunk().equals(chunk.getBukkitChunk())) {
    //                event.setCancelled(true);
    //            }
    //        }
    //    }
    //}
//
    //@EventHandler
    //public void onInteract(PlayerInteractEvent event) {
    //    Player player = event.getPlayer();
    //    Block block = event.getClickedBlock();
//
    //    player.sendMessage(block.getState().getData().toString());
    //}

}
