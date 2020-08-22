package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.api.ui.gui.GUIList;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.Rank;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 13.04.2020
 */
public class SetRankGUI extends GUI {

    private Factions factions = FactionsSystem.getFactions();

    public SetRankGUI(Player player, Faction faction, Player target) {
        super(player, "Set Rank: " + target.getName(), 27);

        addElement(0, new GUIArea(9, 3).fill(0, 0, 9, 1, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).setDisplayName(" ").getItem()))
                .fill(0, 2, 9, 3, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).setDisplayName("§r").getItem())));
        UIList<Rank> list = new GUIList<Rank>(9, 1, faction.getRanks(), rank -> new GUIItem(Items.createItem(rank.getMaterial())
                .setDisplayName(rank.getPrefix()).setLore(GUITags.Set_Rank_Confirm_Lore.getMessage().replace("%rank%", rank.getName()).replace("%player%", target.getName())).getItem(),
                () -> new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial())
                .setDisplayName(GUITags.Confirm_Description.getMessage()).getItem(), () -> {
                    if(!faction.isHigherRank(target, player)) {
                        factions.setPlayer(faction, target, rank);
                        factions.saveFaction(faction);
                        player.sendMessage(SuccessMessage.Successfully_Set_Rank.getMessage().replace("%player%", target.getName()).replace("%rank%", rank.getName()));
                        target.sendMessage(OtherMessages.Player_Received_Rank.getMessage().replace("%rank%", rank.getName()));
                    } else {
                        player.sendMessage(ErrorMessage.Rank_Set_Higher_Error.getMessage());
                        close();
                    }
        }).open()));

        addElement(9, list);
    }
}
