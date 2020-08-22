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

    public PlayersGUI(Player player, Faction faction) {
        super(player, "Members", 27);

        addElement(0, new GUIArea(9, 3).fill(0, 0, 9, 1, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).setDisplayName(" ").getItem()))
                .fill(0, 2, 9, 3, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).setDisplayName("§r").getItem())));

        UIList<UUID> list = new GUIList<UUID>(9, 1, faction.getPlayers(), uuid ->
                new GUIItem(Items.createHead(Bukkit.getOfflinePlayer(uuid).getName())
                        .setDisplayName(Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).getPlayer().isOnline() ?
                                Bukkit.getPlayer(uuid).getPlayer().getName() : Bukkit.getOfflinePlayer(uuid).getName() + " §8(§cOffline§8)")
                        .setLore("§7- " + faction.getRankOfPlayer(uuid).getPrefix()).getItem()));

        addElement(9, list);

        if (faction.getPlayers().size() > 9) {
            addElement(19, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createHead("MHF_ArrowLeft").setDisplayName(GUITags.Back.getMessage()).getItem()),
                    new GUIItem(Items.createHead("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }
    }
}
