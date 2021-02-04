package de.miinoo.factions.core.command;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.entity.Player;

public class ContainerPlayerCommand extends PlayerCommand {

    protected ContainerPlayerCommand(String name, CommandDescription description, RankPermission permission) {
        super(name, description, permission);
    }

    protected ContainerPlayerCommand(String name, CommandDescription description) {
        super(name, description);
    }

    protected ContainerPlayerCommand(String name, RankPermission permission) {
        super(name, permission);
    }

    protected ContainerPlayerCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(Player player, ArgumentParser args) {
        Faction faction = FactionsSystem.getFactions().getFaction(player);
        player.sendMessage("");
        player.sendMessage("§8§m               §r§8[§c " + getName() + " §8]§m               ");
        player.sendMessage("");
        for (Command command : getCommands()) {
            if (command.permission == null || (faction != null && faction.hasPermission(player.getUniqueId(), command.permission))) {
                player.sendMessage(command.toDescriptionString(player));
            }
        }
        player.sendMessage("");
        return true;
    }

}
