package net.drugssmp.simpleafkzone.commands;

import lombok.RequiredArgsConstructor;
import net.drugssmp.simpleafkzone.SimpleAfkZone;
import net.drugssmp.simpleafkzone.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class SetupAfkZoneCommand implements CommandExecutor {
    private final SimpleAfkZone main;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player))
            return false;
        if (!player.hasPermission("simpleafkzone.command.setup")) {
            CC.send(player, "&cYou don't have permission.");
            return false;
        }
        if (args.length == 0) {
            CC.send(player, "&cUse: /setupafkzone <pos1/pos2/spawn>");
            return false;
        }
        switch (args[0]) {
            case "pos1":
                main.getConfig().set("afkzone.locations.pos1", player.getLocation());
                main.saveConfig();
            case "pos2":
                main.getConfig().set("afkzone.locations.pos2", player.getLocation());
                main.saveConfig();
            case "spawn":
                main.getConfig().set("afkzone.locations.spawn", player.getLocation());
                main.saveConfig();
        }
        return true;
    }
}
