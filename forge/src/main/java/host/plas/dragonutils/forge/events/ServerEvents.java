package host.plas.dragonutils.forge.events;

import host.plas.dragonutils.DragonUtilsAPI;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonUtilsAPI.MOD_ID)
public class ServerEvents {
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        DragonUtilsAPI.onServerStart(event.getServer());
    }
}
