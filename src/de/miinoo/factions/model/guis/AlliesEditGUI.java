package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.DependGUIItem;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Mino
 * 21.04.2020
 */
public class AlliesEditGUI extends GUI {

    private Factions factions = FactionsSystem.getFactions();
    private Collection<RelationPermissionValue> permissions;

    public AlliesEditGUI(Player player, Faction faction, Faction ally, GUI gui) {
        super(player, "Ally: " + ally.getName(), 27);


        permissions = RelationPermission.getValues(faction.getRelation(ally.getId()).getPermissions());

        addElement(0, new GUIArea(9, 3).
                fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));
        addElement(10, new GUIItem(Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName("§c" + ally.getName()).getItem(), () -> {
            new FactionInfoGUI(player, ally).open();
        }));

        addElement(13, new GUIItem(Items.createItem(XMaterial.BARRIER.parseMaterial()).setDisplayName(GUITags.Unally.getMessage()).getItem(), () -> {
            factions.removeRelation(faction, ally.getId());
            factions.removeRelation(ally, faction.getId());
            FactionsSystem.getFactions().saveFaction(faction);
            FactionsSystem.getFactions().saveFaction(ally);

            for (UUID uuid : ally.getPlayers()) {
                Player all = Bukkit.getPlayer(uuid);
                if (all != null && all.isOnline()) {
                    all.sendMessage(OtherMessages.Faction_Unallied.getMessage().replace("%faction%", faction.getName()));
                }
            }
            player.sendMessage(SuccessMessage.Successfully_Unallied.getMessage().replace("%faction%", ally.getName()));
            close();
        }));

        addElement(16, new DependGUIItem(() -> Items.createItem(XMaterial.ANVIL.parseMaterial())
                .setDisplayName(GUITags.Edit_Ally_Permission.getMessage())
                .setLore(permissions.stream().filter(RelationPermissionValue::getValue).map(per -> "§8- §7" + per.getPermission().getName()).collect(Collectors.toList())).getItem(),
                () -> {
                    new RelationPermissionsGUI(player, permissions, this).open();
                }));

        addElement(22, new GUIItem(Items.createSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwZjQ1MzdkMjE0ZDM4NjY2ZTYzMDRlOWM4NTFjZDZmN2U0MWEwZWI3YzI1MDQ5YzlkMjJjOGM1ZjY1NDVkZiJ9fX0=")
                .setDisplayName(GUITags.Save.getMessage()).getItem()).setOnClickListener((player1, item, event) -> {

            factions.removeRelation(faction, ally.getId());
            Relation relation = new Relation(ally.getId(), "ally",
                    permissions.stream().filter(RelationPermissionValue::getValue).map(RelationPermissionValue::getPermission).collect(Collectors.toList()));
            faction.addRelation(relation);
            factions.saveFaction(faction);
            player.sendMessage(SuccessMessage.Successfully_Edited_Ally.getMessage());
            close();
            return true;
        }));

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));
    }
}
