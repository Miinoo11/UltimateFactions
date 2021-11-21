package de.miinoo.factions.listener;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.ColorHelper;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.region.Region;
import de.miinoo.factions.util.RegionUtil;
import de.miinoo.factions.util.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

/**
 * @author Mino
 * 11.04.2020
 */
public class DamageListener implements Listener {

    private Factions factions = FactionsSystem.getFactions();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (event.getEntity() instanceof Player) {
                Player target = (Player) event.getEntity();
                Faction damagerFaction = FactionsSystem.getFactions().getFaction(damager);
                Faction targetFaction = FactionsSystem.getFactions().getFaction(target);
                if (damagerFaction != null) {
                    if (targetFaction != null) {
                        if (damagerFaction.getAlliesRelation().contains(targetFaction.getId())) {
                            for (UUID uuid : damagerFaction.getAlliesRelation()) {
                                Faction ally = factions.getFaction(uuid);
                                for (UUID uuid1 : ally.getPlayers()) {
                                    if (uuid1.equals(target.getUniqueId())) {
                                        if (!damagerFaction.hasPermission(damager.getUniqueId(), RankPermission.HIT_ALLIES)) {
                                            event.setCancelled(true);
                                            damager.sendMessage(ErrorMessage.Ally_Hit_Error.getMessage());
                                        }
                                    }
                                }
                            }
                        }

                        if (damagerFaction.getTrucesRelation().contains(targetFaction.getId())) {
                            for (UUID uuid : damagerFaction.getAlliesRelation()) {
                                Faction truce = factions.getFaction(uuid);
                                for (UUID uuid1 : truce.getPlayers()) {
                                    if (uuid1.equals(target.getUniqueId())) {
                                        if (!damagerFaction.hasPermission(damager.getUniqueId(), RankPermission.HIT_TRUCE)) {
                                            event.setCancelled(true);
                                            damager.sendMessage(ErrorMessage.Truce_Hit_Error.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                        if (damagerFaction.getPlayers().contains(target.getUniqueId())) {
                            damager.sendMessage(ErrorMessage.Faction_Hit_Error.getMessage());
                            event.setCancelled(true);
                        }
                    }
                }

                // Flag listener
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    if (FactionsSystem.getRegionUtil().isInRegion(player)) {
                        Region region = FactionsSystem.getRegionUtil().getRegion(player.getLocation());
                        if (region.hasFlag("disable-pvp")) {
                            if (!player.hasPermission("ultimatefactions.regionflag.bypass")) {
                                event.setCancelled(true);
                                damager.sendMessage(OtherMessages.PvP_Disabled_In_Region.getMessage());
                            }
                        }
                    }
                }
            }
        } else if (event.getDamager() instanceof Projectile) {
            if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                Projectile projectile = (Projectile) event.getDamager();
                if (projectile.getShooter() instanceof Player) {
                    if (event.getEntity() instanceof Player) {
                        Player player = (Player) event.getEntity();
                        Faction playerFaction = factions.getFaction(player);
                        Player shooter = (Player) projectile.getShooter();
                        Faction shooterFaction = factions.getFaction(shooter);
                        if (playerFaction != null) {
                            if (shooterFaction != null) {
                                if (shooterFaction.getPlayers().contains(player.getUniqueId())) {
                                    event.setCancelled(true);
                                }
                                if (shooterFaction.getTrucesRelation().contains(playerFaction.getId())) {
                                    if (!shooterFaction.hasPermission(shooter.getUniqueId(), RankPermission.HIT_TRUCE)) {
                                        event.setCancelled(true);
                                    }
                                }
                                if (shooterFaction.getAlliesRelation().contains(playerFaction.getId())) {
                                    if (!shooterFaction.hasPermission(shooter.getUniqueId(), RankPermission.HIT_ALLIES)) {
                                        event.setCancelled(true);
                                    }
                                }
                            }
                        }

                        // Flag listener
                        if (FactionsSystem.getRegionUtil().isInRegion(player)) {
                            Region region = FactionsSystem.getRegionUtil().getRegion(player.getLocation());
                            if (region.hasFlag("disable-pvp")) {
                                if (!player.hasPermission("ultimatefactions.regionflag.bypass")) {
                                    event.setCancelled(true);
                                    shooter.sendMessage(OtherMessages.PvP_Disabled_In_Region.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        Faction faction = factions.getFaction(player);
        if (faction == null) {
            return;
        }

        if (FactionsSystem.getRegionUtil().isInRegion(player)) {
            return;
        }

        if (FactionsSystem.getRegionUtil().isInDisabledWorld(player)) {
            return;
        }

        if (killer != null) {
            Faction killerFac = factions.getFaction(killer);
            if (killerFac != null) {
                if (factions.getFaction(player) != null) {

                    if (killerFac.getPower() <= (killerFac.getPowerCap() - FactionsSystem.getSettings().getPowerKillIncrease())) {
                        killerFac.addPower(FactionsSystem.getSettings().getPowerKillIncrease());
                    }

                    if (FactionsSystem.getSettings().enableTablist()) {
                        FactionsSystem.adapter.sendTabListHeaderFooter(killer, ColorHelper.colorize(FactionsSystem.getSettings().tabHeader(killer)),
                                ColorHelper.colorize(FactionsSystem.getSettings().tabFooter(killer)));
                    }
                }

                if(faction.getWarPieces(killerFac) == null) {
                    killerFac.addWarPiece(faction, 1);
                } else {
                    if (killerFac.getWarPieces(faction).getAmount() < FactionsSystem.getSettings().getMaxWarPiece()) {
                        killerFac.addWarPiece(faction, 1);
                    }
                }

                if(faction.getWarPieces(killerFac) == null) {
                    faction.removeWarPiece(killerFac, 1);
                } else {
                    if (faction.getWarPieces(killerFac).getAmount() > FactionsSystem.getSettings().getMinWarPiece()) {
                        faction.removeWarPiece(killerFac, 1);
                    }
                }

                factions.saveFaction(killerFac);

            }
        }

        if (faction.getPower() >= FactionsSystem.getSettings().getMinPower() + FactionsSystem.getSettings().getPowerKillDecrease()) {
            faction.removePower(FactionsSystem.getSettings().getPowerKillDecrease());
        }

        factions.saveFaction(faction);

        if (FactionsSystem.getSettings().enableTablist()) {
            FactionsSystem.adapter.sendTabListHeaderFooter(player, ColorHelper.colorize(FactionsSystem.getSettings().tabHeader(player)),
                    ColorHelper.colorize(FactionsSystem.getSettings().tabFooter(player)));
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            FactionsSystem.adapter.sendScoreboard(all);
        }
    }

}
