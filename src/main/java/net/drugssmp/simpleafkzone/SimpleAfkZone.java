package net.drugssmp.simpleafkzone;

import lombok.Getter;
import net.drugssmp.simpleafkzone.afkzone.struct.AfkManager;
import net.drugssmp.simpleafkzone.commands.SetupAfkZoneCommand;
import net.drugssmp.simpleafkzone.listeners.GeneralListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public final class SimpleAfkZone extends JavaPlugin {

    public static final Logger LOGGER = Bukkit.getLogger();
    private static SimpleAfkZone instance;

    private AfkManager afkManager;

    public static SimpleAfkZone get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            afkManager = new AfkManager(this);
        }, 3 * 20L);

        getServer().getPluginManager().registerEvents(new GeneralListeners(), this);
        getCommand("setupafkzone").setExecutor(new SetupAfkZoneCommand(this));
    }
}
