package de.miinoo.factions.shop.gui;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.gui.ListGUI;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.DependGUIItem;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.api.ui.input.AnvilInput;
import de.miinoo.factions.api.ui.input.GUIInput;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.shop.ShopCategory;
import de.miinoo.factions.util.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopEditCategoryGUI extends GUI {

    public ShopEditCategoryGUI(Player player, ShopCategory category) {
        super(player, GUITags.Shop_EditCategory.getMessage(), 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(9, new DependGUIItem(() -> Items.createItem(XMaterial.NAME_TAG.parseMaterial())
                .setDisplayName(GUITags.Shop_EditCategory_Name.getMessage().replace("%name%", category.getName())).getItem(), () -> {
            GUIInput.get(player, GUITags.Shop_AddCategory_Enter_Name.getMessage(), input -> {
                category.setName(ChatColor.translateAlternateColorCodes('&', input));
                open();
            }, AnvilInput.class);
        }));

        addElement(11, new DependGUIItem(() -> Items.createItem(XMaterial.PAPER.parseMaterial())
                .setDisplayName(GUITags.Shop_EditCategory_Description.getMessage().replace("%description%", category.getLore())).getItem(), () -> {
            GUIInput.get(player, GUITags.Shop_AddCategory_Enter_Description.getMessage(), input -> {
                category.setLore(ChatColor.translateAlternateColorCodes('&', input));
                open();
            }, AnvilInput.class);
        }));

        addElement(13, new DependGUIItem(() -> Items.createItem(category.getIcon() != null ? category.getIcon() : XMaterial.GRASS_BLOCK.parseItem())
                .setDisplayName(GUITags.Shop_AddCategory_Icon.getMessage()).getItem(), () -> {
            new ListGUI<ItemStack>(player, GUITags.Shop_ChooseIcon.getMessage(), ItemUtil.getItemsAsList(player.getInventory()),
                    icon -> new GUIItem(Items.createItem(icon.getType())
                            .setAmount(icon.getAmount())
                            .getItem()),
                    (player1, list, index, element, event) -> {
                        category.setIcon(element);
                        open();
                        return true;
                    }).open();
        }));

        addElement(15, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkZDIwYmU5MzUyMDk0OWU2Y2U3ODlkYzRmNDNlZmFlYjI4YzcxN2VlNmJmY2JiZTAyNzgwMTQyZjcxNiJ9fX0=")
                .setDisplayName(GUITags.Shop_EditCategory_Add_Item.getMessage()).getItem(),
                () -> new ShopAddItemGUI(player, category).open()));

        addElement(17, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ4YTk5ZGIyYzM3ZWM3MWQ3MTk5Y2Q1MjYzOTk4MWE3NTEzY2U5Y2NhOTYyNmEzOTM2Zjk2NWIxMzExOTMifX19")
                .setDisplayName(GUITags.Shop_EditCategory_Remove_Item.getMessage()).getItem(),
                () -> new ShopRemoveItemGUI(player, category).open()));

        addElement(size - 5, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
                .setDisplayName(GUITags.Save.getMessage())
                .getItem(), () -> {

            FactionsSystem.getShopConfiguration().saveCategory(category);
            close();
            player.sendMessage(SuccessMessage.Successfully_Added_ShopCategory.getMessage());
        }));
    }
}
