package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.WarPiece;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * @author Mino
 * 03.05.2020
 */
public class WarPiecesGUI extends GUI {

    private Factions factions = FactionsSystem.getFactions();

    public WarPiecesGUI(Player player, Faction faction, GUI gui) {
        super(player, "WarPieces", 27);

        addElement(0, new GUIArea(9, 3).
                fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<WarPiece> list1 = new GUIList<WarPiece>(9, 1, faction.getWarPieces(), warPiece -> new GUIItem(Items.createItem(XMaterial.SLIME_BALL.parseMaterial())
                .setDisplayName("§c" + factions.getFaction(warPiece.getUuid()).getName())
                .setLore(GUITags.WarPieces_Lore1.getMessage().replace("%count%", "" +
                                (faction.getWarPieces(factions.getFaction(warPiece.getUuid())).getAmount() > 0 ? "+" + faction.getWarPieces(factions.getFaction(warPiece.getUuid())).getAmount() : faction.getWarPieces(factions.getFaction(warPiece.getUuid())).getAmount())),
                        GUITags.WarPieces_Lore2.getMessage().replace("%count%", "" +
                                (factions.getFaction(warPiece.getUuid()).getWarPieces(faction).getAmount() > 0 ? "+" + factions.getFaction(warPiece.getUuid()).getWarPieces(faction).getAmount() : factions.getFaction(warPiece.getUuid()).getWarPieces(faction).getAmount())))
                .getItem()));

        addElement(9, list1);

        if (faction.getWarPieces().size() > 9) {
            addElement(size - 6, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 3, list1,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
