package de.miinoo.factions.shop.gui;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.shop.ShopCategory;
import org.bukkit.entity.Player;

public class ShopEditCategoryAllGUI extends GUI {

    public ShopEditCategoryAllGUI(Player player) {
        super(player, GUITags.Shop_EditCategory.getMessage(), 36);

        addElement(0, new GUIArea(9, 4).fill(0, 0, 9, 1, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem()))
                .fill(0, 3, 9, 4, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<ShopCategory> list = new GUIList<ShopCategory>(9, 2, FactionsSystem.getShopConfiguration().getCategories(), category ->
                new GUIItem(Items.createItem(category.getIcon())
                        .setDisplayName(category.getName())
                        .setLore(category.getLore()).getItem(), () -> {
                    new ShopEditCategoryGUI(player, category).open();
                }));

        addElement(9, list);

        if (FactionsSystem.getShopConfiguration().getCategories().size() > 9) {
            addElement(size - 8, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }
    }
}
