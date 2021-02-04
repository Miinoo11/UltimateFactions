package de.miinoo.factions.addon;

import de.miinoo.factions.core.command.Command;
import org.bukkit.event.Listener;

public interface AddonRegistry {

    void registerFactionsCommand(Command command);

    void registerEvents(Listener listener);

}
