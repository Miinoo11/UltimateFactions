package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.DependGUIItem;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.ui.ui.UIElement;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.util.TimeUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Mino
 * 13.05.2020
 */
public class EnergyGUI extends GUI {

    private Factions factions = FactionsSystem.getFactions();

    private final BukkitTask updater;
    UIElement element;
    int slot;

    public EnergyGUI(Player player, Faction faction, GUI gui) {
        super(player, "Energy", 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName("§r").getItem())));

        addElement(slot = 11, element = new DependGUIItem(() -> Items.createItem(XMaterial.EMERALD.parseMaterial())
                .setDisplayName(GUITags.Townhall_Energy.getMessage())
                .setLore(GUITags.Townhall_Energy_Lore.getMessage()
                        .replace("%count%", TimeUtil.convertSeconds(faction.getEnergy()))).getItem()));

        addElement(15, new GUIItem(
                Items.createSkull(
                        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkZDIwYmU5MzUyMDk0OWU2Y2U3ODlkYzRmNDNlZmFlYjI4YzcxN2VlNmJmY2JiZTAyNzgwMTQyZjcxNiJ9fX0=")
        .setDisplayName(GUITags.Townhall_Buy_Energy.getMessage()).setLore(GUITags.Townhall_Buy_Energy_Lore.getMessage()
                        .replace("%costs%", Double.toString(faction.getClaimed().size() * FactionsSystem.getSettings().getEnergyMultiplier()))).getItem(), () -> {
                    if(faction.getClaimed().size() > 0) {
                        player.sendMessage(ErrorMessage.Energy_Buy_Error.getMessage());
                        return;
                    }
                    if(!(FactionsSystem.getEconomy().getBalance(player) - (faction.getClaimed().size() * FactionsSystem.getSettings().getEnergyMultiplier()) >= 0)) {
                        player.sendMessage(ErrorMessage.Not_Enough_Money.getMessage());
                        return;
                    }
                    if(faction.getEnergy() <= FactionsSystem.getSettings().getEnergyTimer() * 86400) {
                        faction.addEnergy(86400);
                        factions.saveFaction(faction);
                        FactionsSystem.getEconomy().withdrawPlayer(player, faction.getClaimed().size() * FactionsSystem.getSettings().getEnergyMultiplier());
                        player.sendMessage(SuccessMessage.Successfully_Bought_Energy.getMessage());
                    } else {
                        player.sendMessage(ErrorMessage.Energy_Buy_Error.getMessage());
                    }
        }));

        if(gui != null)
            addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));

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
