package de.miinoo.factions.model.guis;

import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.*;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.util.AdminUtil;
import org.bukkit.entity.Player;

/**
 * @author Mino
 * 03.05.2020
 */
public class FactionsGUI extends GUI {

    private Factions factions = FactionsSystem.getFactions();

    public FactionsGUI(Player player, GUI gui) {
        super(player, "Factions", 45);

        addElement(0, new GUIArea(9, 5).fill(0, 0, 9, 1, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem()))
                .fill(0, 4, 9, 5, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        if(player.hasPermission("ultimatefactions.admin")) {
            addElement(4, new GUIItem(Items.createItem(XMaterial.ENCHANTED_GOLDEN_APPLE.parseMaterial())
                    .setDisplayName(GUITags.Admin_Bypass_All.getMessage())
                    .setLore(GUITags.Admin_Bypass_All_Lore.getMessage()).getItem(), () -> {
                if(AdminUtil.adminMode.contains(player)) {
                    player.sendMessage(ErrorMessage.ByPassAllMode_Enter_Error.getMessage());
                    close();
                    return;
                }
                if(AdminUtil.bypassAllMode.contains(player)) {
                    AdminUtil.bypassAllMode.remove(player);
                    for(Faction faction : factions.getFactions()) {
                        faction.removePlayer(player.getUniqueId());
                    }
                    player.sendMessage(SuccessMessage.Successfully_Left_ByPassAll.getMessage());
                } else {
                    AdminUtil.bypassAllMode.add(player);
                    for(Faction faction : factions.getFactions()) {
                        faction.addPlayer(player.getUniqueId(), faction.getHighestRank());
                    }
                    player.sendMessage(SuccessMessage.Successfully_Entered_ByPassAll.getMessage());
                }
                close();
            }));
        }

        UIList<Faction> list = new GUIList<Faction>(9, 3, factions.getFactions(),
                faction -> new GUIItem(Items.createItem(XMaterial.MAGMA_CREAM.parseMaterial())
                        .setDisplayName("§c" + faction.getName()).setLore(GUITags.Admin_Leader_Lore.getMessage().replace("%leader%", faction.getLeader().getName())).getItem(), () -> {
                    if(player.hasPermission("ultimatefactions.admin")) {
                        new AdminFactionInfoGUI(player, faction, this).open();
                    } else {
                        new FactionInfoGUI(player, faction).open();
                    }
                }));

        addElement(9, list);

        if (factions.getFactions().size() > 9) {
            addElement(size - 6, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 3, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }
        if(gui != null)
            addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
