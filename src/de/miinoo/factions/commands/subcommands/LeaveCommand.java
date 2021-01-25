package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.events.FactionPlayerLeaveEvent;
import de.miinoo.factions.model.ColorHelper;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.guis.ConfirmationGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Mino
 * 10.04.2020
 */
public class LeaveCommand extends PlayerCommand {

    public LeaveCommand() {
        super("leave", new CommandDescription(OtherMessages.Help_LeaveCommand.getMessage()));
    }
    private static double minPower = FactionsSystem.getSettings().getMinPower();
    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if (faction.getRankOfPlayer(player.getUniqueId()).equals(faction.getHighestRank())) {
            player.sendMessage(ErrorMessage.Player_Can_Not_Leave.getMessage());
            return true;
        }

        new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial())
                .setDisplayName(GUITags.Confirm_Description.getMessage()).setLore(GUITags.Leave_Confirm_Lore.getMessage()).getItem(), () -> {

            Bukkit.getPluginManager().callEvent(new FactionPlayerLeaveEvent(player, faction));

            if (faction.getPlayers().size() == 3) {
                if ((faction.getPowerCap() - FactionsSystem.getSettings().getPlayer3Power()) >= FactionsSystem.getSettings().getMinPower()) {
                    faction.removePowerCap(FactionsSystem.getSettings().getPlayer3Power());
                    if ((faction.getPower() - FactionsSystem.getSettings().getPlayer3Power()) >= minPower) {
                        faction.removePower(FactionsSystem.getSettings().getPlayer3Power());
                    }
                } else {
                    faction.setPowerCap(FactionsSystem.getSettings().getMinPower());
                }
            } else if (faction.getPlayers().size() == 2) {
                if ((faction.getPowerCap() - FactionsSystem.getSettings().getPlayer2Power()) >= FactionsSystem.getSettings().getMinPower()) {
                    faction.removePowerCap(FactionsSystem.getSettings().getPlayer2Power());
                    if ((faction.getPower() - FactionsSystem.getSettings().getPlayer2Power()) >= minPower) {
                        faction.removePower(FactionsSystem.getSettings().getPlayer2Power());
                    }
                } else {
                    faction.setPowerCap(FactionsSystem.getSettings().getMinPower());
                }
            } else {
                if ((faction.getPowerCap() - FactionsSystem.getSettings().getPowerLeaveDecrease()) >= FactionsSystem.getSettings().getMinPower()) {
                    faction.removePowerCap(FactionsSystem.getSettings().getPowerLeaveDecrease());
                    if((faction.getPower() - FactionsSystem.getSettings().getPowerLeaveDecrease()) >= minPower) {
                        faction.removePower(FactionsSystem.getSettings().getPowerLeaveDecrease());
                    }
                } else {
                    faction.setPowerCap(FactionsSystem.getSettings().getMinPower());
                }
            }

            FactionsSystem.getFactions().removePlayer(faction, player);

            if(faction.getPower() > faction.getPowerCap()) {
                faction.setPower(faction.getPowerCap());
            }

            FactionsSystem.getFactions().saveFaction(faction);
            for (UUID uuid : faction.getPlayers()) {
                Player all = Bukkit.getPlayer(uuid);
                if (all != null && all.isOnline()) {
                    all.getPlayer().sendMessage(OtherMessages.Player_Faction_Left.getMessage().replace("%player%", player.getName()));
                }
            }
            for (Player all : Bukkit.getOnlinePlayers()) {
                FactionsSystem.adapter.sendScoreboard(all);
            }
            player.sendMessage(SuccessMessage.Successfully_Left.getMessage().replace("%faction%", faction.getName()));

            if(FactionsSystem.getSettings().enableTablist()) {
                FactionsSystem.adapter.sendTabListHeaderFooter(player, ColorHelper.colorize(FactionsSystem.getSettings().tabHeader(player)),
                    ColorHelper.colorize(FactionsSystem.getSettings().tabFooter(player)));
            }
        }).open();

        return true;
    }
}
