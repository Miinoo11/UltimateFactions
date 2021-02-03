package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.*;
import de.miinoo.factions.api.ui.ui.UIElement;
import de.miinoo.factions.api.ui.ui.UIList;
import de.miinoo.factions.Factions;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.configuration.messages.SuccessMessage;
import de.miinoo.factions.model.Faction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;

/**
 * @author Mino
 * 12.05.2020
 */
public class WithdrawGUI extends GUI {

    private Factions factions = FactionsSystem.getFactions();
    private final BukkitTask updater;

    public WithdrawGUI(Player player, Faction faction, Collection<Material> elements, GUI gui) {
        super(player, "Withdraw", 45);
        UIElement element;
        int slot;

        addElement(0, new GUIArea(9, 5).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem())));

        UIList<Material> list = new GUIList<Material>(9, 3, elements, material ->
                new DependGUIItem(() -> Items.createItem(material).setLore(
                        GUITags.Bank_Item_Info_Lore.getMessage().replace("%amount%", Integer.toString(formatAmount(faction, material))),
                        GUITags.Bank_Withdraw_1.getMessage(),
                        GUITags.Bank_Withdraw_32.getMessage(),
                        GUITags.Bank_Withdraw_64.getMessage()).getItem()
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
                        default:
                            return false;
                    }

                    if(faction.getBankItems().get(material) == null) {
                        player.sendMessage(ErrorMessage.Withdaw_Error.getMessage());
                        return true;
                    }

                    if (faction.getBankItemAmount(material) == 0 || faction.getBankItemAmount(material) < amount) {
                        player.sendMessage(ErrorMessage.Withdaw_Error.getMessage());
                        return true;
                    }

                    faction.removeBankItem(material, amount);
                    faction.removeBank((FactionsSystem.getBank().getPrice(material) * amount));
                    factions.saveFaction(faction);
                    player.getInventory().addItem(new ItemStack(material, amount));
                    player.sendMessage(SuccessMessage.Successfully_Withdraw.getMessage()
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


    private int formatAmount(Faction faction, Material material) {
        if(faction.getBankItems() == null) {
            return 0;
        }
        if(!faction.getBankItems().containsKey(material)) {
            return 0;
        }
        if(faction.getBankItems().size() == 0) {
            return 0;
        }
        if(faction.getBankItemAmount(material) == 0) {
            return 0;
        }
        return faction.getBankItemAmount(material);
    }
}
