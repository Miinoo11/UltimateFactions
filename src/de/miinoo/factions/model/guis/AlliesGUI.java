package de.miinoo.factions.model.guis;

import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.*;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Mino
 * 21.04.2020
 */
public class AlliesGUI extends GUI {

    public AlliesGUI(Player player, Faction faction, GUI gui) {
        super(player, "Allies", 27);

        addElement(0, new GUIArea(9, 3).fill(0, 0, 9, 1, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem()))
                .fill(0, 2, 9, 3, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<UUID> list = new GUIList<UUID>(9, 1, faction.getAlliesRelation(),
                ally -> new GUIItem(Items.createItem(XMaterial.SLIME_BALL.parseMaterial())
                        .setDisplayName("§f" + FactionsSystem.getFactions().getFaction(ally).getName())
                        .setLore("§cAlly").getItem()).setOnClickListener(((player1, item, event) -> {
                    new AlliesEditGUI(player, faction, FactionsSystem.getFactions().getFactionByUUID(ally), this).open();
                    return true;
                })));
        addElement(9, list);
        if(faction.getRelations().size() > 9) {
            addElement(size - 6, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 3, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

        if(gui != null)
            addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
