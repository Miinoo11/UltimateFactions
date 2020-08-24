package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.util.TopUtil;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Mino
 * 17.05.2020
 */
public class TopGUI extends GUI {

    private final NumberFormat formatter = new DecimalFormat("#,###.00");

    public TopGUI(Player player) {
        super(player, "Top Factions", 36);

        addElement(0, new GUIArea(9, 4).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<Faction> list = new GUIList<Faction>(5, 2, TopUtil.getTopFactions(), faction ->
                 new DependGUIItem(() -> Items.createItem(XMaterial.MAGMA_CREAM.parseMaterial())
                .setDisplayName(GUITags.TopFactions_Faction.getMessage().replace("%faction%", faction.getName()))
                .setLore(GUITags.TopFactions_Faction_Lore.getMessage().replace("%description%", faction.getDescription()),
                        GUITags.TopFactions_Faction_Lore1.getMessage(),
                        GUITags.TopFactions_Faction_Lore2.getMessage())
                .addLore(TopUtil.itemLores(faction))
                .addLore(GUITags.TopFactions_Faction_Lore3.getMessage().replace("%amount%", faction.getBank() > 0 ? formatter.format(faction.getBank()) : "0.0"))
                .addLore(" ", GUITags.TopFactions_Faction_Lore4.getMessage()
                                .replace("%value%", TopUtil.getFactionValue(faction) > 0 ? formatter.format(TopUtil.getFactionValue(faction)) : "0.0"))
                .setAmount(+1)
                .getItem(), () -> {
                     new FactionInfoGUI(player, faction).open();
                 }));

        addElement(11, list);
    }
}
