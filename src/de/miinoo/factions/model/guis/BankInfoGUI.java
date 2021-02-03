package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
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

    public BankInfoGUI(Player player, Faction faction, Collection<Material> elements, GUI gui) {
        super(player, "Bank Info", 45);

        addElement(0, new GUIArea(9, 5).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<Material> list = new GUIList<Material>(9, 3, elements, material -> new DependGUIItem(() ->
                Items.createItem(material).setLore(GUITags.Bank_Item_Info_Lore.getMessage()
                        .replace("%amount%", String.valueOf(formatAmount(faction, material))),
                        GUITags.Bank_Item_Info_Lore2.getMessage()
                                .replace("%amount%", Double.toString(formatAmount(faction, material) * FactionsSystem.getBank().getPrice(material)))
                        ).getItem()));

        addElement(9, list);
        if (elements.size() > 18) {
            addElement(size - 6, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 3, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
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
