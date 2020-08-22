package de.miinoo.factions.api.command;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mino
 * 05.05.2020
 */
public class HelpCommand extends PlayerCommand {

    private final String title;
    private final int perPage;

    public HelpCommand(String title, int perPage) {
        super("help", new CommandDescription("Shows this view", "[page]"));
        this.title = title;
        this.perPage = perPage;
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        int page = 1;
        List<Command> commands = new ArrayList<>(getParent().getCommands());
        if (args.hasAtLeast(1) && args.isInt(0)) {
            if (commands.size() / perPage < (page = args.getInt(0))) {
                player.sendMessage(ErrorMessage.Help_Error.getMessage());
                return true;
            }
        }
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        player.sendMessage("");
        player.sendMessage("§8§m               §r§8[§c " + title + "§8 (§e" + page + "§7/§e" + commands.size() / perPage + "§8) ]§m               ");
        player.sendMessage("");
        for (int i = (page - 1) * perPage; i < perPage * page && i < commands.size(); i++) {
            Command command = commands.get(i);
            if (command.permission == null || (faction != null && faction.hasPermission(player.getUniqueId(), command.permission))) {
                player.sendMessage(command.toDescriptionString(player));
            }
        }
        player.sendMessage("");
        return true;
    }
}
