package de.miinoo.factions.listener;

import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.util.RegionUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class RegionListener implements Listener {

    @EventHandler
    public void onRegion(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(player.getInventory().getItemInHand().equals(RegionUtil.wand)) {
                if(player.hasPermission("ultimatefactions.wand")) {
                    if(block != null && block.getType() != XMaterial.AIR.parseMaterial()) {
                        RegionUtil.pos1.put(player, block.getLocation());
                        player.sendMessage(SuccessMessage.Successfully_Set_Pos1.getMessage()
                                .replace("%coords%", "§c" + block.getX() + "§8, §c" + block.getY() + "§8, §c" + block.getZ()));
                    }
                }
            }
        } else if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(ServerVersion.getServerVersion() == ServerVersion.VERSION_1_8_R3) {
                if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if(player.getInventory().getItemInHand().equals(RegionUtil.wand)) {
                        if(player.hasPermission("ultimatefactions.wand")) {
                            if(block != null && block.getType() != XMaterial.AIR.parseMaterial()) {
                                RegionUtil.pos2.put(player, block.getLocation());
                                player.sendMessage(SuccessMessage.Successfully_Set_Pos2.getMessage()
                                        .replace("%coords%", "§c" + block.getX() + "§8, §c" + block.getY() + "§8, §c" + block.getZ()));
                            }
                        }
                    }
                }
            } else {
                if(event.getHand().equals(EquipmentSlot.HAND)) {
                    if(player.getInventory().getItemInHand().equals(RegionUtil.wand)) {
                        if(player.hasPermission("ultimatefactions.wand")) {
                            if(block != null && block.getType() != XMaterial.AIR.parseMaterial()) {
                                RegionUtil.pos2.put(player, block.getLocation());
                                player.sendMessage(SuccessMessage.Successfully_Set_Pos2.getMessage()
                                        .replace("%coords%", "§c" + block.getX() + "§8, §c" + block.getY() + "§8, §c" + block.getZ()));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInHand().equals(RegionUtil.wand)) {
            event.setCancelled(true);
        }
    }

}
