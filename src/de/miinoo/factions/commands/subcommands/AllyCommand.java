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
public class AllyCommand extends PlayerCommand {

    public AllyCommand() {
        super("ally", new CommandDescription("Adds a faction to your allies", "<faction>"), RankPermission.ALLY);
    }

    public static HashMap<Faction, Faction> allyRequest = new HashMap<>();
    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if (!args.hasExactly(1)) {
            player.sendMessage(ErrorMessage.Ally_Syntax.getMessage());
            return true;
        }

        Faction allyFaction = factions.getFaction(args.get(0));
        if (allyFaction == null) {
            player.sendMessage(ErrorMessage.Faction_Not_Found.getMessage());
            return true;
        }

        if (faction.getAlliesRelation().contains(allyFaction.getId())) {
            player.sendMessage(ErrorMessage.Ally_Exists_Error.getMessage().replace("%faction%", allyFaction.getName()));
            return true;
        }

        if (allyFaction.getId().equals(faction.getId())) {
            player.sendMessage(ErrorMessage.Ally_Self_Error.getMessage());
            return true;
        }

        if (allyFaction.alreadyRequestedAlly(faction.getId().toString())) {
            player.sendMessage(ErrorMessage.Ally_Already_Sent_Request.getMessage());
            return true;
        }

        if (allyFaction.getAlliesRelation().contains(faction.getId())) {
            player.sendMessage(ErrorMessage.Ally_Exists_Error.getMessage().replace("%faction%", allyFaction.getName()));
            return true;
        }

        if (allyFaction.getAllyRequests().contains(faction.getId().toString())) {
            player.sendMessage(ErrorMessage.Ally_Already_Sent_Request.getMessage());
            return true;
        }

        if (faction.getTrucesRelation().contains(allyFaction.getId())) {
            player.sendMessage(ErrorMessage.Already_In_Relation.getMessage());
            return true;
        }

        if (faction.getAllyRequests().contains(allyFaction.getId().toString())) {
            new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName(GUITags.Confirm_Description.getMessage())
                    .setLore(GUITags.Ally_Request_Lore.getMessage().replace("%faction%", allyFaction.getName())).getItem(),
                    () -> {
                        faction.getRelations().add(new Relation(allyFaction.getId(), "ally", Arrays.asList(RelationPermission.OPEN_DOOR, RelationPermission.USE_LEVER)));
                        allyFaction.getRelations().add(new Relation(faction.getId(), "ally", Arrays.asList(RelationPermission.OPEN_DOOR, RelationPermission.USE_LEVER)));

                        messageToPermitted(faction, allyFaction);
                        messageToPermitted(allyFaction, faction);

                        faction.removeAllyRequest(allyFaction.getId().toString());
                        allyFaction.removeAllyRequest(faction.getId().toString());

                        factions.saveFaction(allyFaction);
                        factions.saveFaction(faction);
                    }).open();
            return true;
        }

        allyFaction.addAllyRequest(faction.getId().toString());
        player.sendMessage(SuccessMessage.Successfully_Ally_Sent.getMessage().replace("%faction%", allyFaction.getName()));
        for (UUID uuid : allyFaction.getPlayers()) {
            if (allyFaction.getRankOfPlayer(uuid).hasPermission(RankPermission.ALLY)) {
                Player allyPlayer = Bukkit.getPlayer(uuid);
                if (allyPlayer != null && allyPlayer.isOnline()) {
                    allyPlayer.sendMessage(OtherMessages.Ally_Request_Get.getMessage().replace("%faction%", faction.getName()));
                }
            }
        }

        return true;
    }

    private void messageToPermitted(Faction faction, Faction target) {
        for (UUID uuid : faction.getPlayers()) {
            if (faction.getRankOfPlayer(uuid).getPermissions().contains(RankPermission.ALLY)) {
                Player player1 = Bukkit.getPlayer(uuid);
                if (player1 != null && player1.isOnline()) {
                    player1.sendMessage(SuccessMessage.Successfully_Ally_Added.getMessage().replace("%faction%", target.getName()));
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
