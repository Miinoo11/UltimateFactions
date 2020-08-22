package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Mino
 * 21.04.2020
 */
public class AlliesGUI extends GUI {

    public AlliesGUI(Player player, Faction faction) {
        super(player, "Allies", 27);

        addElement(0, new GUIArea(9, 3).fill(0, 0, 9, 1, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).setDisplayName(" ").getItem()))
                .fill(0, 2, 9, 3, new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).setDisplayName("§r").getItem())));

        UIList<UUID> list = new GUIList<UUID>(9, 1, faction.getAlliesRelation(),
                ally -> new GUIItem(Items.createItem(XMaterial.SLIME_BALL.parseMaterial())
                        .setDisplayName("§f" + FactionsSystem.getFactions().getFaction(ally).getName())
                        .setLore("§cAlly").getItem()).setOnClickListener(((player1, item, event) -> {
                    new AlliesEditGUI(player, faction, FactionsSystem.getFactions().getFactionByUUID(ally)).open();
                    return true;
                })));
        addElement(9, list);
        if(faction.getRelations().size() > 9) {
            addElement(19, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createHead("MHF_ArrowLeft").setDisplayName(GUITags.Back.getMessage()).getItem()),
                    new GUIItem(Items.createHead("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }
    }
}
