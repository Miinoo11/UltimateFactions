package de.miinoo.factions.model.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.DependGUIItem;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.FactionLevel;
import org.bukkit.entity.Player;

/**
 * @author Miinoo_
 * 06.09.2020
 **/

public class UpgradeGUI extends GUI {

    public UpgradeGUI(Player player, Faction faction) {
        super(player, "§8Upgrade: §c" + faction.getName(), 27);

        addElement(0, new GUIArea(9, 3).
                fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(11, new DependGUIItem(() -> Items.createItem(XMaterial.OAK_SIGN.parseMaterial()).setDisplayName(
                GUITags.Info_All.getMessage())
                .setLore(GUITags.Upgrade_Info_Lore.getMessage().replace("%level%", String.valueOf(faction.getLevel())),
                        GUITags.Upgrade_Info_Lore1.getMessage().replace("%level%", faction.getNextLevel() > 0 ? String.valueOf(faction.getNextLevel()) : "§cX"),
                        GUITags.Upgrade_Info_Lore2.getMessage().replace("%cost%", String.valueOf(FactionsSystem.getFactionLevels().getCost(faction.getNextLevel())))).getItem()));

        if(faction.getNextLevel() != -1) {
            addElement(15, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWMyNjg3NmE0NTQ4ODQ0ZTI4YTZmN2JhMWYzNzdjODBlNTk0OTVmN2QzMjIxNGJjYzQ5MjgwNGIxNjYxOTMzOSJ9fX0=").setDisplayName(GUITags.Upgrade_Title.getMessage())
                    .setLore(GUITags.Upgrade_Lore.getMessage()).getItem(), () -> {
                if (!(FactionsSystem.getEconomy().getBalance(player) - FactionsSystem.getFactionLevels().getCost(faction.getNextLevel()) >= 0)) {
                    player.sendMessage(ErrorMessage.Not_Enough_Money.getMessage());
                    close();
                    return;
                }

                FactionsSystem.getEconomy().withdrawPlayer(player, FactionsSystem.getFactionLevels().getCost(faction.getNextLevel()));
                player.sendMessage(SuccessMessage.Successfully_Upgraded_Faction.getMessage().replace("%level%",
                        String.valueOf(faction.getNextLevel()))
                                .replace("%cost%", String.valueOf(FactionsSystem.getFactionLevels().getCost(faction.getNextLevel()))));
                faction.setLevel(new FactionLevel(faction.getNextLevel()));
                FactionsSystem.getFactions().saveFaction(faction);
                close();
            }));
        } else {
            addElement(15, new DependGUIItem(() -> Items.createSkull(
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZkMjQwMDAwMmFkOWZiYmJkMDA2Njk0MWViNWIxYTM4NGFiOWIwZTQ4YTE3OGVlOTZlNGQxMjlhNTIwODY1NCJ9fX0=")
                    .setDisplayName(GUITags.Upgrade_Maxed.getMessage()).getItem()));
        }

        addElement(size-5, new GUIItem(Items.createItem(XMaterial.PAPER.parseMaterial())
                .setDisplayName(GUITags.Upgrade_Upgrades.getMessage()).setLore(GUITags.Info_Click_Lore.getMessage()).getItem(), () -> {
            new UpgradesGUI(player, faction, this).open();
        }));
    }
}
