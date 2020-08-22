package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.Rank;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 10.04.2020
 */
public class RankInfoGUI extends GUI {

    public RankInfoGUI(Player player, Faction faction, Rank rank) {
        super(player, rank.getName(), 27);
        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).setDisplayName("§r").getItem())));

        //addElement(11, new GUIItem(Items.createHead(player.getName()).setDisplayName(GUITags.Edit_Players.getMessage()).getItem(), () -> new EditPlayersGUI(player, faction, rank).open()));
        //addElement(15, new GUIItem(Items.createItem(XMaterial.ANVIL.parseMaterial()).setDisplayName(GUITags.Edit_Rank.getMessage()).getItem(), () -> new CreateRankGUI(player, faction, rank).open()));

        if(rank != faction.getHighestRank()) {
            addElement(11, new GUIItem(Items.createItem(XMaterial.BARRIER.parseMaterial()).setDisplayName(GUITags.Delete_Rank.getMessage()).getItem(),
                    () -> new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial())
                            .setDisplayName(GUITags.Confirm_Description.getMessage())
                            .setLore(GUITags.Delete_Rank_Confirm_Lore.getMessage()).getItem(), () -> {
                        faction.removeRank(rank);
                        FactionsSystem.getFactions().saveFaction(faction);
                        player.sendMessage(SuccessMessage.Successfully_Deleted_Rank.getMessage().replace("%rank%", rank.getName()));
                    }).open()));
            addElement(15, new GUIItem(Items.createItem(XMaterial.ANVIL.parseMaterial()).setDisplayName(GUITags.Edit_Rank.getMessage()).getItem(), () -> new CreateRankGUI(player, faction, rank).open()));
        }
    }
}