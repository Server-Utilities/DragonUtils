package host.plas.dragonutils.events.own.chat;

import host.plas.dragonutils.events.DragonEvent;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

@Getter @Setter
public class AsyncChatEvent extends DragonEvent {
    private MinecraftServer server;
    private ServerPlayer player;
    private ServerboundChatPacket packet;
    private String message;

    public AsyncChatEvent(MinecraftServer server, ServerPlayer player, ServerboundChatPacket packet) {
        this.server = server;
        this.player = player;
        this.packet = packet;
        this.message = packet.message();
    }
}
