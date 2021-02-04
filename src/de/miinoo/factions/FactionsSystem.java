package de.miinoo.factions;

import de.miinoo.factions.adapter.AdapterDependencyMissingException;
import de.miinoo.factions.adapter.FactionsAdapter;
import de.miinoo.factions.adapter.ServerVersion;
import de.miinoo.factions.addon.AddonLoader;
import de.miinoo.factions.addon.AddonRegistry;
import de.miinoo.factions.addon.FactionsAddon;
import de.miinoo.factions.core.command.Command;
import de.miinoo.factions.core.command.CommandRegistry;
import de.miinoo.factions.core.ui.UIs;
import de.miinoo.factions.core.ui.manager.EmptyLockManager;
import de.miinoo.factions.core.ui.manager.GUIManager;
import de.miinoo.factions.commands.FactionCommand;
import de.miinoo.factions.configuration.Messages;
import de.miinoo.factions.configuration.configurations.*;
import de.miinoo.factions.factionchest.FactionChest;
import de.miinoo.factions.hooks.bstats.Metrics;
import de.miinoo.factions.hooks.placeholderapi.FactionPlaceholders;
import de.miinoo.factions.listener.*;
import de.miinoo.factions.model.*;
import de.miinoo.factions.quest.Quest;
import de.miinoo.factions.quest.QuestDescription;
import de.miinoo.factions.quest.QuestReward;
import de.miinoo.factions.quest.QuestType;
import de.miinoo.factions.quest.rewardtypes.MoneyReward;
import de.miinoo.factions.quest.types.KillQuest;
import de.miinoo.factions.region.Cuboid;
import de.miinoo.factions.region.Flag;
import de.miinoo.factions.region.Region;
import de.miinoo.factions.shop.ShopCategory;
import de.miinoo.factions.shop.ShopItem;
import de.miinoo.factions.updatechecker.UpdateChecker;
import de.miinoo.factions.util.RegionUtil;
import de.miinoo.factions.util.ResourceUtil;
import de.miinoo.factions.util.TopUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import uk.antiperson.stackmob.StackMob;
import uk.antiperson.stackmob.api.EntityManager;

import java.io.File;
import java.util.*;

/**
 * @author Mino
 * 09.04.2020
 */
public class FactionsSystem extends JavaPlugin implements AddonRegistry, CommandRegistry {

    private static FactionsSystem plugin;
    private static Factions factions;
    private static Regions regions;
    private static Messages messages;
    private static SettingsConfiguration settings;
    private static FactionLevelConfiguration factionLevels;
    private static BankConfiguration bank;
    private static ShopConfiguration shop;
    private static Economy economy;
    private static Random random;
    private static RegionUtil regionUtil;
    private static ServerVersion version;
    private static FactionChestsConfiguration chestsConfiguration;
    private static ScoreboardConfiguration scoreboardConfiguration;
    private static Quests quests;

    private final Map<String, Command> commands = new HashMap<>();

    public static FactionsAdapter adapter;
    public static boolean isPlaceHolderAPIFound = false;
    public static boolean isDynMapFound = false;
    public static boolean isStackMobFound = false;

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
    public static ShopConfiguration getShopConfiguration() {
        return shop;
    }
    public static Economy getEconomy() {
        return economy;
    }
    public static RegionUtil getRegionUtil() {
        return regionUtil;
    }
    public static FactionsAdapter getAdapter() {
        return adapter;
    }
    public static Random getRandom() {
        return random;
    }
    public static FactionLevelConfiguration getFactionLevels() {
        return factionLevels;
    }
    public static FactionChestsConfiguration getChestsConfiguration() { return chestsConfiguration; }
    public static ScoreboardConfiguration getScoreboardConfiguration() {
        return scoreboardConfiguration;
    }
    public static Quests getQuests() { return quests; }

    private Command command;

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
        ConfigurationSerialization.registerClass(Flag.class);
        ConfigurationSerialization.registerClass(Cuboid.class);
        ConfigurationSerialization.registerClass(FactionLevel.class);
        ConfigurationSerialization.registerClass(ShopItem.class);
        ConfigurationSerialization.registerClass(ShopCategory.class);
        ConfigurationSerialization.registerClass(FactionChest.class);
        ConfigurationSerialization.registerClass(Quest.class);
        ConfigurationSerialization.registerClass(QuestDescription.class);
        ConfigurationSerialization.registerClass(KillQuest.class);
        ConfigurationSerialization.registerClass(QuestType.class);
        ConfigurationSerialization.registerClass(QuestReward.class);
        ConfigurationSerialization.registerClass(MoneyReward.class);
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

        checkUpdate();

        // Load Vault economy System
        try {
            setupEconomy();
        } catch (NoClassDefFoundError e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&4[UltimateFactions] Vault not found! Please install Vault to use this plugin!"));
            Bukkit.getPluginManager().disablePlugin(this);
        }

        // Load GUI System
        UIs.load(this, new GUIManager(), new EmptyLockManager());

        // Config creation
        messages = new Messages(this, "messages.yml", "/language");

        File file = new File(getDataFolder(), "settings.yml");
        ResourceUtil.createAndWrite("/settings.yml", file);
        settings = new SettingsConfiguration(file);

        file = new File(getDataFolder(), "faction_levels.yml");
        ResourceUtil.createAndWrite("/faction_levels.yml", file);
        factionLevels = new FactionLevelConfiguration(file);

        file = new File(getDataFolder(), "scoreboard.yml");
        ResourceUtil.createAndWrite("/scoreboard.yml", file);
        scoreboardConfiguration = new ScoreboardConfiguration(file);

        bank = new BankConfiguration(this, "bank.yml", "");
        bank.initFile();

        shop = new ShopConfiguration();

        factions = new Factions(this);
        regions = new Regions(this);
        quests = new Quests(this);

        regionUtil = new RegionUtil();

        chestsConfiguration = new FactionChestsConfiguration();

        // PlaceHolderAPI register method
        registerPlaceholders();

        // register listener and commands
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
        Bukkit.getPluginManager().registerEvents(new FillListener(), this);
        Bukkit.getPluginManager().registerEvents(new FactionUpgradeListener(), this);

        registerCommand(command = new FactionCommand());

        // Faction stuff
        startRegenCount();
        updateFactionTop();

        for (Faction faction : factions.getFactions()) {
            faction.startEnergyTask();
            if (faction.getTownHall() == null) {
                continue;
            }
            faction.getTownHall().startMoveTask();
        }

        // Random for randomteleport
        random = new Random();

        // Other Hooks
        loadBStats();

        // Loading Addons
        loadAddons();

        // starting count for the potion upgrades
        startPotionEffectCount();

    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.closeInventory();
        }
    }

    private void loadAddons() {
        Collection<FactionsAddon> addons = AddonLoader.loadAddons(new File(getDataFolder(), "/addons"));
        addons.forEach(addon -> {
            addon.onEnable(FactionsSystem.this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[FactionsAddon] loaded " + addon.getName()));
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    addons.forEach(addon -> {
                        addon.onPostEnable(FactionsSystem.this);
                    });
                } catch (NoClassDefFoundError ex) {
                    try {
                        throw new AdapterDependencyMissingException();
                    } catch (AdapterDependencyMissingException e) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Error loading an addon"));
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You are missing a plugin that the addon needs to work!"));
                    }
                }
            }
        }.runTask(this);
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

    private void startPotionEffectCount() {
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                i++;
                if(i == 5) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        Faction faction = FactionsSystem.getFactions().getFaction(player);
                        if(faction != null) {
                            if(FactionsSystem.getFactionLevels().isEffectsEnabled(faction.getLevel())) {
                                for(FactionChunk factionChunk : faction.getClaimed()) {
                                    if(player.getLocation().getChunk().equals(factionChunk.getBukkitChunk())) {
                                        for(String effect : FactionsSystem.getFactionLevels().getEffects(faction.getLevel())) {
                                            PotionEffectType potionType = PotionEffectType.getByName(effect);
                                            if(potionType != null) {
                                                player.addPotionEffect(new PotionEffect(potionType, 200, 0));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    i = 0;
                }
            }
        }.runTaskTimer(this, 20, 20);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    private void registerPlaceholders() {
        Plugin placeholderapi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        if (placeholderapi == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&4[UltimateFactions] PlaceHolderAPI not found! Please install the plugin"));
            return;
        }
        new FactionPlaceholders().register();
        isPlaceHolderAPIFound = true;
    }

    private void checkUpdate() {
        new UpdateChecker(getPlugin(), 81103).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&a[UltimateFactions] You are running the newest version!"));
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&4[UltimateFactions] There is a new update available! Please download the newer version!"));
            }
        });
    }

    private void updateFactionTop() {
        TopUtil.calculate();
        TopUtil.startUpdateTask();
    }

    private void loadBStats() {
        Metrics metrics = new Metrics(getPlugin(), 9794);
    }

    @Override
    public void registerFactionsCommand(Command command) {
        this.command.addCommand(command);
    }

    @Override
    public void registerCommand(Command command) {
        if (command != null) {
            commands.put(command.getName().toLowerCase(), command);
        }
    }

    public void registerEvents(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
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
