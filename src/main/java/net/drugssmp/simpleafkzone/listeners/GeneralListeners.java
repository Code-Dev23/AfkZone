package net.drugssmp.simpleafkzone.listeners;

import net.drugssmp.simpleafkzone.SimpleAfkZone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class GeneralListeners implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        SimpleAfkZone.get().getAfkManager().getPlayers().remove(player.getUniqueId());
    }
}
