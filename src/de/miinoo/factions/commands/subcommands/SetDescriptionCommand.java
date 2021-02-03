package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.events.FactionChangeDescriptionEvent;
import de.miinoo.factions.events.FactionChangeNameEvent;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 14.04.2020
 */
public class SetDescriptionCommand extends PlayerCommand {

    public SetDescriptionCommand() {
        super("setdescription", new CommandDescription(OtherMessages.Help_SetDescriptionCommand.getMessage(), OtherMessages.Help_SetDescriptionCommandSyntax.getMessage()), RankPermission.CHANGE_INFO);
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if(faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(!args.hasAtLeast(1)) {
            player.sendMessage(ErrorMessage.Set_Description_Syntax.getMessage());
            return true;
        }

        String msg = "";
        for(int i = 0; i < args.length(); i++) {
            msg += args.get(i) + " ";
        }

        Bukkit.getPluginManager().callEvent(new FactionChangeDescriptionEvent(player, faction, faction.getDescription(), msg));
        faction.setDescription(msg);
        factions.saveFaction(faction);
        player.sendMessage(SuccessMessage.Successfully_Set_Description.getMessage().replace("%faction%", faction.getName()).replace("%description%", msg));

        return true;
    }
}
