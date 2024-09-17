package host.plas.dragonutils.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import host.plas.dragonutils.fabriclike.DragonUtilsFabricLike;

public final class DragonUtilsQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        // Run the Fabric-like setup.
        DragonUtilsFabricLike.init();
    }
}
