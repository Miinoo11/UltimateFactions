package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 14.04.2020
 */
public class ReloadCommand extends PlayerCommand {

    public ReloadCommand() {
        super("reload", new CommandDescription(OtherMessages.Help_ReloadCommand.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        if(!player.hasPermission("ultimatefactions.reload")) {
            player.sendMessage(ErrorMessage.Player_Not_Permitted.getMessage());
            return true;
        }

        for(Faction faction : FactionsSystem.getFactions().getFactions()) {
            FactionsSystem.getFactions().saveFaction(faction);
        }

        Bukkit.getPluginManager().disablePlugin(FactionsSystem.getPlugin());
        Bukkit.getPluginManager().enablePlugin(FactionsSystem.getPlugin());

        player.sendMessage(SuccessMessage.Successfully_Reloaded.getMessage());

        return true;
    }
}
