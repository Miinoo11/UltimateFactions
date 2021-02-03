package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.gui.ListGUI;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.DependGUIItem;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.api.ui.input.AnvilInput;
import de.miinoo.factions.api.ui.input.GUIInput;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.*;
import de.miinoo.factions.util.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Mino
 * 06.04.2020
 */
public class CreateRankGUI extends GUI {

    private Material material = XMaterial.IRON_CHESTPLATE.parseMaterial();
    private String name;
    private String prefix;
    private Collection<RankPermissionValue> permissions;

    public CreateRankGUI(Player player, Faction faction, Rank rank, GUI gui) {
        super(player, "Create Rank", 27);
        if(rank != null) {
            material = rank.getMaterial();
            name = rank.getName();
            prefix = rank.getPrefix();
            permissions = RankPermission.getValues(rank.getPermissions());
        } else {
            permissions = RankPermission.getValues();
        }
        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        //addElement(10, new DependGUIItem(() -> Items.createItem(material).setDisplayName(GUITags.Set_Icon.getMessage()).getItem(),
        //        () -> new ListGUI<>(player, "Edit Icon", Arrays.asList(RankIcon.values()), icon -> new GUIItem(Items.createItem(icon.getMaterial()).getItem()),
        //                (player2, list, index, element, event) -> {
        //                    material = element.getMaterial();
        //                    open();
        //                    return true;
        //                }).open()));

        addElement(10, new DependGUIItem(() -> Items.createItem(material).setDisplayName(GUITags.Set_Icon.getMessage()).getItem(),
                () -> new ListGUI<ItemStack>(player, GUITags.Shop_ChooseIcon.getMessage(), ItemUtil.getItemsAsList(player.getInventory()), icon -> new GUIItem(Items.createItem(icon).getItem()),
                        (player2, list, index, element, event) -> {
                            material = element.getType();
                            open();
                            return true;
                        }).open()));

        addElement(12, new DependGUIItem(() -> Items.createItem(XMaterial.NAME_TAG.parseMaterial()).setDisplayName(name != null ? "§6Name: §f" + name : GUITags.Set_Name.getMessage()).getItem(),
                () -> GUIInput.get(player, name != null ? name : GUITags.Rank_Enter_Name.getMessage(), input -> {
                    name = input;
                    open();
                }, AnvilInput.class)));

        addElement(14, new DependGUIItem(() -> Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName(prefix != null ? "§6Prefix: §f" + prefix : GUITags.Set_Prefix.getMessage()).getItem(),
                () -> GUIInput.get(player, prefix != null ? prefix.replace('§', '&') : GUITags.Rank_Enter_Prefix.getMessage(), input -> {
                    prefix = ChatColor.translateAlternateColorCodes('&', input);
                    open();
                }, AnvilInput.class)));

        addElement(16, new DependGUIItem(() -> Items.createItem(XMaterial.DIAMOND.parseMaterial())
                .setDisplayName(GUITags.Set_Permissions.getMessage())
                .setLore(permissions.stream().filter(RankPermissionValue::getValue).map(per -> "§8- §7" + per.getPermission().getName()).collect(Collectors.toList())).getItem(),
                () -> new PermissionsGUI(player, permissions, this).open()));

        addElement(22, new GUIItem(Items.createSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
                .setDisplayName(GUITags.Save.getMessage()).getItem(),() -> {

            if(name == null) {
                player.sendMessage(ErrorMessage.Rank_Name_Null.getMessage());
            } else if(prefix == null) {
                player.sendMessage(ErrorMessage.Rank_Prefix_Null.getMessage());
            } else if(rank == null && faction.existRank(name)) {
                player.sendMessage(ErrorMessage.Rank_Already_Exists.getMessage());
            } else {
                if(rank != null) {
                    if(!rank.getName().equals(name)) {
                        faction.removeRank(rank);
                        rank.setName(name);
                    }
                    faction.removeRank(rank);
                    rank.setMaterial(material);
                    rank.setPermissions(permissions.stream().filter(RankPermissionValue::getValue).map(RankPermissionValue::getPermission).collect(Collectors.toList()));
                    rank.setPrefix(prefix);
                    faction.addRank(rank);
                    FactionsSystem.getFactions().saveFaction(faction);
                    player.sendMessage(SuccessMessage.Successfully_Edited_Rank.getMessage());
                } else {
                    Rank rank2 = new Rank(name, prefix, material,
                            permissions.stream().filter(RankPermissionValue::getValue).map(RankPermissionValue::getPermission).collect(Collectors.toList()));
                    faction.addRank(rank2);
                    FactionsSystem.getFactions().saveFaction(faction);
                    player.sendMessage(SuccessMessage.Successfully_Created_Rank.getMessage().replace("%rank%", rank2.getName()));
                }
                close();
            }
        }));

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
