package de.miinoo.factions.shop.gui;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.guis.ConfirmationGUI;
import de.miinoo.factions.shop.ShopCategory;
import org.bukkit.entity.Player;

public class ShopMainGUI extends GUI {

    public ShopMainGUI(Player player) {
        super(player, GUITags.Shop_MainGUI.getMessage(), player.hasPermission("ultimatefactions.shop.edit") ? 45 : 36);

        addElement(0, new GUIArea(9, 4).fill(0, 0, 9, 1, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem()))
                .fill(0, 3, 9, 4, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<ShopCategory> list = new GUIList<ShopCategory>(9, 2, FactionsSystem.getShopConfiguration().getCategories(), category ->
                new GUIItem(Items.createItem(category.getIcon())
                        .setDisplayName(category.getName())
                        .setLore(category.getLore()).getItem(), () -> {
                    new ShopBuyGUI(player, category).open();
                }));

        addElement(9, list);

        if (player.hasPermission("ultimatefactions.shop.edit")) {
            addElement(38, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkZDIwYmU5MzUyMDk0OWU2Y2U3ODlkYzRmNDNlZmFlYjI4YzcxN2VlNmJmY2JiZTAyNzgwMTQyZjcxNiJ9fX0=")
                    .setDisplayName(GUITags.Shop_AddCategory.getMessage()).getItem(), () ->
                    new ShopAddCategoryGUI(player).open()));

            addElement(40, new GUIItem(Items.createItem(XMaterial.ANVIL.parseMaterial())
                    .setDisplayName(GUITags.Shop_EditCategory.getMessage()).getItem(), () -> {
                new ShopEditCategoryAllGUI(player).open();
            }));

            addElement(42, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ4YTk5ZGIyYzM3ZWM3MWQ3MTk5Y2Q1MjYzOTk4MWE3NTEzY2U5Y2NhOTYyNmEzOTM2Zjk2NWIxMzExOTMifX19")
                    .setDisplayName(GUITags.Shop_RemoveCategory.getMessage()).getItem(), () -> {
                new ShopRemoveCategoryGUI(player).open();
            }));
        }

        if (FactionsSystem.getShopConfiguration().getCategories().size() > 9) {
            addElement(size - 8, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

    }
}
