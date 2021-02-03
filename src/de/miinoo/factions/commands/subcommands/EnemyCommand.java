package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.model.Relation;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mino
 * 13.04.2020
 */
public class EnemyCommand extends PlayerCommand {

    public EnemyCommand() {
        super("enemy", new CommandDescription(OtherMessages.Help_EnemyCommand.getMessage(), OtherMessages.Help_EnemyCommandSyntax.getMessage()), RankPermission.ENEMY);
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(!args.hasAtLeast(1)) {
            player.sendMessage(ErrorMessage.Enemy_Syntax.getMessage());
            return true;
        }

        Faction enemy = factions.getFaction(args.get(0));

        if(enemy == null) {
            player.sendMessage(ErrorMessage.Faction_Not_Found.getMessage());
            return true;
        }

        if(faction.getEnemyRelation().contains(enemy.getId())) {
            player.sendMessage(ErrorMessage.Enemy_Exists_Error.getMessage());
            return true;
        }

        if (faction.getTrucesRelation().contains(enemy.getId()) || faction.getAlliesRelation().contains(enemy.getId())) {
            player.sendMessage(ErrorMessage.Already_In_Relation.getMessage());
            return true;
        }

        faction.getRelations().add(new Relation(enemy.getId(), "enemy", null));
        factions.saveFaction(faction);
        player.sendMessage(SuccessMessage.Successfully_Added_Enemy.getMessage().replace("%enemy%", enemy.getName()));
        return true;
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
