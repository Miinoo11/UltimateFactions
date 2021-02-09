package de.miinoo.factions.quest.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.core.gui.ListGUI;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.DependGUIItem;
import de.miinoo.factions.core.ui.gui.GUI;
import de.miinoo.factions.core.ui.gui.GUIArea;
import de.miinoo.factions.core.ui.gui.GUIItem;
import de.miinoo.factions.core.ui.input.AnvilInput;
import de.miinoo.factions.core.ui.input.GUIInput;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.quest.QuestAction;
import de.miinoo.factions.quest.QuestReward;
import de.miinoo.factions.quest.QuestType;
import de.miinoo.factions.quest.QuestTypes;
import de.miinoo.factions.quest.rewards.MoneyReward;
import de.miinoo.factions.quest.types.CollectQuest;
import de.miinoo.factions.quest.types.KillQuest;
import de.miinoo.factions.quest.types.TameQuest;
import de.miinoo.factions.util.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CreateQuestGUI extends GUI {

    private String name;
    private String description;
    private Material icon = XMaterial.GRASS_BLOCK.parseMaterial();
    private QuestType type;
    private QuestReward reward;
    QuestAction action;

    public CreateQuestGUI(Player player, GUI gui) {
        super(player, GUITags.Quests_Create_Quest_Title.getMessage(), 36);

        addElement(0, new GUIArea(9, 4).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(10, new DependGUIItem(() -> Items.createItem(XMaterial.NAME_TAG.parseMaterial())
                .setDisplayName(name != null ? "§6Name: " + name : GUITags.Set_Name.getMessage())
                .getItem(), () -> GUIInput.get(player, name != null ? name : GUITags.Quest_Enter_Name.getMessage(), input -> {
            name = input;
            open();
        }, AnvilInput.class)));

        addElement(12, new DependGUIItem(() -> Items.createItem(XMaterial.PAPER.parseMaterial())
                .setDisplayName(GUITags.Set_Description.getMessage())
                .setLore(description != null ? "§7" + description : null)
                .getItem(), () -> GUIInput.get(player, description != null ? description : GUITags.Quest_Enter_Description.getMessage(), input -> {
            description = input;
            open();
        }, AnvilInput.class)));

        addElement(14, new DependGUIItem(() -> Items.createItem(icon)
                .setDisplayName(GUITags.Set_Icon.getMessage())
                .getItem(),
                () -> new ListGUI<ItemStack>(player, GUITags.Quest_Choose_Icon.getMessage(), ItemUtil.getItemsAsList(player.getInventory()), is -> new GUIItem(Items.createItem(is).getItem()),
                        (player2, list, index, element, event) -> {
                            icon = element.getType();
                            open();
                            return true;
                        }).open()));

        addElement(16, new DependGUIItem(() -> Items.createItem(XMaterial.HOPPER.parseMaterial())
                .setDisplayName(GUITags.Quest_Set_Type.getMessage())
                .setLore(type != null ? "§8- §7" + type.getName() : null)
                .getItem(),
                () -> new ListGUI<QuestTypes>(player, GUITags.Quest_Choose_Type.getMessage(), QuestTypes.getTypes(),
                        qt -> new GUIItem(Items.createItem(qt.getIcon()).setDisplayName("§b" + qt.getName()).getItem()),
                        (player2, list, index, element, event) -> {
                            type = new QuestType(QuestTypes.getType(element.getIcon()));
                            open();
                            return true;
                        }).open()));

        addElement(20, new DependGUIItem(() -> Items.createItem(XMaterial.GOLD_INGOT.parseMaterial())
                .setDisplayName(reward != null ? "§eReward: " + reward.getRewardText() : GUITags.Quest_Set_Reward.getMessage())
                .getItem(), () -> {
            GUIInput.get(player, GUITags.Quest_Enter_Reward.getMessage(), input -> {
                String string = ChatColor.translateAlternateColorCodes('&', input);
                try {
                    double priceDouble = Double.parseDouble(string);
                    reward = new MoneyReward("Money", GUITags.Quest_Reward_Text.getMessage().replace("%reward%", String.valueOf(priceDouble)), priceDouble);
                } catch (NumberFormatException exception) {
                    close();
                    player.sendMessage(ErrorMessage.Valid_Number_Error.getMessage());
                    return;
                }
                open();
            }, AnvilInput.class);
        }));

        addElement(24, new DependGUIItem(() -> Items.createItem(type != null ? type.getIcon() : XMaterial.DIAMOND_SWORD.parseMaterial())
                .setDisplayName(GUITags.Quest_Set_Action.getMessage())
                .getItem(), () -> {
            GUIInput.get(player, GUITags.Quest_Enter_Action.getMessage(), input -> {
                String string = input;

                String sAmount = input.split("|")[0];
                int amount;
                try {
                    amount = Integer.parseInt(sAmount);
                } catch (NumberFormatException e) {
                    player.sendMessage(ErrorMessage.Valid_Number_Error.getMessage());
                    close();
                    return;
                }
                if (type != null) {
                    // check if kill quest
                    if (type.getName().equals(QuestTypes.KILL.getName())) {
                        if (string.split(";")[1] != null) {
                            try {
                                EntityType entityType = EntityType.valueOf(string.split(";")[1]);
                                action = new QuestAction(amount, entityType.toString());
                            } catch (IllegalArgumentException e) {
                                player.sendMessage(ErrorMessage.Quest_Create_Entity_Not_Found.getMessage());
                            }
                        }
                    } else if (type.getName().equals(QuestTypes.COLLECT.getName())) {
                        if (string.split(";")[1] != null) {
                            Material toCollect = Material.getMaterial(string.split(";")[1]);
                            if (toCollect != null) {
                                action = new QuestAction(amount, toCollect.toString());
                            } else {
                                player.sendMessage(ErrorMessage.Quest_Create_Item_Not_Found.getMessage());
                            }
                        }
                    } else if (type.getName().equals(QuestTypes.TAME.getName())) {
                        if (string.split(";")[1] != null) {
                            try {
                                EntityType entityType = EntityType.valueOf(string.split(";")[1]);
                                action = new QuestAction(amount, entityType.toString());
                            } catch (IllegalArgumentException e) {
                                player.sendMessage(ErrorMessage.Quest_Create_Entity_Not_Found.getMessage());
                            }
                        }
                    }
                } else {
                    player.sendMessage(ErrorMessage.Quest_Create_Select_First.getMessage());
                }
                open();
            }, AnvilInput.class);
        }));

        addElement(size - 5, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
                .setDisplayName(GUITags.Save.getMessage())
                .getItem(), () -> {

            if (name == null || description == null || type == null || action == null) {
                player.sendMessage(ErrorMessage.Quest_Create_Missing_Argument.getMessage());
                close();
                return;
            }

            if (type.getName().equals(QuestTypes.KILL.getName())) {
                KillQuest quest = new KillQuest(name, description, icon, action, reward);
                FactionsSystem.getQuests().saveQuest(quest);
            } else if (type.getName().equals(QuestTypes.COLLECT.getName())) {
                CollectQuest quest = new CollectQuest(name, description, icon, action, reward);
                FactionsSystem.getQuests().saveQuest(quest);
            } else if (type.getName().equals(QuestTypes.TAME.getName())) {
                TameQuest quest = new TameQuest(name, description, icon, action, reward);
                FactionsSystem.getQuests().saveQuest(quest);
            }


            close();
            player.sendMessage(SuccessMessage.Successfully_Created_Quest.getMessage());
        }));

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
