package de.miinoo.factions.shop.gui;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.GUI;
import de.miinoo.factions.core.ui.gui.GUIItem;
import de.miinoo.factions.core.ui.gui.GUIList;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.events.ShopBuyItemEvent;
import de.miinoo.factions.events.ShopSellItemEvent;
import de.miinoo.factions.shop.ShopCategory;
import de.miinoo.factions.shop.ShopItem;
import de.miinoo.factions.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopBuyGUI extends GUI {

    public ShopBuyGUI(Player player, ShopCategory category) {
        super(player, category.getName(), 36);

        addElement(0, new GUIList<ShopItem>(9, 3, category.getItems(),
                item -> new GUIItem(Items.createItem(item.getItemStack())
                        .setDisplayName(item.getDisplayName())
                        .setLore(lore(item))
                        .addEnchantments(item.getItemStack().getEnchantments())
                        .setAmount(item.getAmount())
                        .getItem()).setOnClickListener((player1, item1, event) -> {

                    System.out.println(item.getItemStack());
                    System.out.println(item1.getItem());

                    switch (event.getClick()) {
                        case LEFT:
                            if ((FactionsSystem.getEconomy().getBalance(player) - item.getPrice()) >= 0) {
                                if (!ItemUtil.hasAvailableSlot(player.getInventory(), item.getAmount())) {
                                    close();
                                    player.sendMessage(ErrorMessage.Inventory_Full_Error.getMessage());
                                    break;
                                } else {
                                    Bukkit.getPluginManager().callEvent(new ShopBuyItemEvent(player, item));

                                    FactionsSystem.getEconomy().withdrawPlayer(player, item.getPrice());
                                    close();

                                    player.getInventory().addItem(item.getItemStack());
                                    player.sendMessage(SuccessMessage.Successfully_Bought_ShopItem.getMessage()
                                            .replace("%amount%", String.valueOf(item.getAmount()))
                                            .replace("%item%", item.getDisplayName()));
                                }
                            } else {
                                close();
                                player.sendMessage(ErrorMessage.Not_Enough_Money.getMessage());
                            }
                            break;
                        case RIGHT:
                            if(item.canSell()) {
                                if (!ItemUtil.takeItem(player.getInventory(),item.getItemStack(), item.getAmount())) {
                                    close();
                                    player.sendMessage(ErrorMessage.Shop_Sell_Item_Error.getMessage());
                                    break;
                                } else {
                                    Bukkit.getPluginManager().callEvent(new ShopSellItemEvent(player, item));
                                    FactionsSystem.getEconomy().depositPlayer(player, item.getSell());
                                    close();
                                    player.sendMessage(SuccessMessage.Successfully_Sold_ShopItem.getMessage()
                                            .replace("%amount%", String.valueOf(item.getAmount()))
                                            .replace("%item%", item.getDisplayName()));
                                }
                            }
                            break;
                    }

                    return true;
                })));

    }

    private List<String> lore(ShopItem item) {
        List<String> lores = new ArrayList<>();
        if (item.canSell()) {
            lores.addAll(Arrays.asList(item.getLore(), "§8§m          ",
                    GUITags.Shop_Item_Price_Lore.getMessage()
                            .replace("%price%", String.valueOf(item.getPrice())),
                    GUITags.Shop_Item_Sell_Price_Lore.getMessage()
                            .replace("%price%", String.valueOf(item.getSell()))));
        } else {
            lores.addAll(Arrays.asList(item.getLore(), "§8§m          ",
                    GUITags.Shop_Item_Price_Lore.getMessage()
                            .replace("%price%", String.valueOf(item.getPrice()))));
        }

        return lores;
    }

}
