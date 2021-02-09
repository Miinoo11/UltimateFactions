package de.miinoo.factions.quest.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.*;
import de.miinoo.factions.core.ui.ui.UIList;
import de.miinoo.factions.events.FactionAcceptedQuestEvent;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.quest.Quest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class QuestsGUI extends GUI {

    public QuestsGUI(Player player, Faction faction) {
        super(player, GUITags.Quests_Title.getMessage(), 45);

        addElement(0, new GUIArea(9, 5).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        UIList<Quest> list = new GUIList<Quest>(9, 3, FactionsSystem.getQuests().getNonCompletedQuests(faction), quest ->
                new GUIItem(Items.createItem(quest.getMaterial())
                        .setDisplayName(GUITags.Quests_Quest.getMessage().replace("%quest%", quest.getName()))
                        .addGlow(quest.hasAccepted(faction))
                        .setLore(GUITags.Quests_Quest_Lore.getMessage().replace("%value%", quest.hasAccepted(faction) ? GUITags.True.getMessage() : GUITags.False.getMessage()),
                                GUITags.Quests_Quest_Lore1.getMessage().replace("%type%", quest.getType().getName()),
                                GUITags.Quests_Quest_Lore2.getMessage(),
                                GUITags.Quests_Quest_Description.getMessage()
                                        .replace("%description%", GUITags.Quests_Quest_Description.getMessage().replace("%description%", quest.getDescription())),
                                GUITags.Quests_Quest_Lore2.getMessage(),
                                GUITags.Quests_Quest_Lore_Reward.getMessage().replace("%reward%", quest.getReward().getRewardText()),
                                GUITags.Quests_Quest_Lore2.getMessage(),
                                quest.hasAccepted(faction) ? GUITags.Quests_Quest_Process.getMessage()
                                        .replace("%value%", String.valueOf(quest.getProcess(faction)))
                                        .replace("%max%", String.valueOf(quest.getAction().getAmount())) : " ")
                        .getItem(), () -> {
                    if (!faction.hasPermission(player.getUniqueId(), RankPermission.MANAGE_QUESTS)) {
                        player.sendMessage(ErrorMessage.Player_Not_Permitted.getMessage());
                        return;
                    }

                    if (FactionsSystem.getQuests().hasReachedMaxActiveQuests(faction)) {
                        player.sendMessage(ErrorMessage.Quest_Max_Reached.getMessage());
                        close();
                        return;
                    }

                    if (!quest.hasAccepted(faction)) {
                        quest.addToAccepted(faction);
                        FactionsSystem.getQuests().saveQuest(quest);
                        Bukkit.getPluginManager().callEvent(new FactionAcceptedQuestEvent(player, faction, quest));
                        player.sendMessage(SuccessMessage.Successfully_Accepted_Quest.getMessage().replace("%quest%", quest.getName()));
                        close();
                    }

                }));

        addElement(9, list);

        addElement(size - 1, new GUIItem(Items.createItem(XMaterial.CHEST.parseMaterial())
                .setDisplayName(GUITags.Quests_Completed_Quests.getMessage())
                .setLore(GUITags.Info_Click_Lore.getMessage())
                .getItem(), () -> new CompletedQuestsGUI(player, faction, this).open()));

        if (FactionsSystem.getQuests().getQuests().size() >= 1) {
            addElement(size - 7, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 5, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

        if (player.hasPermission("ultimatefactions.quests.manage")) {
            addElement(getInventory().getSize() - 5,
                    new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkZDIwYmU5MzUyMDk0OWU2Y2U3ODlkYzRmNDNlZmFlYjI4YzcxN2VlNmJmY2JiZTAyNzgwMTQyZjcxNiJ9fX0=")
                            .setDisplayName(GUITags.Quests_Create_Quest.getMessage()).getItem(), () -> new CreateQuestGUI(player, this).open()));
        }
    }
}
