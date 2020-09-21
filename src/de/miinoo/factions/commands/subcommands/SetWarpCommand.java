package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 12.05.2020
 */
public class SetWarpCommand extends PlayerCommand {


    public SetWarpCommand() {
        super("setwarp", new CommandDescription("Sets a warp point", "<name> [password]"), RankPermission.MANAGE_WARPS);
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if (!args.hasAtLeast(1)) {
            player.sendMessage(ErrorMessage.Set_Warp_Syntax.getMessage());
            return true;
        }

        if (faction.getFactionWarp(args.get(0)) != null) {
            player.sendMessage(ErrorMessage.Warp_Already_Exists.getMessage());
            return true;
        }

        if (args.get(0).equalsIgnoreCase("list")) {
            player.sendMessage(ErrorMessage.Faction_SetWarp_Name_Error.getMessage());
            return true;
        }

        if (faction.getClaimed().size() == 0) {
            player.sendMessage(ErrorMessage.Faction_SetWarp_Error.getMessage());
            return true;
        }

        if(faction.getWarps().size() == FactionsSystem.getFactionLevels().getMaxWarps(faction.getLevel())) {
            player.sendMessage(ErrorMessage.Faction_SetWarp_Limit_Error.getMessage());
            return true;
        }

        if (FactionsSystem.getSettings().canSetWarpOutSideFactionChunk()) {
            if (args.hasExactly(1)) {
                faction.addWarp(args.get(0), null, player.getLocation());
                player.sendMessage(SuccessMessage.Successfully_Added_Warp.getMessage().replace("%warp%", args.get(0)));
                FactionsSystem.getFactions().saveFaction(faction);
                return true;
            } else if (args.hasExactly(2)) {
                String password = args.get(1);
                faction.addWarp(args.get(0), password, player.getLocation());
                player.sendMessage(SuccessMessage.Successfully_Added_Warp.getMessage().replace("%warp%", args.get(0)));
                FactionsSystem.getFactions().saveFaction(faction);
                return true;
            } else {
                player.sendMessage(ErrorMessage.Set_Warp_Syntax.getMessage());
            }
        } else {
            for (FactionChunk claimed : faction.getClaimed()) {
                if (factions.isInChunk(player.getLocation(), claimed.getBukkitChunk())) {
                    if (args.hasExactly(1)) {
                        faction.addWarp(args.get(0), null, player.getLocation());
                        player.sendMessage(SuccessMessage.Successfully_Added_Warp.getMessage().replace("%warp%", args.get(0)));
                        FactionsSystem.getFactions().saveFaction(faction);
                        return true;
                    } else if (args.hasExactly(2)) {
                        String password = args.get(1);
                        faction.addWarp(args.get(0), password, player.getLocation());
                        player.sendMessage(SuccessMessage.Successfully_Added_Warp.getMessage().replace("%warp%", args.get(0)));
                        FactionsSystem.getFactions().saveFaction(faction);
                        return true;
                    } else {
                        player.sendMessage(ErrorMessage.Set_Warp_Syntax.getMessage());
                        return true;
                    }
                }
            }
            player.sendMessage(ErrorMessage.Faction_SetWarp_Error.getMessage());
            return true;
        }

        //if(!FactionsSystem.getSettings().canSetWarpOutSideFactionChunk()) {
        //    if(faction.getClaimed().size() == 0) {
        //        player.sendMessage(ErrorMessage.Faction_SetWarp_Error.getMessage());
        //        return true;
        //    }
        //    for(FactionChunk claimed : faction.getClaimed()) {
        //        if(!factions.isInChunk(player.getLocation(), claimed.getBukkitChunk())) {
        //            player.sendMessage(ErrorMessage.Faction_SetWarp_Error.getMessage());
        //            return true;
        //        }
        //    }
        //}

        //if(args.hasExactly(1)) {
        //    faction.addWarp(args.get(0), null, player.getLocation());
        //    player.sendMessage(SuccessMessage.Successfully_Added_Warp.getMessage().replace("%warp%", args.get(0)));
        //    FactionsSystem.getFactions().saveFaction(faction);
        //    return true;
        //} else if(args.hasExactly(2)) {
        //    String password = args.get(1);
        //    faction.addWarp(args.get(0), password, player.getLocation());
        //    player.sendMessage(SuccessMessage.Successfully_Added_Warp.getMessage().replace("%warp%", args.get(0)));
        //    FactionsSystem.getFactions().saveFaction(faction);
        //    return true;
        //} else {
        //    player.sendMessage(ErrorMessage.Set_Warp_Syntax.getMessage());
        //}

        return true;
    }
}
