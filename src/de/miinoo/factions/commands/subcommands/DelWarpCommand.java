package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.events.FactionDeleteWarpEvent;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 13.04.2020
 */
public class DelWarpCommand extends PlayerCommand {

    public DelWarpCommand() {
        super("delwarp", new CommandDescription(OtherMessages.Help_DelWarpCommand.getMessage(), OtherMessages.Help_DelWarpCommandSyntax.getMessage()), RankPermission.MANAGE_WARPS);
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Factions factions = FactionsSystem.getFactions();
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(!args.hasAtLeast(1)) {
            player.sendMessage(ErrorMessage.Del_Warp_Syntax.getMessage());
            return true;
        }

        if(faction.getFactionWarp(args.get(0)) == null) {
            player.sendMessage(ErrorMessage.Warp_Not_Found.getMessage());
            return true;
        }

        Bukkit.getPluginManager().callEvent(new FactionDeleteWarpEvent(player, faction, faction.getFactionWarp(args.get(0))));
        faction.removeWarp(args.get(0));
        factions.saveFaction(faction);
        player.sendMessage(SuccessMessage.Successfully_Deleted_Warp.getMessage().replace("%warp%", args.get(0)));

        return true;
    }
}
