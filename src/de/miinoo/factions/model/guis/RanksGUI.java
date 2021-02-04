package de.miinoo.factions.model.guis;

import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.*;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.Rank;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 10.04.2020
 */
public class RanksGUI extends GUI {

    public RanksGUI(Player player, Faction faction, GUI gui) {
        super(player, "Ranks", 27);
        addElement(0, new GUIArea(9, 3).fill(0, 0, 9, 1, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem()))
                .fill(0, 2, 9, 3, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<Rank> list = new GUIList<Rank>(9, 1, faction.getRanks(), rank -> new GUIItem(rank.createItem())).setOnClickListener(((player1, list1, index, element, event) -> {
            if (faction.hasPermission(player.getUniqueId(), RankPermission.ASSIGN_ROLES)) {
                new RankInfoGUI(player, faction, element, this).open();
            }
            return false;
        }));

        addElement(9, list);

        if (faction.getRanks().size() > 9)
            addElement(size - 7, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 5, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));

        if (faction.hasPermission(player.getUniqueId(), RankPermission.MANAGE_ROLES)) {
            addElement(getInventory().getSize() - 5,
                    new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkZDIwYmU5MzUyMDk0OWU2Y2U3ODlkYzRmNDNlZmFlYjI4YzcxN2VlNmJmY2JiZTAyNzgwMTQyZjcxNiJ9fX0=")
                            .setDisplayName(GUITags.Create_Rank.getMessage()).getItem(), () -> new CreateRankGUI(player, faction, null, this).open()));
        }
        if(gui != null)
        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
