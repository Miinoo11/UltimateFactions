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
 * 11.04.2020
 */
public class SetHomeCommand extends PlayerCommand {

    public SetHomeCommand() {
        super("sethome", new CommandDescription("Sets the faction home point"), RankPermission.SET_HOME);
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if(faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(faction.getClaimed().size() <= 0) {
            player.sendMessage(ErrorMessage.Faction_SetHome_Error.getMessage());
            return true;
        }

        for(FactionChunk chunk : faction.getClaimed()) {
            if(factions.isInChunk(player.getLocation(), chunk.getBukkitChunk())) {
                faction.setHome(player.getLocation());
                factions.saveFaction(faction);
                player.sendMessage(SuccessMessage.Successfully_Set_Home.getMessage().replace("%faction%", faction.getName()));
                return true;
            }
        }
        player.sendMessage(ErrorMessage.Faction_SetHome_Error.getMessage());
        return true;
    }
}
