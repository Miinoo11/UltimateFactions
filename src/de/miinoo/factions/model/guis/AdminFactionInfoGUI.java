package de.miinoo.factions.model.guis;

import de.miinoo.factions.core.item.Items;
import de.miinoo.factions.core.ui.gui.GUI;
import de.miinoo.factions.core.ui.gui.GUIArea;
import de.miinoo.factions.core.ui.gui.GUIItem;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.ui.input.AnvilInput;
import de.miinoo.factions.core.ui.input.GUIInput;
import de.miinoo.factions.hooks.xseries.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.events.FactionChangeDescriptionEvent;
import de.miinoo.factions.events.FactionChangeNameEvent;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.util.AdminUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Mino
 * 03.05.2020
 */
public class AdminFactionInfoGUI extends GUI {

    private Factions factions = FactionsSystem.getFactions();

    public AdminFactionInfoGUI(Player player, Faction faction, GUI gui) {
        super(player, "Faction: " + faction.getName(), 45);

        addElement(0, new GUIArea(9, 5).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(9, new GUIItem(Items.createItem(XMaterial.DIAMOND.parseMaterial()).setDisplayName(GUITags.Admin_Get_Permissions.getMessage()).getItem(), () -> {
            if (AdminUtil.bypassAllMode.contains(player)) {
                player.sendMessage(ErrorMessage.AdminMode_Enter_Error.getMessage());
                close();
                return;
            }
            if (!AdminUtil.adminMode.contains(player)) {
                AdminUtil.adminMode.add(player);
                new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName(GUITags.Confirm_Description.getMessage()).setLore(GUITags.Admin_Get_Permissions_Lore.getMessage()).getItem(), () -> {
                    faction.addPlayer(player.getUniqueId(), faction.getHighestRank());
                    player.sendMessage(SuccessMessage.Successfully_Entered_AdminMode.getMessage());
                    close();
                }).open();
            } else {
                AdminUtil.adminMode.remove(player);
                faction.removePlayer(player.getUniqueId());
                player.sendMessage(SuccessMessage.Successfully_Left_AdminMode.getMessage());
                close();
            }
        }));

        addElement(13, new GUIItem(Items.createItem(XMaterial.ENDER_PEARL.parseMaterial())
                .setDisplayName(GUITags.Admin_Warps.getMessage())
                .setLore(GUITags.Admin_Warps_Lore.getMessage()).getItem(), () -> new AdminWarpsGUI(player, faction, faction.getWarps(), this).open()));

        addElement(17, new GUIItem(Items.createItem(XMaterial.TNT.parseMaterial()).setDisplayName(GUITags.Admin_Disband_Faction.getMessage()).getItem(), () -> {
            new ConfirmationGUI(player, Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName(GUITags.Confirm_Description.getMessage()).setLore(GUITags.Admin_Disband_Faction_Lore.getMessage()).getItem(), () -> {
                factions.removeFaction(faction);
                factions.removeAllChunks(faction);
                player.sendMessage(SuccessMessage.Successfully_Disbanded.getMessage().replace("%faction%", faction.getName()));

                if (faction.townHallExists() && Bukkit.getEntity(faction.getTownHall().getEntityUUID()) != null) {
                    Bukkit.getEntity(faction.getTownHall().getEntityUUID()).remove();
                }


                for (UUID uuid : faction.getEnemyRelation()) {
                    Faction enemy = factions.getFaction(uuid);
                    factions.removeRelation(faction, enemy.getId());
                    factions.removeRelation(enemy, faction.getId());
                    factions.saveFaction(enemy);
                }

                for (UUID allyID : faction.getAlliesRelation()) {
                    Faction ally = factions.getFaction(allyID);
                    factions.removeRelation(faction, ally.getId());
                    factions.removeRelation(ally, faction.getId());
                    factions.saveFaction(ally);
                }

                for (UUID truceID : faction.getTrucesRelation()) {
                    Faction truce = factions.getFaction(truceID);
                    factions.removeRelation(faction, truce.getId());
                    factions.removeRelation(truce, faction.getId());
                    factions.saveFaction(truce);
                }
            }).open();
        }));

        addElement(20, new GUIItem(Items.createItem(XMaterial.NAME_TAG.parseMaterial()).setDisplayName(GUITags.Admin_Faction_Change_Name.getMessage()).getItem(),
                () -> GUIInput.get(player, faction.getName(), input -> {

                    if(factions.exists(input)) {
                        player.sendMessage(ErrorMessage.Faction_Already_Exists.getMessage());
                        close();
                        return;
                    }

                    if(FactionsSystem.getSettings().onlyCharacter()) {
                        if(!input.matches("[A-Za-z0-9]+")) {
                            player.sendMessage(ErrorMessage.Create_Error_Alphanumeric.getMessage());
                            close();
                            return;
                        }
                    }

                    if(input.length() > FactionsSystem.getSettings().getFactionNameMaximalLength()) {
                        player.sendMessage(ErrorMessage.Create_Error_Length.getMessage());
                        close();
                        return;
                    }

                    Bukkit.getPluginManager().callEvent(new FactionChangeNameEvent(player, faction, faction.getName(), input));
                    faction.setName(input);
                    factions.saveFaction(faction);
                    player.sendMessage(SuccessMessage.Successfully_Set_Name.getMessage().replace("%faction%", faction.getName()));
                    FactionsSystem.getAdapter().sendScoreboard(player);
                    close();
                }, AnvilInput.class)));

        addElement(24, new GUIItem(Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName(GUITags.Admin_Faction_Change_Desc.getMessage()).getItem(),
                () -> GUIInput.get(player, faction.getDescription(), input -> {
                    Bukkit.getPluginManager().callEvent(new FactionChangeDescriptionEvent(player, faction, faction.getDescription(), input));
                    faction.setDescription(input);
                    factions.saveFaction(faction);
                    player.sendMessage(SuccessMessage.Successfully_Set_Description.getMessage()
                            .replace("%faction%", faction.getName()).replace("%description%", input));
                    close();
                }, AnvilInput.class)));

        addElement(getInventory().getSize() - 5, new GUIItem(Items.createItem(XMaterial.VILLAGER_SPAWN_EGG.parseMaterial())
                .setDisplayName(GUITags.Admin_TownHall.getMessage())
                .setLore(GUITags.Admin_TownHall_Lore.getMessage()).getItem(), () -> new AdminTownHallGUI(player, faction, this).open()));

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));

    }

}
