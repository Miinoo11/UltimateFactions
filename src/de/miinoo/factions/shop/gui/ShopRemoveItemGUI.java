package de.miinoo.factions.shop.gui;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.guis.ConfirmationGUI;
import de.miinoo.factions.shop.ShopCategory;
import de.miinoo.factions.shop.ShopItem;
import org.bukkit.entity.Player;

public class ShopRemoveItemGUI extends GUI {

    public ShopRemoveItemGUI(Player player, ShopCategory category) {
        super(player, GUITags.Shop_RemoveItem.getMessage(), 27);

        addElement(0, new GUIArea(9, 4).fill(0, 0, 9, 1, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem()))
                .fill(0, 3, 9, 4, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<ShopItem> list = new GUIList<ShopItem>(9, 2, category.getItems(), item ->
                new GUIItem(Items.createItem(item.getItemStack())
                        .setDisplayName(item.getDisplayName())
                        .setLore(item.getLore()).getItem(), () -> {
                    new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName(GUITags.Confirm_Description.getMessage()).getItem(),
                            () -> {
                                category.removeItem(item);
                                FactionsSystem.getShopConfiguration().saveCategory(category);
                                player.sendMessage(SuccessMessage.Successfully_Removed_ShopItem.getMessage());
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