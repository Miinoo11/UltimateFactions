package de.miinoo.factions.shop.gui;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.*;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.guis.ConfirmationGUI;
import de.miinoo.factions.shop.ShopCategory;
import org.bukkit.entity.Player;

public class ShopRemoveCategoryGUI extends GUI {

    public ShopRemoveCategoryGUI(Player player) {
        super(player, GUITags.Shop_RemoveCategory.getMessage(), 27);

        addElement(0, new GUIArea(9, 4).fill(0, 0, 9, 1, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem()))
                .fill(0, 3, 9, 4, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<ShopCategory> list = new GUIList<ShopCategory>(9, 2, FactionsSystem.getShopConfiguration().getCategories(), category ->
                new GUIItem(Items.createItem(category.getIcon())
                        .setDisplayName(category.getName())
                        .setLore(category.getLore()).getItem(), () -> {
                    new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName(GUITags.Confirm_Description.getMessage()).getItem(),
                            () -> {
                                FactionsSystem.getShopConfiguration().removeCategory(category);
                                player.sendMessage(SuccessMessage.Successfully_Removed_ShopCategory.getMessage());
                                close();
                            }).open();
                }));

        addElement(9, list);

        if (FactionsSystem.getShopConfiguration().getCategories().size() > 9) {
            addElement(size - 8, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }
    }
}
