package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.guis.FactionInfoGUI;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 14.04.2020
 */
public class InfoCommand extends PlayerCommand {

    public InfoCommand() {
        super("info", new CommandDescription("Shows you an info about a faction", "[name]"));
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction ownFaction = factions.getFaction(player);
        if(ownFaction == null && args.hasExactly(0)) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(args.hasAtLeast( 1)) {
            Faction faction = factions.getFaction(args.get(0));
            if(faction == null) {
                player.sendMessage(ErrorMessage.Faction_Not_Found.getMessage());
                return true;
            }
            new FactionInfoGUI(player, faction).open();
            return true;
        }

        new FactionInfoGUI(player, ownFaction).open();
        return true;
    }
}
