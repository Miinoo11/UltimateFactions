package de.miinoo.factions.commands.subcommands;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.api.command.ArgumentParser;
import de.miinoo.factions.api.command.CommandDescription;
import de.miinoo.factions.api.command.PlayerCommand;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.model.RankPermission;
import de.miinoo.factions.shop.gui.ShopMainGUI;
import org.bukkit.entity.Player;

public class ShopCommand extends PlayerCommand {

    public ShopCommand() {
        super("shop", new CommandDescription("Opens a Shop GUI"));
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
