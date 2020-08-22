package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.guis.ConfirmationGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mino
 * 14.04.2020
 */
public class SetLeaderCommand extends PlayerCommand {

    public SetLeaderCommand() {
        super("setleader", new CommandDescription("Promote a new leader", "<player>"));
    }

    private Factions factions = FactionsSystem.getFactions();

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = factions.getFaction(player);

        if(!args.hasExactly(1)) {
            player.sendMessage(ErrorMessage.Set_Leader_Syntax.getMessage());
            return true;
        }

        if (faction == null) {
            player.sendMessage(ErrorMessage.Player_Not_In_Faction.getMessage());
            return true;
        }

        if(!faction.isHighestRank(player)) {
            player.sendMessage(ErrorMessage.Player_Not_Permitted.getMessage());
            return true;
        }

        Player target = Bukkit.getPlayer(args.get(0));

        if(!(target != null && target.isOnline())) {
            player.sendMessage(ErrorMessage.Player_Not_Found.getMessage());
            return true;
        }

        if(!faction.getPlayers().contains(target.getUniqueId())) {
            player.sendMessage(ErrorMessage.Target_Not_In_Faction.getMessage());
            return true;
        }

        new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial())
                .setDisplayName(GUITags.Confirm_Description.getMessage())
                .setLore(GUITags.Promote_Leader_Confirm_Lore.getMessage().replace("%player%", target.getName())).getItem(), () -> {

            factions.setPlayer(faction, player, faction.getSecondHighestRank());
            factions.setPlayer(faction, target, faction.getHighestRank());
            factions.saveFaction(faction);

            player.sendMessage(SuccessMessage.Successfully_Promoted.getMessage().replace("%player%", target.getName()));
            target.sendMessage(OtherMessages.Player_Get_Promoted.getMessage());

        }).open();

        return true;
    }
    @Override
    public List<String> complete(Player player, ArgumentParser args) {
        List<String> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> players.add(p.getName()));
        return players;
    }
}
