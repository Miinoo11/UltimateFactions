package de.miinoo.factions.model.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.DependGUIItem;
import de.miinoo.factions.core.ui.gui.GUI;
import de.miinoo.factions.core.ui.gui.GUIArea;
import de.miinoo.factions.core.ui.gui.GUIItem;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author Miinoo_
 * 07.09.2020
 **/

public class UpgradesGUI extends GUI {

    public UpgradesGUI(Player player, Faction faction, GUI gui) {
        super(player, "§8Upgrades: §c" + faction.getName(), 54);

        addElement(0, new GUIArea(9, 6).
                fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(10, new DependGUIItem(() -> Items.createItem(XMaterial.FEATHER.parseMaterial()).setDisplayName(
                GUITags.Upgrades_Fly.getMessage())
                .setLore(faction.hasFly() ? GUITags.Upgrades_Lore_Unlocked.getMessage() : GUITags.Upgrades_Lore_Locked.getMessage()).getItem()
        ));

        addElement(12, new DependGUIItem(() -> Items.createItem(XMaterial.TNT.parseMaterial()).setDisplayName(
                GUITags.Upgrades_Fill.getMessage())
                .setLore(faction.hasFill() ? GUITags.Upgrades_Lore_Unlocked.getMessage() : GUITags.Upgrades_Lore_Locked.getMessage()).getItem()
        ));

        addElement(14, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk2MjdiZTYyY2VkNzE0MTEzOWQzZjE1NTc5MGE1ZDQzNTZlYjdiOWVlOTVlNTA0YjMzMjI5NzRjYmM1MTVlYSJ9fX0=")
                .setDisplayName(
                        GUITags.Upgrades_MemberCount.getMessage().replace("%value%", String.valueOf(FactionsSystem.getFactionLevels().getMaxMember(faction.getLevel()))))
                .setLore(nextValueLore(faction, String.valueOf(FactionsSystem.getFactionLevels().getMaxMember(faction.getNextLevel())))).getItem()));

        addElement(16, new GUIItem(Items.createItem(XMaterial.OAK_FENCE.parseMaterial()).setDisplayName(
                GUITags.Upgrades_ClaimCount.getMessage().replace("%value%", String.valueOf(FactionsSystem.getFactionLevels().getMaxClaims(faction.getLevel()))))
                .setLore(nextValueLore(faction, String.valueOf(FactionsSystem.getFactionLevels().getMaxClaims(faction.getNextLevel())))).getItem()
        ));

        addElement(20, new GUIItem(Items.createItem(XMaterial.CLOCK.parseMaterial()).setDisplayName(
                GUITags.Upgrades_TeleportDelay.getMessage().replace("%value%", String.valueOf(FactionsSystem.getFactionLevels().getWarpCooldown(faction.getLevel()))))
                .setLore(nextValueLore(faction, FactionsSystem.getFactionLevels().getWarpCooldown(faction.getNextLevel()) + "§7s")).getItem()
        ));

        addElement(22, new GUIItem(Items.createItem(XMaterial.STRING.parseMaterial()).setDisplayName(GUITags.Upgrades_MobDropMultiplayer.getMessage()
                .replace("%value%", String.valueOf(FactionsSystem.getFactionLevels().getMobDropMultiplier(faction.getLevel()))))
                .setLore(nextValueLore(faction, String.valueOf(FactionsSystem.getFactionLevels().getMobDropMultiplier(faction.getNextLevel())))).getItem()));

        addElement(24, new GUIItem(Items.createItem(XMaterial.ENDER_EYE.parseMaterial()).setDisplayName(
                GUITags.Upgrades_WarpCount.getMessage().replace("%value%", String.valueOf(FactionsSystem.getFactionLevels().getMaxWarps(faction.getLevel()))))
                .setLore(nextValueLore(faction, String.valueOf(FactionsSystem.getFactionLevels().getMaxWarps(faction.getNextLevel())))).getItem()
        ));

        addElement(30, new GUIItem(Items.createItem(XMaterial.GLASS_BOTTLE.parseMaterial()).setDisplayName(
                GUITags.Upgrades_PotionEffects.getMessage().replace("%value%",
                        FactionsSystem.getFactionLevels().isEffectsEnabled(faction.getLevel())
                                ? String.valueOf(FactionsSystem.getFactionLevels().getEffects(faction.getLevel()).size())
                                : "0"))
                .setLore(effectsLore(faction))
                .addLore(" ")
                .addLore(GUITags.Upgrades_PotionEffects_Next_Lore.getMessage())
                .addLore(nextEffectsLore(faction))
                .getItem()));

        //addElement(32, new GUIItem(Items.createItem(XMaterial.GOLDEN_HOE.parseMaterial()).setDisplayName(
        //        GUITags.Upgrades_GrowSpeed.getMessage().replace("%value%", String.valueOf(FactionsSystem.getFactionLevels().getGrowSpeedMultiplier(faction.getLevel()))))
        //        .setLore(nextValueLore(faction, String.valueOf(FactionsSystem.getFactionLevels().getGrowSpeedMultiplier(faction.getNextLevel()))))
        //        .getItem()));

        addElement(32, new GUIItem(Items.createItem(XMaterial.BARRIER.parseMaterial()).setDisplayName(
                GUITags.Upgrades_Soon.getMessage())
                .getItem()));

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));

    }

    private List<String> effectsLore(Faction faction) {
        List<String> effects = new ArrayList<>();
        if (FactionsSystem.getFactionLevels().isEffectsEnabled(faction.getLevel())) {
            for (String string : FactionsSystem.getFactionLevels().getEffects(faction.getLevel())) {
                effects.add(GUITags.Upgrades_PotionEffects_Lore_Format.getMessage().replace("%effect%", string));
            }
            return effects;
        }
        return Arrays.asList(GUITags.Upgrades_Lore_Locked.getMessage());
    }

    private List<String> nextEffectsLore(Faction faction) {
        List<String> effects = new ArrayList<>();
        List<String> nextEffects = new ArrayList<>();
        List<String> finalList = new ArrayList<>();
        if (faction.getNextLevel() > 0) {
            effects.addAll(FactionsSystem.getFactionLevels().getEffects(faction.getLevel()));
            nextEffects.addAll(FactionsSystem.getFactionLevels().getEffects(faction.getNextLevel()));
            nextEffects.removeAll(new HashSet(effects));
            for (String nextEffect : nextEffects) {
                finalList.add(GUITags.Upgrades_PotionEffects_Lore_Format.getMessage().replace("%effect%", nextEffect));
            }
            return finalList.isEmpty() ? effects : finalList;
        }
        return Arrays.asList(GUITags.Upgrade_Maxed.getMessage());
    }

    private String nextValueLore(Faction faction, String replace) {
        String lore = "";

        if (faction.getNextLevel() > 0) {
            lore = GUITags.Upgrades_Lore_Next_Value.getMessage().replace("%value%", replace);
        } else {
            lore = GUITags.Upgrade_Maxed.getMessage();
        }

        return lore;
    }
}
