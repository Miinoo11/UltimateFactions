package de.miinoo.factions.model.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.*;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.commands.subcommands.InviteCommand;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvitesGUI extends GUI {

    public InvitesGUI(Player player) {
        super(player, GUITags.Invites_Title.getMessage(), 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        List<Faction> invites = new ArrayList<>();
        for(Map.Entry<OfflinePlayer, Faction> map : InviteCommand.invited.entrySet()) {
            if(map.getKey().getPlayer().equals(player)) {
                invites.add(InviteCommand.invited.get(player));
            }
        }

        UIList<Faction> list = new GUIList<Faction>(9, 1, invites,
                faction -> new GUIItem(Items.createItem(XMaterial.MAGMA_CREAM.parseMaterial())
                        .setDisplayName(GUITags.Invites_Faction.getMessage().replace("%faction%", faction.getName()))
                        .getItem(), () -> {
                    new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial())
                            .setDisplayName(GUITags.Confirm_Description.getMessage())
                            .setLore(GUITags.Invites_Faction_Description_Lore.getMessage()).getItem(), () -> {
                        player.performCommand("f join " + faction.getName());
                    });
                }));

        addElement(9, list);

        if (FactionsSystem.getRegions().getRegions().size() > 9) {
            addElement(19, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }
    }
}
