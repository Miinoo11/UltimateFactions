package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.core.command.ArgumentParser;
import de.miinoo.factions.core.command.CommandDescription;
import de.miinoo.factions.core.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.shop.gui.ShopMainGUI;
import org.bukkit.entity.Player;

public class ShopCommand extends PlayerCommand {

    public ShopCommand() {
        super("shop", new CommandDescription(OtherMessages.Help_ShopCommand.getMessage()));
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {

        if(!FactionsSystem.getSettings().enableShop()) {
            player.sendMessage(ErrorMessage.Shop_Enabled_Error.getMessage());
            return true;
        }

        new ShopMainGUI(player).open();
        return true;
    }
}
