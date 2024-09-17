package host.plas.dragonutils.utils;

import host.plas.dragonutils.DragonUtilsAPI;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Map;

public class MessageUtils {
    public static void broadcastMessage(List<ServerPlayer> players, String message, boolean format) {
        players.forEach(player -> {
            player.sendSystemMessage(format ? ColorUtils.colorizeToComponent(message) : Component.literal(message));
        });
    }

    public static void broadcastMessage(List<ServerPlayer> players, String message) {
        broadcastMessage(players, message, true);
    }

    public static void broadcastMessage(Map<String, ServerPlayer> players, String message, boolean format) {
        broadcastMessage(players.values().stream().toList(), message, format);
    }

    public static void broadcastMessage(Map<String, ServerPlayer> players, String message) {
        broadcastMessage(players, message, true);
    }

    public static void broadcastAll(String message, boolean format) {
        broadcastMessage(DragonUtilsAPI.getOnlinePlayersByUUID(), message, format);
    }

    public static void broadcastAll(String message) {
        broadcastAll(message, true);
    }
}
