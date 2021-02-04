package de.miinoo.factions.model.guis;

import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.GUI;
import de.miinoo.factions.core.ui.gui.GUIArea;
import de.miinoo.factions.core.ui.gui.GUIItem;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mino
 * 14.04.2020
 */
public class RelationsGUI extends GUI {

    public RelationsGUI(Player player, Faction faction, GUI gui) {
        super(player, "Relations: " + faction.getName(), 27);

        List<String> trucesList = new ArrayList<>();
        List<String> trucesNotFound = new ArrayList<>();
        trucesNotFound.add("§7No truces found!");
        for (UUID uuid : faction.getTrucesRelation()) {
            Faction truceFac = FactionsSystem.getFactions().getFaction(uuid);
            String s = truceFac.getName();
            s = "§8- §7" + s;
            trucesList.add(s);
        }

        List<String> alliesList = new ArrayList<>();
        List<String> alliesNotFound = new ArrayList<>();
        alliesNotFound.add("§7No allies found!");
        for (UUID uuid : faction.getAlliesRelation()) {
            Faction allyFac = FactionsSystem.getFactions().getFaction(uuid);
            String s = allyFac.getName();
            s = "§8- §7" + s;
            alliesList.add(s);
        }

        List<String> enemiesList = new ArrayList<>();
        List<String> enemiesNotFound = new ArrayList<>();
        enemiesNotFound.add("§7No enemies found!");
        for (UUID uuid : faction.getEnemyRelation()) {
            Faction enemyFac = FactionsSystem.getFactions().getFaction(uuid);
            String s = enemyFac.getName();
            s = "§8- §7" + s;
            enemiesList.add(s);
        }

        addElement(0, new GUIArea(9, 3).
                fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(11, new GUIItem(Items.createItem(XMaterial.MUSHROOM_STEW.parseMaterial()).
                setDisplayName(GUITags.Info_Truces.getMessage()).setLore(faction.getTrucesRelation().isEmpty() ? trucesNotFound : trucesList).getItem()));

        addElement(13, new GUIItem(Items.createItem(XMaterial.MUSHROOM_STEW.parseMaterial()).setDisplayName(GUITags.Info_Allies.getMessage()).
                setLore(faction.getAlliesRelation().isEmpty() ? alliesNotFound : alliesList).getItem(), () -> {
            if (faction.hasPermission(player.getUniqueId(), RankPermission.ALLY)) {
                new AlliesGUI(player, faction, this).open();
            }
        }));

        addElement(15, new GUIItem(Items.createItem(XMaterial.MUSHROOM_STEW.parseMaterial()).setDisplayName(GUITags.Info_Enemies.getMessage()).
                setLore(faction.getEnemyRelation().isEmpty() ? enemiesNotFound : enemiesList).getItem()));

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
