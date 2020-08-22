package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * @author Mino
 * 11.05.2020
 */
public class BankInfoGUI extends GUI {

    public BankInfoGUI(Player player, Faction faction, Collection<Material> elements) {
        super(player, "Bank Info", elements.size() > 45 ? 54 : (elements.size() >= 0 && elements.size() % 9 == 0 ? elements.size() + 9 : ((elements.size() / 9) + 2) * 9));

        UIList<Material> list = new GUIList<Material>(9, elements.size() > 54 ? 5 : size / 9, elements, material -> new DependGUIItem(() ->
                Items.createItem(material).setLore(GUITags.Bank_Item_Info_Lore.getMessage()
                        .replace("%amount%", String.valueOf(formatAmount(faction, material))),
                        GUITags.Bank_Item_Info_Lore2.getMessage()
                                .replace("%amount%", Double.toString(formatAmount(faction, material) * FactionsSystem.getBank().getPrice(material)))
                        ).getItem()));

        addElement(0, list);
        if (elements.size() > 54) {
            addElement(46, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createHead("MHF_ArrowLeft").setDisplayName(GUITags.Back.getMessage()).getItem()),
                    new GUIItem(Items.createHead("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }
    }

    private int formatAmount(Faction faction, Material material) {
        if(faction.getBankItems() == null) {
            return 0;
        }
        if(!faction.getBankItems().containsKey(material)) {
            return 0;
        }
        if(faction.getBankItems().size() == 0) {
            return 0;
        }
        if(faction.getBankItemAmount(material) == 0) {
            return 0;
        }
        return faction.getBankItemAmount(material);
    }
}