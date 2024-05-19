package net.drugssmp.simpleafkzone.utils;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

@UtilityClass
public class CC {
    public String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public String color(Player player, String text) {
        return PlaceholderAPI.setPlaceholders(player, color(text));
    }

    public void send(Player player, String message) {
        player.sendMessage(color(player, message));
    }

    public void sendActionBar(Player player, String text) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(color(player, text)));
    }

    public void sendTitle(Player player, String title, String subTitle, int seconds) {
        player.sendTitle(color(player, title), color(player, subTitle), 10, seconds * 20, 10);
    }
}