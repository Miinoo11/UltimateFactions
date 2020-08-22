package de.miinoo.factions.listener;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Piston;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

/**
 * @author Mino
 * 01.06.2020
 */
public class PistonListener implements Listener {

    private Factions factions = FactionsSystem.getFactions();

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPistonExtend(final BlockPistonExtendEvent event) {
        final Block piston = event.getBlock();
        final Block block = event.getBlock().getRelative(event.getDirection(), event.getLength() + 1);
        final Faction pistonFaction = factions.getFaction(piston.getChunk());
        final Faction blockFaction = factions.getFaction(block.getChunk());
        if(pistonFaction == null) {
            if(blockFaction != null) {
                event.setCancelled(true);
            }
        } else {
            if(blockFaction != null) {
                if ((block.isEmpty() || block.isLiquid()) && !canMoveBlock(pistonFaction, block)) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(false);
            }
        }

    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPistonRetract(final BlockPistonRetractEvent event) {
        if (!event.isSticky()) {
            return;
        }
        final Block block = event.getBlock().getRelative(event.getDirection(), -2);
        if (block.isEmpty() || block.isLiquid()) {
            return;
        }
        final Faction pistonFaction = factions.getFaction(event.getBlock().getChunk());
        if (!this.canMoveBlock(pistonFaction, block)) {
            event.setCancelled(true);
        }
    }

    private boolean canMoveBlock(final Faction faction, final Block block) {
        final Faction blockFaction = factions.getFaction(block.getChunk());
        if (blockFaction != null && faction != null) {
            return faction.getId().equals(blockFaction.getId());
        }
        return false;
    }

    //@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    //public void onPistonExtend(final BlockPistonExtendEvent event) {
    //    final Faction pistonFaction = this.claims.getOwner(new Claim(event.getBlock().getChunk()));
    //    final Block targetBlock = event.getBlock().getRelative(event.getDirection(), event.getLength() + 1);
    //    if ((targetBlock.isEmpty() || targetBlock.isLiquid()) && !this.canPistonMoveBlock(pistonFaction, targetBlock.getLocation())) {
    //        event.setCancelled(true);
    //    }
    //}
//
    //@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    //public void onBlockPistonRetract(final BlockPistonRetractEvent event) {
    //    if (!event.isSticky()) {
    //        return;
    //    }
    //    final Block targetBlock = event.getBlock().getRelative(event.getDirection(), -2);
    //    if (targetBlock.isEmpty() || targetBlock.isLiquid()) {
    //        return;
    //    }
    //    final Faction pistonFaction = this.claims.getOwner(new Claim(event.getBlock().getLocation()));
    //    if (!this.canPistonMoveBlock(pistonFaction, targetBlock.getLocation())) {
    //        event.setCancelled(true);
    //    }
    //}
//
    //private boolean canPistonMoveBlock(final Faction pistonFaction, final Location target) {
    //    final Faction otherFaction = this.claims.getOwner(new Claim(target.getChunk()));
    //    return pistonFaction.getId().equals(((Identifier<Object>)otherFaction).getId());
    //}

}
