package de.miinoo.factions.addon;

import de.miinoo.factions.api.command.Command;
import de.miinoo.factions.api.command.CommandRegistry;
import org.bukkit.event.Listener;

public interface AddonRegistry {

    void registerFactionsCommand(Command command);

    void registerEvents(Listener listener);

}
