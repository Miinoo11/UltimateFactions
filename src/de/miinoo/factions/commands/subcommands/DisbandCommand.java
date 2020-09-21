package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.model.WarPiece;
import de.miinoo.factions.model.guis.ConfirmationGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Mino
 * 10.04.2020
 */
public class DisbandCommand extends PlayerCommand {

    public DisbandCommand() {
        super("disband", new CommandDescription("Disbands your faction"), RankPermission.DISBAND);
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }
        factions.removeFaction(faction);
        factions.removeAllChunks(faction);
        new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial())
                .setDisplayName(GUITags.Confirm_Description.getMessage()).setLore(GUITags.Disband_Confirm_Lore.getMessage()).getItem(), () -> {
            player.sendMessage(SuccessMessage.Successfully_Disbanded.getMessage().replace("%faction%", faction.getName()));
            for (UUID uuid : faction.getPlayers()) {
                Player player1 = Bukkit.getPlayer(uuid);
                if (player1 != null && player1.isOnline()) {
                    player1.getPlayer().sendMessage(OtherMessages.Player_Faction_Disbanded.getMessage().replace("%faction%", faction.getName()));
                }
            }

            Entity townhall = null;
            for (FactionChunk c : faction.getClaimed()) {
                for (Entity e : c.getBukkitChunk().getEntities()) {
                    if (faction.getTownHall() != null) {
                        if (e.getUniqueId().equals(faction.getTownHall().getEntityUUID())) {
                            townhall = e;
                        }
                    }
                }
            }
            if (faction.townHallExists() && townhall != null) {
                townhall.remove();
            }

            for (UUID uuid : faction.getEnemyRelation()) {
                Faction enemy = factions.getFaction(uuid);
                factions.removeRelation(faction, enemy.getId());
                factions.removeRelation(enemy, faction.getId());
                factions.saveFaction(enemy);
            }

            for (UUID allyID : faction.getAlliesRelation()) {
                Faction ally = factions.getFaction(allyID);
                factions.removeRelation(faction, ally.getId());
                factions.removeRelation(ally, faction.getId());
                factions.saveFaction(ally);
            }

            for (UUID truceID : faction.getTrucesRelation()) {
                Faction truce = factions.getFaction(truceID);
                factions.removeRelation(faction, truce.getId());
                factions.removeRelation(truce, faction.getId());
                factions.saveFaction(truce);
            }

            for (WarPiece warPiece : faction.getWarPieces()) {
                if (warPiece.getUuid().equals(faction.getId())) {
                    continue;
                }
                if (faction.getWarPieces() == null || faction.getWarPieces().isEmpty()) {
                    return;
                }
                Faction enemy = factions.getFaction(warPiece.getUuid());
                if (enemy.getWarPieces(faction) != null) {
                    enemy.removeWarPieces(faction);
                }
                factions.saveFaction(enemy);
            }

            for (Player all : Bukkit.getOnlinePlayers()) {
                FactionsSystem.adapter.sendScoreboard(all);
            }
        }).open();

        return true;
    }
}
