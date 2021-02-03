package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIElement;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.util.ItemUtil;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;

/**
 * @author Mino
 * 10.05.2020
 */
public class DepositGUI extends GUI {

    private Factions factions = FactionsSystem.getFactions();
    private final BukkitTask updater;
    private final NumberFormat formatter = new DecimalFormat("#,###.00");

    public DepositGUI(Player player, Faction faction, Collection<Material> elements, GUI gui) {
        super(player, "Deposit", 45);

        UIElement element;
        int slot;

        addElement(0, new GUIArea(9, 5).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem())));

        UIList<Material> list = new GUIList<Material>(9, 3, elements, material ->
                new DependGUIItem(() -> Items.createItem(material).setLore(GUITags.Bank_Deposit_1.getMessage()
                                .replace("%price%", "" + FactionsSystem.getBank().getPrice(material)),
                        GUITags.Bank_Deposit_32.getMessage().replace("%price%", formatter.format(FactionsSystem.getBank().getPrice(material) * 32)),
                        GUITags.Bank_Deposit_64.getMessage().replace("%price%", formatter.format(FactionsSystem.getBank().getPrice(material) * 64)),
                        GUITags.Bank_Deposit_All.getMessage().replace("%price%", ItemUtil.getAmount(player.getInventory(), material) > 0 ?
                                formatter.format(ItemUtil.getAmount(player.getInventory(), material) * FactionsSystem.getBank().getPrice(material)) : "0")).getItem()
                ).setOnClickListener((player1, item, event) -> {

                    int amount = 0;
                    switch (event.getClick()) {
                        case LEFT:
                            amount = 1;
                            break;
                        case SHIFT_LEFT:
                            amount = 32;
                            break;
                        case RIGHT:
                            amount = 64;
                            break;
                        case SHIFT_RIGHT:
                            amount = ItemUtil.getAmount(player.getInventory(), material);
                            break;
                        default:
                            return false;
                    }

                    if (ItemUtil.getAmount(player.getInventory(), material) < amount || ItemUtil.getAmount(player.getInventory(), material) == 0) {
                        player.sendMessage(ErrorMessage.Deposit_Error.getMessage());
                        return true;
                    }

                    faction.addBankItem(material, amount);
                    faction.addBank((FactionsSystem.getBank().getPrice(material) * amount));
                    factions.saveFaction(faction);
                    ItemUtil.removeItems(player.getInventory(), material, amount);
                    player.sendMessage(SuccessMessage.Successfully_Deposit.getMessage()
                            .replace("%amount%", Integer.toString(amount))
                            .replace("%item%", material.toString()));
                    return false;
                }));

        addElement(slot = 9, element = list);

        if (elements.size() > 18) {
            addElement(size - 6, new GUIScrollBar(GUIScrollBar.HORIZONTAL, 3, list,
                    new GUIItem(Items.createSkull("MHF_ArrowLeft").setDisplayName(GUITags.Previous.getMessage()).getItem()),
                    new GUIItem(Items.createSkull("MHF_ArrowRight").setDisplayName(GUITags.Next.getMessage()).getItem())));
        }

        addElement(getInventory().getSize() - 9, new GUIItem(Items.createBackArrow().setDisplayName(GUITags.Back.getMessage()).getItem(), () -> gui.open()));

        updater = new BukkitRunnable() {
            @Override
            public void run() {
                build(slot, element);
            }
        }.runTaskTimer(FactionsSystem.getPlugin(), 20, 5);
    }

    @Override
    protected void onClose(Player player) {
        updater.cancel();
    }
}
