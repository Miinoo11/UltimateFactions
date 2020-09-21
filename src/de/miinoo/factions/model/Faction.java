package de.miinoo.factions.model;

import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.configuration.messages.OtherMessages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mino
 * 09.04.2020
 */
public class Faction implements ConfigurationSerializable {

    private final UUID id;
    private String name;
    private FactionLevel level;
    private UUID leader;
    private String description;
    private double power;
    private double powerCap;
    private double bank;
    private Map<UUID, UUID> players;
    private Collection<FactionChunk> claimed;
    private SortedSet<Rank> ranks;
    private List<Relation> relations;
    private List<WarPiece> warPieces;
    private List<String> allyRequests;
    private List<String> truceRequests;
    private long energy;
    private BukkitTask energyTask;
    private Townhall townHall;
    private Map<Material, Integer> bankItems;
    private List<FactionWarp> warps;
    private Map<Material, Integer> placedBlocks;

    public Faction(String name, UUID leader, String description) {
        id = UUID.randomUUID();
        this.name = name;
        this.level = new FactionLevel(0);
        this.leader = leader;
        this.description = description;

        this.power = 0.0D;
        this.powerCap = 35.0D;
        this.bank = 0.0D;
        this.players = new HashMap<>();
        this.claimed = new ArrayList<>();
        this.ranks = new TreeSet<>();
        this.relations = new ArrayList<>();
        this.warPieces = new ArrayList<>();
        this.allyRequests = new ArrayList<>();
        this.truceRequests = new ArrayList<>();
        this.bankItems = new HashMap<>();
        this.warps = new ArrayList<>();
        this.placedBlocks = new HashMap<>();

        ranks.add(Rank.LEADER);
        ranks.add(Rank.GENERAL);
        ranks.add(Rank.LIEUTENANT);
        ranks.add(Rank.MEMBER);
        ranks.add(Rank.RECRUIT);

        startEnergyTask();
    }

    public Faction(Map<String, Object> args) {
        id = UUID.fromString((String) args.get("id"));
        name = (String) args.get("name");
        level = (FactionLevel) args.get("level");
        leader = UUID.fromString((String) args.get("leader"));
        description = (String) args.get("description");
        power = (double) args.get("power");
        powerCap = (double) args.get("powerCap");
        bank = (double) args.get("bank");
        players = new HashMap<>();
        ((Collection<FactionPlayer>) args.get("players")).forEach(player -> players.put(player.getPlayer(), player.getRank()));
        claimed = (Collection<FactionChunk>) args.get("claimed");
        relations = (List<Relation>) args.get("relations");
        ranks = new TreeSet<>((Collection<Rank>) args.get("ranks"));
        warPieces = (List<WarPiece>) args.get("warpieces");
        allyRequests = (List<String>) args.get("allyRequests");
        truceRequests = (List<String>) args.get("truceRequests");
        townHall = (Townhall) args.get("townhall");
        bankItems = new HashMap<>();
        ((Map<String, Long>) args.get("bankItems")).forEach((material, aInt) -> bankItems.put(Material.getMaterial(material), aInt.intValue()));
        warps = (List<FactionWarp>) args.get("warps");
        this.placedBlocks = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level.getLevel();
    }

    public OfflinePlayer getLeader() {
        return Bukkit.getOfflinePlayer(leader);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getPower() {
        return power;
    }

    public void addPower(double amount) {
        power += amount;
    }

    public void removePower(double amount) {
        power -= amount;
    }

    public double getPowerCap() {
        return powerCap;
    }

    public void addPowerCap(double amount) {
        powerCap += amount;
    }

    public void setPowerCap(double amount) {
        powerCap = amount;
    }

    public void removePowerCap(double amount) {
        powerCap -= amount;
    }

    public Collection<FactionChunk> getClaimed() {
        return claimed;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void addRelation(Relation relation) {
        relations.add(relation);
    }

    public Relation getRelation(UUID id) {
        Optional<Relation> optional = relations.stream().filter(r -> r.getId().equals(id)).findFirst();
        return optional.orElse(null);
    }

    public Map<Material, Integer> getPlacedBlocks() {
        return placedBlocks;
    }

    public List<UUID> getAlliesRelation() {
        List<UUID> list = new ArrayList<>();
        relations.stream().filter(relation -> relation.getIdentifier().equalsIgnoreCase("ally")).map(Relation::getId).forEach(list::add);
        return list;
    }

    public List<UUID> getTrucesRelation() {
        List<UUID> list = new ArrayList<>();
        relations.stream().filter(relation -> relation.getIdentifier().equalsIgnoreCase("truce")).map(Relation::getId).forEach(list::add);
        return list;
    }

    public List<UUID> getEnemyRelation() {
        List<UUID> list = new ArrayList<>();
        relations.stream().filter(relation -> relation.getIdentifier().equalsIgnoreCase("enemy")).map(Relation::getId).forEach(list::add);
        return list;
    }

    public boolean relationIsPermitted(Relation relation, RelationPermission permission) {
        return relation.getPermissions().contains(permission);
    }

    public Collection<Rank> getRanks() {
        return ranks;
    }

    public boolean isHigherRank(OfflinePlayer player, OfflinePlayer target) {
        return getRankOfPlayer(player.getUniqueId()).getPermissions().size() > getRankOfPlayer(target.getUniqueId()).getPermissions().size();
    }

    public boolean isHighestRank(OfflinePlayer player) {
        return players.get(player.getUniqueId()).equals(getHighestRank().getId());
    }

    public Rank getHighestRank() {
        return ranks.first();
    }

    public Rank getSecondHighestRank() {
        Rank second = new ArrayList<>(ranks).get(1);
        return second;
    }

    public Rank getLastRank() {
        return ranks.last();
    }

    public void addRank(Rank rank) {
        ranks.add(rank);
    }

    public void removeRank(Rank rank) {
        ranks.remove(rank);
    }

    public Rank getRankOfPlayer(UUID player) {
        Optional<Rank> rank = getRank(players.get(player));
        return rank.orElseGet(this::getLastRank);
    }

    public Optional<Rank> getRank(UUID id) {
        return ranks.stream().filter(rank -> rank.getId().equals(id)).findFirst();
    }

    public Optional<Rank> getRank(String name) {
        Optional<String> rankName = ranks.stream().map(Rank::getName).findAny();
        if (rankName.isPresent()) {
            return ranks.stream().filter(rank -> rank.getName().equalsIgnoreCase(name)).findFirst();
        }
        return null;
    }

    public boolean existRank(String name) {
        return ranks.stream().anyMatch(rank -> rank.getName().equalsIgnoreCase(name));
    }

    //public OfflinePlayer getLeader() {
    //    for (UUID playerUUID : players.keySet()) {
    //        Optional<Rank> optionalRank = getRank(players.get(playerUUID));
    //        if (optionalRank.isPresent()) {
    //            Rank rank = optionalRank.get();
    //            if (rank.getId().equals(getHighestRank().getId())) {
    //                return Bukkit.getOfflinePlayer(playerUUID);
    //            }
    //        }
    //    }
    //    return null;
    //}

    public Collection<UUID> getPlayers() {
        return players.keySet();
    }

    public List<WarPiece> getWarPieces() {
        return warPieces;
    }

    public WarPiece getWarPieces(Faction enemy) {
        Optional<WarPiece> optional = warPieces.stream().filter(warPiece -> warPiece.getUuid().equals(enemy.getId())).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public void addWarPiece(Faction enemy, int amount) {
        WarPiece warPiece = getWarPieces(enemy);
        if (warPiece != null) {
            warPieces.remove(warPiece);
            warPiece.addAmount(amount);
        } else {
            warPiece = new WarPiece(enemy.getId(), amount);
        }
        warPieces.add(warPiece);
    }

    public void removeWarPiece(Faction enemy, int amount) {
        WarPiece warPiece = getWarPieces(enemy);
        if (warPiece != null) {
            warPieces.remove(warPiece);
            warPiece.removeAmount(amount);
        } else {
            warPiece = new WarPiece(enemy.getId(), 0);
        }
        warPieces.add(warPiece);
    }

    public void removeWarPieces(Faction enemy) {
        WarPiece warPiece = getWarPieces(enemy);
        if (warPiece != null) {
            warPieces.remove(warPiece);
        }
    }

    public void setWarPieces(Faction enemy, int amount) {
        WarPiece warPiece = getWarPieces(enemy);
        if (warPiece != null) {
            warPiece.setAmount(amount);
        } else {
            warPiece = new WarPiece(enemy.getId(), 0);
        }
        warPieces.add(warPiece);
    }

    public void addPlayer(UUID player, Rank rank) {
        players.put(player, rank.getId());
    }

    public void addPlayer(UUID player, UUID rank) {
        players.put(player, rank);
    }

    public void removePlayer(UUID player) {
        players.remove(player);
    }

    public boolean hasPermission(UUID player, RankPermission permission) {
        UUID id = players.get(player);
        if (id != null) {
            Optional<Rank> rank = getRank(id);
            if (rank.isPresent()) {
                return rank.get().hasPermission(permission);
            }
        }
        return false;
    }

    public List<String> getAllyRequests() {
        return allyRequests;
    }

    public void addAllyRequest(String uuid) {
        allyRequests.add(uuid);
    }

    public boolean alreadyRequestedAlly(String uuid) {
        return allyRequests.contains(uuid);
    }

    public void removeAllyRequest(String uuid) {
        allyRequests.remove(uuid);
    }

    public List<String> getTruceRequests() {
        return truceRequests;
    }

    public void addTruceRequest(String uuid) {
        truceRequests.add(uuid);
    }

    public boolean alreadyRequestedTruce(String uuid) {
        return truceRequests.contains(uuid);
    }

    public void removeTruceRequest(String uuid) {
        truceRequests.remove(uuid);
    }

    public long getEnergy() {
        return energy;
    }

    public void addEnergy(int amount) {
        energy += amount;
    }

    public void startEnergyTask() {
        stopEnergyTask();
        Faction faction = this;
        int energyTimer = (((FactionsSystem.getSettings().getEnergyTimer() * 60) * 60) * 24);
        energyTask = new BukkitRunnable() {
            int i = energyTimer;

            @Override
            public void run() {
                if (i == energyTimer / 2 || i == energyTimer / 4) {
                    for (UUID uuid : faction.getPlayers()) {
                        Player player = Bukkit.getPlayer(uuid);
                        if (player != null && player.isOnline()) {
                            player.sendMessage(OtherMessages.Energy_Is_Getting_Low.getMessage());
                        }
                    }
                }
                if (i == 0) {
                    stopEnergyTask();
                    for (UUID uuid : faction.getPlayers()) {
                        Player player = Bukkit.getPlayer(uuid);
                        if (player != null && player.isOnline()) {
                            player.sendMessage(OtherMessages.Energy_Limit_Reached.getMessage());
                        }
                    }
                    for (FactionChunk chunk : getClaimed()) {
                        FactionsSystem.getFactions().unClaim(faction, chunk);
                    }
                }
                energy = i;
                i--;
            }
        }.runTaskTimerAsynchronously(FactionsSystem.getPlugin(), 20, 20);
    }

    public void stopEnergyTask() {
        if (energyTask != null && !energyTask.isCancelled()) {
            energyTask.cancel();
        }
    }

    public Townhall getTownHall() {
        return townHall;
    }

    public boolean townHallExists() {
        return townHall != null;
    }

    public void setTownHall(Townhall townHall) {
        this.townHall = townHall;
    }

    public void removeTownHall() {
        townHall.stopMoveTask();
        this.townHall = null;
    }


    public double getBank() {
        return bank;
    }

    public void addBank(double amount) {
        bank += amount;
    }

    public void removeBank(double amount) {
        bank -= amount;
    }

    public Map<Material, Integer> getBankItems() {
        return bankItems;
    }

    public int getBankItemAmount(Material material) {
        return bankItems.get(material);
    }

    public void addBankItem(Material material, int amount) {
        if (!bankItems.containsKey(material)) {
            bankItems.put(material, amount);
        } else {
            int currentAmount = getBankItemAmount(material);
            bankItems.replace(material, currentAmount + amount);
        }
    }

    public boolean removeBankItem(Material material, int amount) {
        if (bankItems.containsKey(material)) {
            int currentAmount = getBankItemAmount(material);
            if (currentAmount - amount >= 0) {
                bankItems.replace(material, (currentAmount - amount));
                return true;
            }
        }
        return false;
    }

    public List<FactionWarp> getWarps() {
        return warps;
    }

    public FactionWarp getFactionWarp(String name) {
        Optional<FactionWarp> optional = warps.stream().filter(warp -> warp.getName().equals(name)).findFirst();
        return optional.orElse(null);
    }

    public void addWarp(String name, String password, Location location) {
        if (!warps.contains(getFactionWarp(name))) {
            warps.add(new FactionWarp(name, password, location));
        }
    }

    public void setHome(Location location) {
        if (warps.contains(getFactionWarp("home"))) {
            removeWarp("home");
            addWarp("home", null, location);
            return;
        }
        addWarp("home", null, location);
    }

    public void removeWarp(String name) {
        warps.remove(getFactionWarp(name));
    }

    public void removeWarp(FactionWarp factionWarp) {
        warps.remove(factionWarp);
    }

    public boolean hasFly() {
        return level.hasFly;
    }

    public boolean hasFill() {
        return level.hasFill;
    }

    public int getNextLevel() {
        return level.nextLevel();
    }

    public void setLevel(FactionLevel level) {
        this.level = level;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", id.toString());
        result.put("name", name);
        result.put("level", level);
        result.put("leader", leader.toString());
        result.put("description", description);
        result.put("power", power);
        result.put("powerCap", powerCap);
        result.put("bank", bank);
        result.put("players", players.entrySet().stream().map(entry -> new FactionPlayer(entry.getKey(), entry.getValue())).collect(Collectors.toList()));
        result.put("claimed", claimed);
        result.put("relations", new ArrayList<>(relations));
        result.put("ranks", new ArrayList<>(ranks));
        result.put("warpieces", new ArrayList<>(warPieces));
        result.put("allyRequests", allyRequests);
        result.put("truceRequests", truceRequests);
        result.put("townhall", townHall);
        result.put("bankItems", bankItems);
        result.put("warps", warps);
        return result;
    }
}
