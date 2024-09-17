package host.plas.dragonutils.events;

import host.plas.dragonutils.DragonUtilsAPI;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.MinecraftServer;
import tv.quaint.events.components.BaseEvent;

@Getter @Setter
public class DragonEvent extends BaseEvent {
    public static MinecraftServer getServerInstance() {
        return DragonUtilsAPI.getServer();
    }
}
