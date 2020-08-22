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
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.model.Relation;
import de.miinoo.factions.model.guis.ConfirmationGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Mino
 * 26.04.2020
 */
public class NeutralCommand extends PlayerCommand {

    public NeutralCommand() {
        super("neutral", new CommandDescription("Removes a faction from you relations", "<faction>"), RankPermission.NEUTRAL);
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if(faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(!args.hasExactly(1)) {
            player.sendMessage(ErrorMessage.Neutral_Syntax.getMessage());
            return true;
        }

        Faction relation = factions.getFaction(args.get(0));
        if(relation == null) {
            player.sendMessage(ErrorMessage.Faction_Not_Found.getMessage());
            return true;
        }

        Relation rel = faction.getRelation(relation.getId());
        if(!faction.getRelations().contains(rel)) {
            player.sendMessage(ErrorMessage.Faction_Not_In_Relation.getMessage());
            return true;
        }


        new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial())
                .setDisplayName(GUITags.Confirm_Description.getMessage())
                .setLore(GUITags.Neutral_Lore.getMessage())
                .getItem(), () -> {
            for(UUID uuid : faction.getAlliesRelation()) {
                if(uuid.equals(relation.getId())) {
                    factions.removeRelation(faction, relation.getId());
                    factions.removeRelation(relation, faction.getId());
                    factions.saveFaction(faction);
                    factions.saveFaction(relation);

                    for (UUID players : relation.getPlayers()) {
                        Player all = Bukkit.getPlayer(players);
                        if (all != null && all.isOnline()) {
                            all.sendMessage(OtherMessages.Faction_Unallied.getMessage().replace("%faction%", faction.getName()));
                        }
                    }
                    player.sendMessage(SuccessMessage.Successfully_Unallied.getMessage().replace("%faction%", relation.getName()));
                }
            }
            for(UUID uuid : faction.getTrucesRelation()) {
                if(uuid.equals(relation.getId())) {
                    factions.removeRelation(faction, relation.getId());
                    factions.removeRelation(relation, faction.getId());
                    factions.saveFaction(faction);
                    factions.saveFaction(relation);

                    for (UUID players : relation.getPlayers()) {
                        Player all = Bukkit.getPlayer(players);
                        if (all != null && all.isOnline()) {
                            all.sendMessage(OtherMessages.Faction_Untruced.getMessage().replace("%faction%", faction.getName()));
                        }
                    }
                    player.sendMessage(SuccessMessage.Successfully_Untruced.getMessage().replace("%faction%", relation.getName()));
                }
            }
            for(UUID uuid : faction.getEnemyRelation()) {
                if(uuid.equals(relation.getId())) {
                    factions.removeRelation(faction, relation.getId());
                    factions.removeRelation(relation, faction.getId());
                    factions.saveFaction(faction);
                    factions.saveFaction(relation);

                    for (UUID players : relation.getPlayers()) {
                        Player all = Bukkit.getPlayer(players);
                        if (all != null && all.isOnline()) {
                            all.sendMessage(OtherMessages.Faction_Unenemied.getMessage().replace("%faction%", faction.getName()));
                        }
                    }
                    player.sendMessage(SuccessMessage.Successfully_Unenemied.getMessage().replace("%faction%", relation.getName()));
                }
            }
        }).open();

        return true;
    }
}
