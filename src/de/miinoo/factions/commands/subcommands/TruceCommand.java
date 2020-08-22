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
import de.miinoo.factions.model.RelationPermission;
import de.miinoo.factions.model.guis.ConfirmationGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author Mino
 * 14.04.2020
 */
public class TruceCommand extends PlayerCommand {

    public TruceCommand() {
        super("truce", new CommandDescription("Adds a faction to your truces", "<faction>"), RankPermission.TRUCE);
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if (!args.hasExactly(1)) {
            player.sendMessage(ErrorMessage.Truce_Syntax.getMessage());
            return true;
        }

        Faction truceFaction = factions.getFaction(args.get(0));
        if (truceFaction == null) {
            player.sendMessage(ErrorMessage.Faction_Not_Found.getMessage());
            return true;
        }

        if (faction.getTrucesRelation().contains(truceFaction.getId())) {
            player.sendMessage(ErrorMessage.Truce_Exists_Error.getMessage().replace("%faction%", truceFaction.getName()));
            return true;
        }

        if (truceFaction.getId().equals(faction.getId())) {
            player.sendMessage(ErrorMessage.Truce_Self_Error.getMessage());
            return true;
        }

        if(truceFaction.getTruceRequests().contains(faction.getId().toString())) {
            player.sendMessage(ErrorMessage.Truce_Already_Sent_Request.getMessage());
            return true;
        }

        if(faction.getAlliesRelation().contains(truceFaction.getId())) {
            player.sendMessage(ErrorMessage.Already_In_Relation.getMessage());
            return true;
        }

        if(faction.getTruceRequests().contains(truceFaction.getId().toString())) {
            new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName(GUITags.Confirm_Description.getMessage())
                    .setLore(GUITags.Truce_Request_Lore.getMessage().replace("%faction%", truceFaction.getName())).getItem(),
                    () -> {
                        faction.getRelations().add(new Relation(truceFaction.getId(), "truce", Arrays.asList(RelationPermission.OPEN_DOOR, RelationPermission.USE_LEVER)));
                        truceFaction.getRelations().add(new Relation(faction.getId(), "truce", Arrays.asList(RelationPermission.OPEN_DOOR, RelationPermission.USE_LEVER)));

                        messageToPermitted(faction, truceFaction);
                        messageToPermitted(truceFaction, faction);

                        faction.removeTruceRequest(truceFaction.getId().toString());
                        truceFaction.removeTruceRequest(faction.getId().toString());

                        factions.saveFaction(truceFaction);
                        factions.saveFaction(faction);
                    }).open();
            return true;
        }

        truceFaction.addTruceRequest(faction.getId().toString());
        player.sendMessage(SuccessMessage.Successfully_Truce_Sent.getMessage().replace("%faction%", truceFaction.getName()));
        for (UUID uuid : truceFaction.getPlayers()) {
            if (truceFaction.getRankOfPlayer(uuid).hasPermission(RankPermission.TRUCE)) {
                Player trucePlayer = Bukkit.getPlayer(uuid);
                if (trucePlayer != null && trucePlayer.isOnline()) {
                    trucePlayer.sendMessage(OtherMessages.Truce_Request_Get.getMessage().replace("%faction%", faction.getName()));
                }
            }
        }

        return true;
    }

    private void messageToPermitted(Faction faction, Faction target) {
        for (UUID uuid : faction.getPlayers()) {
            if (faction.getRankOfPlayer(uuid).getPermissions().contains(RankPermission.TRUCE)) {
                Player player1 = Bukkit.getPlayer(uuid);
                if (player1 != null && player1.isOnline()) {
                    player1.sendMessage(SuccessMessage.Successfully_Truced_Added.getMessage().replace("%faction%", target.getName()));
                }
            }
        }
    }

    @Override
    public List<String> complete(Player player, ArgumentParser args) {
        List<String> factionsList = new ArrayList<>();
        for (Faction faction : factions.getFactions()) {
            if(factions.getFaction(player) != null) {
                if(!faction.getId().equals(factions.getFaction(player).getId())) {
                    factionsList.add(faction.getName());
                }
            }
        }
        return factionsList;
    }

}
