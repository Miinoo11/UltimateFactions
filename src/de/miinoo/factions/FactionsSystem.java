package de.miinoo.factions;

import de.miinoo.factions.adapter.FactionsAdapter;
import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.api.command.Command;
import de.miinoo.factions.api.command.CommandRegistry;
import de.miinoo.factions.api.ui.UIs;
import de.miinoo.factions.api.ui.manager.EmptyLockManager;
import de.miinoo.factions.api.ui.manager.GUIManager;
import de.miinoo.factions.api.updatechecker.UpdateChecker;
import de.miinoo.factions.commands.FactionCommand;
import de.miinoo.factions.configuration.Messages;
import de.miinoo.factions.configuration.configurations.BankConfiguration;
import de.miinoo.factions.configuration.configurations.SettingsConfiguration;
import de.miinoo.factions.hooks.placeholderapi.FactionPlaceholders;
import de.miinoo.factions.listener.*;
import de.miinoo.factions.model.*;
import de.miinoo.factions.util.ResourceUtil;
import de.miinoo.factions.util.TopUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Mino
 * 09.04.2020
 */
public class FactionsSystem extends JavaPlugin implements CommandRegistry {

    private static FactionsSystem plugin;
    private static Factions factions;
    private static Regions regions;
    private static Messages messages;
    private static SettingsConfiguration settings;
    private static BankConfiguration bank;
    private static Economy economy;
    private static Random random;

    private static ServerVersion version;
    public static FactionsAdapter adapter;

    public static FactionsSystem getPlugin() {
        return plugin;
    }

    public static Factions getFactions() {
        return factions;
    }

    public static Regions getRegions() {
        return regions;
    }

    public static Messages getMessages() {
        return messages;
    }

    public static SettingsConfiguration getSettings() {
        return settings;
    }

    public static BankConfiguration getBank() {
        return bank;
    }

    public static Economy getEconomy() {
        return economy;
    }

    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(Faction.class);
        ConfigurationSerialization.registerClass(Rank.class);
        ConfigurationSerialization.registerClass(FactionPlayer.class);
        ConfigurationSerialization.registerClass(FactionChunk.class);
        ConfigurationSerialization.registerClass(Relation.class);
        ConfigurationSerialization.registerClass(WarPiece.class);
        ConfigurationSerialization.registerClass(Townhall.class);
        ConfigurationSerialization.registerClass(FactionWarp.class);
        ConfigurationSerialization.registerClass(Region.class);
        ConfigurationSerialization.registerClass(Cuboid.class);
        plugin = this;

        version = ServerVersion.getServerVersion();
        adapter = version.getAdapter();

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "\n" +
                "&b  _    _ _ _   _                 _         ______         _   _                 \n" +
                " | |  | | | | (_)               | |       |  ____|       | | (_)                \n" +
                " | |  | | | |_ _ _ __ ___   __ _| |_ ___  | |__ __ _  ___| |_ _  ___  _ __  ___ \n" +
                " | |  | | | __| | '_ ` _ \\ / _` | __/ _ \\ |  __/ _` |/ __| __| |/ _ \\| '_ \\/ __|\n" +
                " | |__| | | |_| | | | | | | (_| | ||  __/ | | | (_| | (__| |_| | (_) | | | \\__ \\\n" +
                "  \\____/|_|\\__|_|_| |_| |_|\\__,_|\\__\\___| |_|  \\__,_|\\___|\\__|_|\\___/|_| |_|___/\n" +
                "                                                                                \n" +
                "                              &eenabled &cv" + getDescription().getVersion() + " &eby Miinoo_ \n" +
                "                         &athank you for using ultimatefactions!                  \n" +
                "             &cif you find any bug please report them on my discord! discord.gg/4dgT7Pz      "));

        new UpdateChecker(this, 81103).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&a[UltimateFactions] You are running the newest version!"));
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&4[UltimateFactions] There is a new update available! Please download the newer version!\n"
                + "https://www.spigotmc.org/resources/ultimatefactions-unique-faction-plugin-1-8-1-13-1-16-fully-configurable.81103/"));
            }
        });

        try {
            setupEconomy();
        } catch (NoClassDefFoundError e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&4[UltimateFactions] Vault not found! Please install Vault to use this plugin!"));
            Bukkit.getPluginManager().disablePlugin(this);
        }
        UIs.load(this, new GUIManager(), new EmptyLockManager());

        messages = new Messages(this, "messages.yml", "/language");
        File file = new File(getDataFolder(), "settings.yml");
        ResourceUtil.createAndWrite("/settings.yml", file);
        settings = new SettingsConfiguration(file);
        bank = new BankConfiguration(this, "bank.yml", "");

        bank.initFile();
        registerPlaceholders();

        factions = new Factions(this);
        regions = new Regions(this);

        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new BuildListener(), this);
        Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new BucketListener(), this);
        Bukkit.getPluginManager().registerEvents(new TownhallListener(), this);
        Bukkit.getPluginManager().registerEvents(new RegionListener(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportListener(), this);
        Bukkit.getPluginManager().registerEvents(new PistonListener(), this);

        registerCommand(new FactionCommand());

        startRegenCount();
        updateFactionTop();

        for (Faction faction : factions.getFactions()) {
            faction.startEnergyTask();
            if (faction.getTownHall() == null) {
                continue;
            }
            faction.getTownHall().startMoveTask();
        }

        random = new Random();

    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.closeInventory();
        }
    }

    private void startRegenCount() {
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                if (i == settings.getPowerRegenCount()) {
                    for (Faction faction : factions.getFactions()) {
                        if (faction.getPower() <= faction.getPowerCap() - settings.getPowerRegenValue()) {
                            faction.addPower(settings.getPowerRegenValue());
                        } else {
                            faction.setPower(faction.getPowerCap());
                        }
                        factions.saveFaction(faction);
                    }
                    i = 0;
                    FactionsSystem.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(FactionsSystem.getPlugin(), () -> {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            adapter.sendScoreboard(player);
                            if (FactionsSystem.getSettings().enableTablist()) {
                                FactionsSystem.adapter.sendTabListHeaderFooter(player, ColorHelper.colorize(FactionsSystem.getSettings().tabHeader(player)),
                                        ColorHelper.colorize(FactionsSystem.getSettings().tabFooter(player)));
                            }
                        }
                    });
                }
                i++;
            }
        }.runTaskTimerAsynchronously(plugin, 20, 20);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    private void registerPlaceholders() {
        Plugin placeholdersapi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        if (placeholdersapi == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&4[UltimateFactions] PlaceHolderAPI not found! Please install the plugin"));
            return;
            //Bukkit.getPluginManager().disablePlugin(this);
        }
        new FactionPlaceholders().register();
    }

    private void updateFactionTop() {
        TopUtil.calculate();
        TopUtil.startUpdateTask();
    }

    public static Random getRandom() {
        return random;
    }

    @Override
    public void registerCommand(Command command) {
        if (command != null) {
            commands.put(command.getName().toLowerCase(), command);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        String name = command.getName().toLowerCase();
        if (commands.containsKey(name)) {
            commands.get(name).execute(sender, args);
            return true;
        }
        return super.onCommand(sender, command, label, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        String name = command.getName().toLowerCase();
        if (commands.containsKey(name)) {
            return commands.get(name).complete(sender, args);
        }
        return super.onTabComplete(sender, command, alias, args);
    }

}
