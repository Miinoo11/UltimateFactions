package de.miinoo.factions.quest.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.*;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.quest.Quest;
import org.bukkit.entity.Player;

public class CompletedQuestsGUI extends GUI {

    public CompletedQuestsGUI(Player player, Faction faction, GUI gui) {
        super(player, GUITags.Quests_Completed_Title.getMessage(), 45);

        addElement(0, new GUIArea(9, 5).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<Quest> list = new GUIList<Quest>(9, 3, FactionsSystem.getQuests().getCompletedQuests(faction), quest ->
                new GUIItem(Items.createItem(quest.getMaterial())
                        .setDisplayName(GUITags.Quests_Quest.getMessage().replace("%quest%", quest.getName()))
                        .addGlow(!quest.hasClaimed(player))
                        .setLore(GUITags.Quests_Quest_Completed_Lore.getMessage(),
                                GUITags.Quests_Quest_Lore1.getMessage().replace("%type%", quest.getType().getName()),
                                GUITags.Quests_Quest_Lore2.getMessage(),
                                GUITags.Quests_Quest_Description.getMessage()
                                        .replace("%description%", GUITags.Quests_Quest_Description.getMessage().replace("%description%", quest.getDescription())),
                                GUITags.Quests_Quest_Lore2.getMessage(),
                                GUITags.Quests_Quest_Lore_Reward.getMessage().replace("%reward%", quest.getReward().getRewardText()),
                                GUITags.Quests_Quest_Lore2.getMessage(),
                                !quest.hasClaimed(player) ? GUITags.Quests_Quest_Completed_Claim_Lore.getMessage() : GUITags.Quests_Quest_Completed_Claim_Lore2.getMessage())
                        .getItem(), () -> {

                    if(!faction.hasPermission(player.getUniqueId(), RankPermission.CLAIM_QUESTS)) {
                        player.sendMessage(ErrorMessage.Player_Not_Permitted.getMessage());
                        close();
                        return;
                    }

                    if(!quest.hasClaimed(player)) {
                        quest.getReward().give(player);
                        player.sendMessage(SuccessMessage.Successfully_Claimed_Reward.getMessage().replace("%reward%", quest.getReward().getRewardText()).replace("%quest%", quest.getName()));
                        quest.addToClaimed(player);
                        FactionsSystem.getQuests().saveQuest(quest);
                        close();
                    }
                }));

        addElement(9, list);

        if (FactionsSystem.getQuests().getQuests().size() > 18) {
            addElement(size - 6, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 3, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
