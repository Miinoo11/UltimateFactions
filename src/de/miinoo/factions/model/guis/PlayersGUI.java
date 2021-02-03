package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Mino
 * 18.04.2020
 */
public class PlayersGUI extends GUI {

    public PlayersGUI(Player player, Faction faction, GUI gui) {
        super(player, "Members", 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem())));

        UIList<UUID> list = new GUIList<UUID>(9, 1, faction.getPlayers(), uuid ->
                new GUIItem(Items.createSkull(Bukkit.getOfflinePlayer(uuid).getName())
                        .setDisplayName(Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).getPlayer().isOnline() ?
                                Bukkit.getPlayer(uuid).getPlayer().getName() : Bukkit.getOfflinePlayer(uuid).getName() + " §8(§cOffline§8)")
                        .setLore("§7- " + faction.getRankOfPlayer(uuid).getPrefix()).getItem()));

        addElement(9, list);

        if (faction.getPlayers().size() > 9) {
            addElement(size - 6, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 3, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
