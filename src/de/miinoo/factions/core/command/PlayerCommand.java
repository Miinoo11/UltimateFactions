package de.miinoo.factions.core.command;

import java.util.ArrayList;
import java.util.List;

import de.miinoo.factions.model.RankPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends Command {

	protected PlayerCommand(String name, CommandDescription description, RankPermission permission) {
		super(name, description, permission);
	}

	protected PlayerCommand(String name, CommandDescription description) {
		super(name, description);
	}

	protected PlayerCommand(String name, RankPermission permission) {
		super(name, permission);
	}

	protected PlayerCommand(String name) {
		super(name);
	}

	@Override
	public final boolean execute(CommandSender sender, ArgumentParser args) {
		if (sender instanceof Player) {
			return execute((Player) sender, args);
		}
		return true;
	}

	public abstract boolean execute(Player player, ArgumentParser args);

	@Override
	public final List<String> complete(CommandSender sender, ArgumentParser args) {
		if (sender instanceof Player) {
			return complete((Player) sender, args);
		}
		return new ArrayList<>();
	}
	
	public List<String> complete(Player player, ArgumentParser args){
		return new ArrayList<>();
	}
	
}
