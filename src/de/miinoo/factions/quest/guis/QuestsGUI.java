package de.miinoo.factions.quest.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.*;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.quest.Quest;
import org.bukkit.entity.Player;

public class QuestsGUI extends GUI {

    public QuestsGUI(Player player, Faction faction) {
        super(player, GUITags.Quests_Title.getMessage(), 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<Quest> list = new GUIList<Quest>(9, 1, FactionsSystem.getQuests().getQuests(),
                quest -> new GUIItem(Items.createItem(XMaterial.PAPER.parseMaterial())
                        .setDisplayName(GUITags.Quests_Quest.getMessage().replace("%quest%", quest.getName()))
                        .setLore(GUITags.Quests_Quest_Lore.getMessage()
                                .replace("%value%", quest.hasAccepted(faction) ? GUITags.True.getMessage() : GUITags.False.getMessage())
                                .replace("%action%", quest.getDescription().getAction()
                                .replace("%type%", quest.getType().getName())
                                .replace("%amount%", String.valueOf(quest.getQuestActionAmount()))
                                .replace("%object%", quest.getQuestActionObject().toString())))
                        .getItem(), () -> {

        }));

        addElement(9, list);

        if (FactionsSystem.getRegions().getRegions().size() > 9) {
            addElement(19, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 7, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }
    }
}
