package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.adapter.FactionsAdapter;
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
import de.miinoo.factions.util.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 14.04.2020
 */
public class SetNameCommand extends PlayerCommand {

    public SetNameCommand() {
        super("setname", new CommandDescription("Changes the name of your faction", "<name>"), RankPermission.CHANGE_INFO);
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
            player.sendMessage(ErrorMessage.Set_Name_Syntax.getMessage());
            return true;
        }

        if(factions.exists(args.get(0))) {
            player.sendMessage(ErrorMessage.Faction_Already_Exists.getMessage());
            return true;
        }

        if (FactionsSystem.getSettings().onlyCharacter()) {
            if (!(args.get(0).matches("[A-Za-z0-9]+"))) {
                player.sendMessage(ErrorMessage.Create_Error_Alphanumeric.getMessage());
                return true;
            }
        }

        if(args.get(0).length() > FactionsSystem.getSettings().getFactionNameMaximalLength()) {
            player.sendMessage(ErrorMessage.Create_Error_Length.getMessage());
            return true;
        }

        if(faction.getTownHall() != null) {
            if(Bukkit.getEntity(faction.getTownHall().getEntityUUID()) != null) {
                Entity townhall = Bukkit.getEntity(faction.getTownHall().getEntityUUID());
                townhall.setCustomName(OtherMessages.TownHall_DisplayName.getMessage().replace("%faction%", args.get(0)));
            }
        }

        Bukkit.getOnlinePlayers().forEach(p -> FactionsSystem.adapter.sendScoreboard(p));

        faction.setName(args.get(0));
        factions.saveFaction(faction);
        player.sendMessage(SuccessMessage.Successfully_Set_Name.getMessage().replace("%faction%", args.get(0)));

        return true;
    }
}
