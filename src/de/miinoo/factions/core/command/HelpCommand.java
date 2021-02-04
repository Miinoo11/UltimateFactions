package de.miinoo.factions.core.command;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.configuration.messages.OtherMessages;
import de.miinoo.factions.model.Faction;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mino
 * 05.05.2020
 */
public class HelpCommand extends PlayerCommand {

    private final String title;
    private final int perPage;

    public HelpCommand(String title, int perPage) {
        super("help", new CommandDescription(OtherMessages.Help_HelpCommand.getMessage(), OtherMessages.Help_HelpCommandSyntax.getMessage()));
        this.title = title;
        this.perPage = perPage;
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        int page = 1;
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        List<Command> commands = new ArrayList<>(getParent().getCommands()).stream().filter(command -> command.permission == null || (faction != null && faction.hasPermission(player.getUniqueId(), command.permission))).collect(Collectors.toList());
        if (args.hasAtLeast(1) && args.isInt(0)) {
            if (commands.size() / perPage < (page = args.getInt(0))) {
                player.sendMessage(ErrorMessage.Help_Error.getMessage());
                return true;
            }
        }
        player.sendMessage("");
        player.sendMessage(OtherMessages.Help_HelpCommandHeader.getMessage()
                .replace("%title%", FactionsSystem.getPlugin().getDescription().getName())
                .replace("%page%", String.valueOf(page))
                .replace("%maxpages%", String.valueOf((commands.size() / perPage))));
        //player.sendMessage("§8§m               §r§8[§c " + title + "§8 (§e" + page + "§7/§e" + commands.size() / perPage + "§8) ]§m               ");
        player.sendMessage("");
        for (int i = (page - 1) * perPage; i < perPage * page && i < commands.size(); i++) {
            player.sendMessage(commands.get(i).toDescriptionString(player));
        }
        player.sendMessage("");
        return true;
    }
}
