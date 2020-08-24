package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.DependGUIItem;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.api.ui.ui.UIElement;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.util.TimeUtil;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Mino
 * 14.04.2020
 */
public class FactionInfoGUI extends GUI {

    private final BukkitTask updater;

    public FactionInfoGUI(Player player, Faction faction) {
        super(player, "Info: " + faction.getName(), 27);

        UIElement element;
        int slot;

        if (faction.getPlayers().contains(player.getUniqueId())) {
            addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

            addElement(9, new GUIItem(Items.createSkull(player.getName()).setDisplayName(GUITags.Info_Members.getMessage()).setLore(GUITags.Info_Click_Lore.getMessage()).getItem(), () -> {
                new PlayersGUI(player, faction).open();
            }));

            addElement(slot = 11, element = new DependGUIItem(() -> Items.createItem(XMaterial.OAK_SIGN.parseMaterial()).setDisplayName(GUITags.Info_All.getMessage())
                    .setLore(GUITags.Info_All_Name.getMessage().replace("%name%", faction.getName()),
                            GUITags.Info_All_Description.getMessage().replace("%description%", faction.getDescription()),
                            GUITags.Info_All_Members.getMessage().replace("%count%", "" + faction.getPlayers().size()),
                            GUITags.Info_All_Power.getMessage().replace("%count%", "" + faction.getPower() + "§8 / §e" + faction.getPowerCap()),
                            GUITags.Info_All_Claims.getMessage().replace("%count%", "" + (faction.getClaimed().isEmpty() ? "0" : "" + faction.getClaimed().size()))
                                    .replace("%maxclaims%", "" + FactionsSystem.getSettings().getMaxClaims()),
                            GUITags.Info_Claim_Energy.getMessage().replace("%count%", "" + TimeUtil.convertSeconds(faction.getEnergy()))).getItem()));

            addElement(13, new GUIItem(Items.createItem(XMaterial.MUSHROOM_STEW.parseMaterial()).setDisplayName(GUITags.Info_WarPieces.getMessage()).setLore(GUITags.Info_WarPieces_Lore.getMessage()).getItem(), () -> {
                new WarPiecesGUI(player, faction).open();
            }));

            addElement(15, new GUIItem(Items.createItem(XMaterial.DIAMOND_CHESTPLATE.parseMaterial()).setDisplayName(GUITags.Info_Ranks.getMessage()).setLore(GUITags.Info_Click_Lore.getMessage()).getItem(), () -> {
                new RanksGUI(player, faction).open();
            }));

            addElement(17, new GUIItem(Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName(GUITags.Info_Relations.getMessage()).getItem(), () -> {
                new RelationsGUI(player, faction).open();
            }));

        } else {
            addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

            addElement(slot = 12, element = new DependGUIItem(() -> Items.createItem(XMaterial.OAK_SIGN.parseMaterial()).setDisplayName(GUITags.Info_All.getMessage())
                    .setLore(GUITags.Info_All_Name.getMessage().replace("%name%", faction.getName()),
                            GUITags.Info_All_Description.getMessage().replace("%description%", faction.getDescription()),
                            GUITags.Info_All_Members.getMessage().replace("%count%", "" + faction.getPlayers().size()),
                            GUITags.Info_All_Power.getMessage().replace("%count%", "" + faction.getPower() + "§8 / §e" + faction.getPowerCap()),
                            GUITags.Info_All_Claims.getMessage().replace("%count%", "" + (faction.getClaimed().isEmpty() ? "0" : "" + faction.getClaimed().size()))
                                    .replace("%maxclaims%", "" + FactionsSystem.getSettings().getMaxClaims()),
                            GUITags.Info_Claim_Energy.getMessage().replace("%count%", "" + TimeUtil.convertSeconds(faction.getEnergy()))).getItem()));

            addElement(14, new GUIItem(Items.createItem(XMaterial.PAPER.parseMaterial()).setDisplayName(GUITags.Info_Relations.getMessage()).getItem(), () -> {
                new RelationsGUI(player, faction).open();
            }));

        }

        updater = new BukkitRunnable() {
            @Override
            public void run() {
                build(slot, element);
            }
        }.runTaskTimer(FactionsSystem.getPlugin(), 20, 20);
    }

    @Override
    protected void onClose(Player player) {
        updater.cancel();
    }
}