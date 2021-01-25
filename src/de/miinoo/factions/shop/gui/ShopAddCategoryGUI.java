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
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.shop.ShopCategory;
import de.miinoo.factions.util.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ShopAddCategoryGUI extends GUI {

    private String name = GUITags.Shop_AddCategory_Name.getMessage();
    private String description = GUITags.Shop_AddCategory_Description.getMessage();
    private ItemStack item = null;

    public ShopAddCategoryGUI(Player player) {
        super(player, GUITags.Shop_AddCategory.getMessage(), 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(10, new DependGUIItem(() -> Items.createItem(XMaterial.NAME_TAG.parseMaterial())
                            .setDisplayName(!name.equals(GUITags.Shop_AddCategory_Name.getMessage()) ? name : GUITags.Shop_AddCategory_Name.getMessage()).getItem(), ()-> {
            GUIInput.get(player, GUITags.Shop_AddCategory_Enter_Name.getMessage(), input -> {
                name = ChatColor.translateAlternateColorCodes('&', input);
                open();
            }, AnvilInput.class);
        }));

        addElement(13, new DependGUIItem(() -> Items.createItem(XMaterial.PAPER.parseMaterial())
                .setDisplayName(!description.equals(GUITags.Shop_AddCategory_Description.getMessage()) ? description : GUITags.Shop_AddCategory_Description.getMessage()).getItem(), ()-> {
            GUIInput.get(player, GUITags.Shop_AddCategory_Enter_Description.getMessage(), input -> {
                description = ChatColor.translateAlternateColorCodes('&', input);
                open();
            }, AnvilInput.class);
        }));

        addElement(16, new DependGUIItem(() -> Items.createItem(item != null ? item : XMaterial.GRASS_BLOCK.parseItem())
                .setDisplayName(GUITags.Shop_AddCategory_Icon.getMessage()).getItem(), () -> {
            new ListGUI<ItemStack>(player, GUITags.Shop_ChooseIcon.getMessage(), ItemUtil.getItemsAsList(player.getInventory()),
                    icon -> new GUIItem(Items.createItem(icon.getType())
                            .setAmount(icon.getAmount())
                            .addEnchantments(icon.getEnchantments())
                            .getItem()), (player1, list, index, element, event) -> {
                item = element;
                open();
                return true;
            }).open();
        }));

        addElement(size - 5, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
                .setDisplayName(GUITags.Save.getMessage())
                .getItem(), () -> {

            if(item == null) {
                player.sendMessage(ErrorMessage.Shop_Category_Create_Item_Error.getMessage());close();
                return;
            }

            FactionsSystem.getShopConfiguration().saveCategory(new ShopCategory(name, item, description));
            close();
            player.sendMessage(SuccessMessage.Successfully_Added_ShopCategory.getMessage());
        }));
    }
}
