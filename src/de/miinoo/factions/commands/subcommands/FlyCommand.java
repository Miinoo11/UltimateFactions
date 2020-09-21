package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionChunk;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.util.RegionUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Miinoo_
 * 07.09.2020
 **/

public class FlyCommand extends PlayerCommand {

    private Factions factions = FactionsSystem.getFactions();
    public static List<UUID> flyList = new ArrayList<>();

    public FlyCommand() {
        super("fly", new CommandDescription("Enables fly in your territory"), RankPermission.FLY);
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if(faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(!faction.hasFly()) {
            player.sendMessage(ErrorMessage.Upgrade_Needed_Error.getMessage());
            return true;
        }

        if(flyList.contains(player.getUniqueId())) {
            player.setAllowFlight(false);
            player.setFlying(false);
            flyList.remove(player.getUniqueId());
            player.sendMessage(OtherMessages.Disabled_Fly.getMessage());
            return true;
        } else {
            if(isInFactionChunk(player, faction)) {
                player.setAllowFlight(true);
                flyList.add(player.getUniqueId());
                player.sendMessage(OtherMessages.Enabled_Fly.getMessage());
                return true;
            } else {
                player.sendMessage(ErrorMessage.Fly_Chunk_Error.getMessage());
            }
        }

        return true;
    }

    public static boolean isInFactionChunk(Player player, Faction faction) {
        for(FactionChunk chunk : faction.getClaimed()) {
            if(player.getLocation().getChunk().equals(chunk.getBukkitChunk())) {
                return true;
            }
        }
        return false;
    }
}
