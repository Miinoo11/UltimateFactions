package de.miinoo.factions.model.guis;

import de.miinoo.factions.api.item.Items;
import de.miinoo.factions.api.ui.gui.GUI;
import de.miinoo.factions.api.ui.gui.GUIArea;
import de.miinoo.factions.api.ui.gui.GUIItem;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.xutils.XMaterial;
import de.miinoo.factions.configuration.messages.GUITags;
import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Mino
 * 10.05.2020
 */
public class BankGUI extends GUI {

    private final NumberFormat formatter = new DecimalFormat("#,###.00");

    public BankGUI(Player player, Faction faction) {
        super(player, "Bank", 27);

        addElement(0, new GUIArea(9, 3).fill(new GUIItem(Items.createItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()).setDisplayName(" ").getItem())));

        addElement(11, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWMyNjg3NmE0NTQ4ODQ0ZTI4YTZmN2JhMWYzNzdjODBlNTk0OTVmN2QzMjIxNGJjYzQ5MjgwNGIxNjYxOTMzOSJ9fX0=")
                .setDisplayName(GUITags.Bank_Deposit.getMessage())
                .setLore(GUITags.Info_Click_Lore.getMessage()).getItem(), () -> new DepositGUI(player, faction, FactionsSystem.getBank().getMaterialsCollection()).open()));
        addElement(13, new GUIItem(Items.createItem(XMaterial.OAK_SIGN.parseMaterial())
                .setDisplayName(GUITags.Bank_Info.getMessage())
                .setLore(GUITags.Info_Click_Lore.getMessage(), GUITags.Bank_Info_Lore.getMessage().replace("%amount%",faction.getBank() > 0 ? formatter.format(faction.getBank()) : "0.0")
                ).getItem(), () -> new BankInfoGUI(player, faction, FactionsSystem.getBank().getMaterialsCollection()).open()));
        addElement(15, new GUIItem(Items.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U4OTE0ZGZjYThjMjA5NDMxNzVjMDUzMzMxYWUzNzNhNDhhZjQ1ZWQ1YmQxNTdjODk0OTVjYWU0NmVjOTgifX19")
                .setDisplayName(GUITags.Bank_Withdraw.getMessage())
                .setLore(GUITags.Info_Click_Lore.getMessage()).getItem(), () -> new WithdrawGUI(player, faction, FactionsSystem.getBank().getMaterialsCollection()).open()));

    }
}
