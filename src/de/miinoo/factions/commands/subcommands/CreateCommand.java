package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.ColorHelper;
import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 10.04.2020
 */
public class CreateCommand extends PlayerCommand {

    public CreateCommand() {
        super("create", new CommandDescription("Create your own faction", "<name>"));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {

        if (!args.hasAtLeast(1)) {
            player.sendMessage(ErrorMessage.Create_Syntax.getMessage());
            return true;
        }

        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if (faction != null) {
            player.sendMessage(ErrorMessage.Player_Already_In_Faction.getMessage());
            return true;
        }

        if(FactionsSystem.getFactions().exists(args.get(0))) {
           player.sendMessage(ErrorMessage.Faction_Already_Exists.getMessage());
           return true;
        }

        if(!(args.get(0).matches("[A-Za-z0-9]+"))) {
            player.sendMessage(ErrorMessage.Create_Error_Alphanumeric.getMessage());
            return true;
        }

        if(args.get(0).length() > FactionsSystem.getSettings().getFactionNameMaximalLength()) {
            player.sendMessage(ErrorMessage.Create_Error_Length.getMessage());
            return true;
        }

        faction = new Faction(args.get(0), player.getUniqueId(), "Another Faction");
        faction.addPower(FactionsSystem.getSettings().getPlayer1Power());
        faction.setPowerCap(FactionsSystem.getSettings().getPlayer1Power());
        FactionsSystem.getFactions().setPlayer(faction, player, faction.getHighestRank());
        FactionsSystem.getFactions().saveFaction(faction);

        FactionsSystem.adapter.sendScoreboard(player);

        if(FactionsSystem.getSettings().enableTablist()) {
            FactionsSystem.adapter.sendTabListHeaderFooter(player, ColorHelper.colorize(FactionsSystem.getSettings().tabHeader(player)),
                ColorHelper.colorize(FactionsSystem.getSettings().tabFooter(player)));
        }
        player.sendMessage(SuccessMessage.Successfully_Created.getMessage().replace("%faction%", faction.getName()));

        return true;
    }
}
