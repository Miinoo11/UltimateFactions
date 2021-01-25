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
import de.miinoo.factions.api.xutils.XEnchantment;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.shop.ShopCategory;
import de.miinoo.factions.shop.ShopItem;
import de.miinoo.factions.util.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ShopAddItemGUI extends GUI {

    private String name = GUITags.Shop_AddItem_Name.getMessage();
    private String description = GUITags.Shop_AddItem_Description.getMessage();
    private String price = GUITags.Shop_AddItem_Price.getMessage();
    private String sellPrice = GUITags.Shop_AddItem_Sell_Price.getMessage();
    private boolean canSell = true;
    private ItemStack item = null;

    public ShopAddItemGUI(Player player, ShopCategory category) {
        super(player, GUITags.Shop_AddItem.getMessage(), 45);

        addElement(0, new GUIArea(9, 5).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(10, new DependGUIItem(() -> Items.createItem(XMaterial.NAME_TAG.parseMaterial())
                .setDisplayName(!name.equals(GUITags.Shop_AddItem_Name.getMessage()) ? name : GUITags.Shop_AddItem_Name.getMessage()).getItem(), () -> {
            GUIInput.get(player, GUITags.Shop_AddItem_Enter_Name.getMessage(), input -> {
                name = ChatColor.translateAlternateColorCodes('&', input);
                open();
            }, AnvilInput.class);
        }));

        addElement(12, new DependGUIItem(() -> Items.createItem(XMaterial.PAPER.parseMaterial())
                .setDisplayName(!description.equals(GUITags.Shop_AddItem_Description.getMessage()) ? description : GUITags.Shop_AddItem_Description.getMessage()).getItem(), () -> {
            GUIInput.get(player, GUITags.Shop_AddItem_Enter_Description.getMessage(), input -> {
                description = ChatColor.translateAlternateColorCodes('&', input);
                open();
            }, AnvilInput.class);
        }));

        addElement(14, new DependGUIItem(() -> Items.createItem(XMaterial.GOLD_INGOT.parseMaterial())
                .setDisplayName(!price.equals(GUITags.Shop_AddItem_Price.getMessage()) ? price : GUITags.Shop_AddItem_Price.getMessage()).getItem(), () -> {
            GUIInput.get(player, GUITags.Shop_AddItem_Enter_Price.getMessage(), input -> {
                price = ChatColor.translateAlternateColorCodes('&', input);
                try {
                    double priceDouble = Double.parseDouble(price);
                } catch (NumberFormatException exception) {
                    close();
                    player.sendMessage(ErrorMessage.Valid_Number_Error.getMessage());
                    return;
                }
                open();
            }, AnvilInput.class);
        }));

        addElement(16, new DependGUIItem(() -> Items.createItem(XMaterial.DIAMOND.parseMaterial())
                .setDisplayName(!sellPrice.equals(GUITags.Shop_AddItem_Sell_Price.getMessage()) ? sellPrice : GUITags.Shop_AddItem_Sell_Price.getMessage()).getItem(), () -> {
            GUIInput.get(player, GUITags.Shop_AddItem_Enter_Sell_Price.getMessage(), input -> {
                sellPrice = ChatColor.translateAlternateColorCodes('&', input);
                try {
                    double sellPriceDouble = Double.parseDouble(sellPrice);
                } catch (NumberFormatException exception) {
                    close();
                    player.sendMessage(ErrorMessage.Valid_Number_Error.getMessage());
                    return;
                }
                open();
            }, AnvilInput.class);
        }));

        addElement(24, new DependGUIItem(() -> Items.createItem(item != null ? item : XMaterial.GRASS_BLOCK.parseItem())
                .setDisplayName(GUITags.Shop_AddItem_Item.getMessage()).getItem(), () -> {
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

        addElement(20, new DependGUIItem(() -> Items.createItem(XMaterial.REDSTONE_TORCH.parseMaterial())
                .setDisplayName(GUITags.Shop_AddItem_canSell.getMessage())
                .setLore(canSell ? GUITags.Enabled.getMessage() : GUITags.Disabled.getMessage()).getItem(), () -> {
            canSell = !canSell;
            build();
        }));

        addElement(size - 5, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
                .setDisplayName(GUITags.Save.getMessage())
                .getItem(), () -> {

            if(item == null) {
                player.sendMessage(ErrorMessage.Shop_Add_Item_Item_Error.getMessage());
                close();
                return;
            }

            category.addItem(new ShopItem(item, name, item.getAmount(), description, Double.parseDouble(price), Double.parseDouble(sellPrice), canSell));
            FactionsSystem.getShopConfiguration().saveCategory(category);
            close();
            player.sendMessage(SuccessMessage.Successfully_Added_ShopItem.getMessage());
        }));
    }
}
