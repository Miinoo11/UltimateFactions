package de.miinoo.factions.listener;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.util.AdminUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * @author Mino
 * 06.05.2020
 */
public class TeleportListener implements Listener {

    private Factions factions = FactionsSystem.getFactions();

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if(FactionsSystem.getSettings().isTeleportBlocker()) {
            if (event.getCause() != PlayerTeleportEvent.TeleportCause.PLUGIN && event.getCause() != PlayerTeleportEvent.TeleportCause.COMMAND) {
                return;
            }
            if (event.getTo() == null) {
                return;
            }
            Player player = event.getPlayer();
            Faction chunkFaction = factions.getFaction(event.getTo().getChunk());
            if (chunkFaction != null) {
                Faction faction = factions.getFaction(player);
                if (faction == null) {
                    if (AdminUtil.advancedPermissions.contains(player)) {
                        event.setCancelled(false);
                        return;
                    }
                    player.sendMessage(ErrorMessage.Teleport_Error.getMessage());
                    event.setCancelled(true);
                    return;
                }

                if (chunkFaction.getAlliesRelation().contains(faction.getId()) || chunkFaction.getTrucesRelation().contains(faction.getId())) {
                    event.setCancelled(false);
                    return;
                }

                if (!chunkFaction.getPlayers().contains(player.getUniqueId())) {
                    if (AdminUtil.advancedPermissions.contains(player)) {
                        event.setCancelled(false);
                        return;
                    }
                    player.sendMessage(ErrorMessage.Teleport_Error.getMessage());
                    event.setCancelled(true);
                }
            }
        }
    }

}
