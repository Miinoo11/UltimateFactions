package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * @author Mino
 * 15.04.2020
 */
public class AutoClaimCommand extends PlayerCommand {


    public AutoClaimCommand() {
        super("autoclaim", new CommandDescription(OtherMessages.Help_AutoClaimCommand.getMessage()), RankPermission.CLAIM);
    }

    public static HashMap<Player, Boolean> autoClaimEnabled  = new HashMap<>();
    private Factions factions = FactionsSystem.getFactions();
    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);
        if(faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(autoClaimEnabled.containsKey(player)) {
            autoClaimEnabled.remove(player);
            player.sendMessage(OtherMessages.AutoClaim_Disabled.getMessage());
        } else {
            autoClaimEnabled.put(player, true);
            player.sendMessage(OtherMessages.AutoClaim_Enabled.getMessage());
        }

        return true;
    }
}
