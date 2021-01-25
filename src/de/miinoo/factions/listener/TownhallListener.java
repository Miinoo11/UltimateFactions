package de.miinoo.factions.listener;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.Townhall;
import de.miinoo.factions.model.guis.TownHallGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Mino
 * 12.05.2020
 */
public class TownhallListener implements Listener {

    private Factions factions = FactionsSystem.getFactions();

    @EventHandler
    public void onDamage1(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType().equals(EntityType.VILLAGER)) {
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                Entity entity = event.getEntity();
                Faction faction = null;
                for (Faction faction1 : factions.getFactions()) {
                    if (faction1.getTownHall() != null) {
                        if (faction1.getTownHall().getEntityUUID().equals(entity.getUniqueId())) {
                            faction = faction1;
                        }
                    }
                }
                if (faction == null) {
                    return;
                }
                if (faction.getPlayers().contains(player.getUniqueId())) {
                    if (faction.getTownHall().getEntityUUID().equals(entity.getUniqueId())) {
                        event.setCancelled(true);
                        player.sendMessage(ErrorMessage.TownHall_Hit_Error.getMessage());
                        return;
                    }
                }
                Faction target = factions.getFaction(player);
                if(target != null) {
                    if(target.getAlliesRelation().contains(factions.getFactionByTownHallID(entity.getUniqueId()).getId())) {
                        return;
                    }
                    if(target.getTrucesRelation().contains(factions.getFactionByTownHallID(entity.getUniqueId()).getId())) {
                        return;
                    }
                }

                event.setCancelled(true);

                faction.getTownHall().removeHealth(event.getDamage());
                entity.setCustomName(OtherMessages.TownHall_DisplayName.getMessage().replace("%faction%", faction.getName())
                        + " §8(§e" + Math.round(faction.getTownHall().getHealth()) + "§7 / §e" + FactionsSystem.getSettings().getDefaultHealth() + "§8)");
                if (faction.getTownHall().getHealth() <= 0) {
                    if (!faction.getBankItems().isEmpty()) {
                        for (Material material : faction.getBankItems().keySet()) {
                            ItemStack is = new ItemStack(material, faction.getBankItemAmount(material));
                            faction.removeBankItem(material, faction.getBankItemAmount(material));
                            entity.getWorld().dropItem(entity.getLocation(), is);
                            faction.setBank(0.0);
                        }
                    }
                    faction.getTownHall().stopMoveTask();

                    if (ServerVersion.isLegacy()) {
                        for (Entity en : player.getWorld().getEntities()) {
                            if (en.getUniqueId().equals(faction.getTownHall().getEntityUUID())) {
                                en.remove();
                            }
                        }
                    } else {
                        Bukkit.getEntity(faction.getTownHall().getEntityUUID()).remove();
                    }
                    faction.removeTownHall();
                    factions.saveFaction(faction);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Villager) {
            if (!(event.getEntity().getLastDamageCause() instanceof Player)) {
                for (Faction faction : factions.getFactions()) {
                    if (faction.getTownHall() != null) {
                        if (faction.getTownHall().getEntityUUID().equals(event.getEntity().getUniqueId())) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.VILLAGER) {
            return;
        }
        Player player = event.getPlayer();
        Faction faction = factions.getFaction(player);
        if (faction == null) {
            return;
        }
        if (faction.getTownHall() != null) {
            if (FactionsSystem.getSettings().townhallIsEnabled()) {
                if (faction.getTownHall().getEntityUUID().equals(event.getRightClicked().getUniqueId())) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            try {
                                player.closeInventory();
                                new TownHallGUI(player, faction).open();
                            } catch (IllegalStateException exception) {}
                        }
                    }.runTask(FactionsSystem.getPlugin());
                }
            } else {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.closeInventory();
                    }
                }.runTask(FactionsSystem.getPlugin());
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Faction faction = factions.getFaction(player);
        if (faction == null) {
            return;
        }
        if (event.getClickedBlock() != null) {
            ItemStack is = player.getInventory().getItemInHand();
            ItemMeta meta = is.getItemMeta();
            if (is.getType() == XMaterial.VILLAGER_SPAWN_EGG.parseMaterial() && meta != null &&
                    meta.getDisplayName().equalsIgnoreCase(OtherMessages.TownHall_DisplayName.getMessage().replace("%faction%", faction.getName()))) {
                if (ServerVersion.is1_8_X()) {
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        Location location = event.getClickedBlock().getLocation().add(0, 1, 0);
                        Entity entity = player.getWorld().spawnEntity(location, EntityType.VILLAGER);
                        entity.setCustomNameVisible(true);
                        entity.setCustomName(OtherMessages.TownHall_DisplayName.getMessage().replace("%faction%", faction.getName())
                                + " §8(§e" + faction.getTownHall().getHealth() + "§7 / §e" + FactionsSystem.getSettings().getDefaultHealth() + "§8)");
                        entity.setSilent(true);
                        if (entity instanceof LivingEntity) {
                            ((LivingEntity) entity).setAI(false);
                        }
                        if (faction.getTownHall() != null) {
                            faction.getTownHall().setLocation(location);
                            faction.getTownHall().setEntityUUID(entity.getUniqueId());
                            factions.saveFaction(faction);
                            faction.getTownHall().startMoveTask();
                        } else {
                            faction.setTownHall(new Townhall(entity.getUniqueId(), FactionsSystem.getSettings().getDefaultHealth(), player.getLocation()));
                            faction.getTownHall().startMoveTask();
                            factions.saveFaction(faction);
                        }
                        player.getInventory().remove(is);
                        event.setCancelled(true);
                    }
                } else {
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND) {
                        Location location = event.getClickedBlock().getLocation().add(0, 1, 0);
                        Entity entity = player.getWorld().spawnEntity(location, EntityType.VILLAGER);
                        entity.setCustomNameVisible(true);
                        entity.setCustomName(OtherMessages.TownHall_DisplayName.getMessage().replace("%faction%", faction.getName())
                                + " §8(§e" + faction.getTownHall().getHealth() + "§7 / §e" + FactionsSystem.getSettings().getDefaultHealth() + "§8)");
                        entity.setSilent(true);
                        if (entity instanceof LivingEntity) {
                            ((LivingEntity) entity).setAI(false);
                        }
                        if (faction.getTownHall() != null) {
                            faction.getTownHall().setLocation(location);
                            faction.getTownHall().setEntityUUID(entity.getUniqueId());
                            factions.saveFaction(faction);
                            faction.getTownHall().startMoveTask();
                        } else {
                            faction.setTownHall(new Townhall(entity.getUniqueId(), FactionsSystem.getSettings().getDefaultHealth(), player.getLocation()));
                            faction.getTownHall().startMoveTask();
                            factions.saveFaction(faction);
                        }
                        player.getInventory().remove(is);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
