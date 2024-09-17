package host.plas.dragonutils.mixin;

import host.plas.dragonutils.DragonUtilsAPI;
import host.plas.dragonutils.events.own.chat.AsyncChatEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;

    @Shadow @Final private MinecraftServer server;

    @Inject(method = "handleChat(Lnet/minecraft/network/protocol/game/ServerboundChatPacket;)V",
            at = @At("HEAD"), cancellable = true)
    public void handleChat(ServerboundChatPacket serverboundChatPacket, CallbackInfo ci) {
        CompletableFuture.runAsync(() -> dragonUtils$handleChatAsync(serverboundChatPacket));

        ci.cancel();
    }

    @Unique
    public void dragonUtils$handleChatAsync(ServerboundChatPacket serverboundChatPacket) {
        AsyncChatEvent event = new AsyncChatEvent(server, player, serverboundChatPacket).fire();
        if (event.isCancelled()) return;

        MutableComponent component = Component.translatable("chat.type.text", event.getPlayer().getDisplayName(), event.getMessage());
        DragonUtilsAPI.getOnlinePlayers().forEach(p -> p.sendSystemMessage(component));
        DragonUtilsAPI.getServer().sendSystemMessage(component);
    }
}
