package net.drugssmp.simpleafkzone.afkzone.struct;

import lombok.Getter;
import net.drugssmp.simpleafkzone.SimpleAfkZone;
import net.drugssmp.simpleafkzone.afkzone.model.AfkZone;
import net.drugssmp.simpleafkzone.utils.CC;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class AfkManager {
    private final SimpleAfkZone main;
    private final AfkZone afkZone;
    private final Set<UUID> players;

    public AfkManager(SimpleAfkZone main) {
        this.main = main;
        this.players = new HashSet<>();
        this.afkZone = new AfkZone();
        startTasks();
    }

    public void giveRewards() {
        players.stream().filter(uuid -> Bukkit.getPlayer(uuid) != null).forEach(uuid ->
                afkZone.getRewards().forEach(reward ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward.replace("%player%", Bukkit.getPlayer(uuid).getName()))));
    }

    public void startTasks() {
        int timeConfig = main.getConfig().getInt("afkzone.time");
        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger time = new AtomicInteger(timeConfig);

        Bukkit.getScheduler().runTaskTimer(main, () -> {
            counter.getAndIncrement();
            time.getAndDecrement();
            Set<UUID> playersToAdd = new HashSet<>();
            Set<UUID> playersToRemove = new HashSet<>();

            Bukkit.getOnlinePlayers().forEach(player -> {
                if (afkZone.getCuboid().isIn(player)) {
                    playersToAdd.add(player.getUniqueId());
                    CC.sendActionBar(player, main.getConfig().getString("afkzone.action_bar.time").replace("%time%", String.valueOf(time.get())));
                } else {
                    playersToRemove.add(player.getUniqueId());
                    CC.sendActionBar(player, "");
                }
            });

            players.addAll(playersToAdd);
            players.removeAll(playersToRemove);

            if (counter.get() >= timeConfig && time.get() <= 0) {
                players.stream().filter(uuid -> Bukkit.getPlayer(uuid) != null).forEach(uuid -> {
                    giveRewards();
                    CC.send(Bukkit.getPlayer(uuid), main.getConfig().getString("afkzone.message"));
                    CC.sendActionBar(Bukkit.getPlayer(uuid), main.getConfig().getString("afkzone.action_bar.reward"));
                });
                counter.set(0);
                time.set(timeConfig);
            }
        }, 0, 20L);
    }
}