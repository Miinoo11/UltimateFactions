package de.miinoo.factions.core.command;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.ErrorMessage;
import de.miinoo.factions.model.Faction;
import de.miinoo.factions.model.RankPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Command {

    private final String name;
    protected final CommandDescription description;
    protected RankPermission permission;
    private Command parent;
    private final Map<String, Command> commands;

    protected Command(String name, CommandDescription description, RankPermission permission) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        commands = new LinkedHashMap<>();
    }

    protected Command(String name, CommandDescription description) {
        this(name, description, null);
    }

    protected Command(String name, RankPermission permission) {
        this(name, null, permission);
    }

    protected Command(String name) {
        this(name, null, null);
    }

    public String getName() {
        return name;
    }

    public CommandDescription getDescription() {
        return description;
    }

    public final Command getParent() {
        return parent;
    }

    public final Collection<Command> getCommands() {
        return commands.values();
    }

    public String toDescriptionString(CommandSender sender) {
        String help = "§c" + toString();
        if (description != null) {
            String syntax = description.getSyntax();
            if (syntax != null && (description.getSyntaxPermission() == null || sender.hasPermission(description.getSyntaxPermission()))) {
                help += "§e" + syntax + " ";
            }
            String d = description.getDescription();
            if (d != null) {
                help += "§8- §7" + d;
            }
        }
        return help;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("/");
        Command command = this;
        do {
            result.insert(1, command.getName() + " ");
        } while ((command = command.getParent()) != null);
        return result.toString();
    }

    public final void addCommand(Command command) {
        if (command.parent != null) {
            return;
        }
        command.parent = this;
        commands.put(command.getName().toLowerCase(), command);
    }

    public final void execute(CommandSender sender, String[] args) {
        ArgumentParser parser = new ArgumentParser(args);
        if (parser.hasAtLeast(1)) {
            String name = parser.get(0).toLowerCase();
            if (commands.containsKey(name)) {
                int length = parser.length() - 1;
                String[] a = new String[length];
                System.arraycopy(args, 1, a, 0, length);
                commands.get(name).execute(sender, a);
                return;
            }
        }
        Faction faction;
        if (permission != null && sender instanceof Player && (faction = FactionsSystem.getFactions().getFaction((Player) sender)) != null &&
                !faction.hasPermission(((Player) sender).getUniqueId(), permission)) {
            sender.sendMessage(ErrorMessage.Player_Not_Permitted.getMessage());
        } else if (!execute(sender, parser)) {
            sender.sendMessage(toDescriptionString(sender));
        }
    }

    public abstract boolean execute(CommandSender sender, ArgumentParser args);

    public final List<String> complete(CommandSender sender, String[] args) {
        ArgumentParser parser = new ArgumentParser(args);
        List<String> complete = new ArrayList<>();
        if (args.length > 1) {
            String name = parser.get(0).toLowerCase();
            if (commands.containsKey(name)) {
                int length = parser.length() - 1;
                String[] a = new String[length];
                System.arraycopy(args, 1, a, 0, length);
                return commands.get(name).complete(sender, a);
            }
        } else {
			if (permission != null && sender instanceof Player) {
				Faction faction = FactionsSystem.getFactions().getFaction((Player) sender);
				if(faction != null) {
					complete.addAll(commands.values().stream()
							.filter(command -> command.permission == null || faction.hasPermission(((Player) sender).getUniqueId(), command.permission))
							.map(command -> command.getName()).collect(Collectors.toList()));
				}
			} else {
				complete.addAll(commands.values().stream().map(command -> command.getName()).collect(Collectors.toList()));
			}
		}
        complete.addAll(complete(sender, parser));
        return complete.stream().filter(str -> str.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<String> complete(CommandSender sender, ArgumentParser args) {
        return new ArrayList<>();
    }

}