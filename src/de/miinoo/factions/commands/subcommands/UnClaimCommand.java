package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.events.FactionUnclaimChunkEvent;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mino
 * 12.04.2020
 */
public class UnClaimCommand extends PlayerCommand {


    public UnClaimCommand() {
        super("unclaim", new CommandDescription(OtherMessages.Help_UnClaimCommand.getMessage(), OtherMessages.Help_UnClaimCommandSyntax.getMessage()), RankPermission.UNCLAIM);
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {

        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        FactionChunk chunk = new FactionChunk(player.getWorld(), player.getLocation().getBlockX() >> 4, player.getLocation().getBlockZ() >> 4);


        if (!faction.getClaimed().contains(chunk) && FactionsSystem.getFactions().isClaimedChunk(player.getLocation().getChunk())) {
            player.sendMessage(ErrorMessage.Not_Your_Chunk.getMessage());
            return true;
        }

        if (args.hasExactly(1)) {
            if (args.get(0).equalsIgnoreCase("all")) {
                Bukkit.getPluginManager().callEvent(new FactionUnclaimChunkEvent(player, faction));
                factions.unClaimAll(faction);
                factions.saveFaction(faction);
                player.sendMessage(SuccessMessage.Successfully_UnClaimedAll.getMessage());
                return true;
            }
        } else {
            if (!faction.getClaimed().contains(chunk) && FactionsSystem.getFactions().getFaction(chunk) == null) {
                player.sendMessage(ErrorMessage.Chunk_Not_Claimed.getMessage());
                return true;
            }
            Bukkit.getPluginManager().callEvent(new FactionUnclaimChunkEvent(player, faction));
            factions.unClaim(faction, chunk);
            factions.saveFaction(faction);
            player.sendMessage(SuccessMessage.Successfully_UnClaimed.getMessage());
        }

        return true;
    }

    @Override
    public List<String> complete(Player player, ArgumentParser args) {
        List<String> list = new ArrayList<>();
        list.add("all");
        return list;
    }
}
