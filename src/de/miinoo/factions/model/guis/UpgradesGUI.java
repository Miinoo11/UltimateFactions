package de.miinoo.factions.model.guis;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.DependGUIItem;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;

/**
 * @author Miinoo_
 * 07.09.2020
 **/

public class UpgradesGUI extends GUI {

    public UpgradesGUI(Player player, Faction faction, GUI gui) {
        super(player, "§8Upgrades: §c" + faction.getName(), 36);

        addElement(0, new GUIArea(9, 4).
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

        addElement(size-5, new GUIItem(Items.createSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGMxY2E5ZGM1YzdjOTQxYTY3N2I0MDgzMjc1N2E5MDk4NzFhMjk1ZTI0YzkyYzBjMWU2MzJjMjFkNGY5MDVjIn19fQ==")
                .setDisplayName(GUITags.Back.getMessage()).setLore(GUITags.Info_Click_Lore.getMessage()).getItem(), () -> {
            new UpgradeGUI(player, faction).open();
        }));

    }

    private String nextValueLore(Faction faction, String replace) {
        String lore = "";

        if(faction.getNextLevel() > 0) {
            lore = GUITags.Upgrades_Lore_Next_Value.getMessage().replace("%value%", replace);
        } else {
            lore = GUITags.Upgrade_Maxed.getMessage();
        }

        return lore;
    }
}
