package host.plas.dragonutils.fabric;

import host.plas.dragonutils.DragonUtilsAPI;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

@Getter @Setter
public final class DragonUtilsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        DragonUtilsAPI.init();

        ServerLifecycleEvents.SERVER_STARTING.register(DragonUtilsAPI::onServerStart);
    }

    public static void logInfo(String message) {
        DragonUtilsAPI.logInfo(message);
    }

    public static void logWarn(String message) {
        DragonUtilsAPI.logWarn(message);
    }

    public static void logError(String message) {
        DragonUtilsAPI.logError(message);
    }

    public static void logDebug(String message) {
        DragonUtilsAPI.logDebug(message);
    }

    public static void logInfo(StackTraceElement[] stackTrace) {
        DragonUtilsAPI.logInfo(stackTrace);
    }

    public static void logWarn(StackTraceElement[] stackTrace) {
        DragonUtilsAPI.logWarn(stackTrace);
    }

    public static void logError(StackTraceElement[] stackTrace) {
        DragonUtilsAPI.logError(stackTrace);
    }

    public static void logDebug(StackTraceElement[] stackTrace) {
        DragonUtilsAPI.logDebug(stackTrace);
    }

    public static void logInfo(Throwable throwable) {
        DragonUtilsAPI.logInfo(throwable);
    }

    public static void logWarn(Throwable throwable) {
        DragonUtilsAPI.logWarn(throwable);
    }

    public static void logError(Throwable throwable) {
        DragonUtilsAPI.logError(throwable);
    }

    public static void logDebug(Throwable throwable) {
        DragonUtilsAPI.logDebug(throwable);
    }

    public static void logInfo(String message, Throwable throwable) {
        DragonUtilsAPI.logInfo(message, throwable);
    }

    public static void logWarn(String message, Throwable throwable) {
        DragonUtilsAPI.logWarn(message, throwable);
    }

    public static void logError(String message, Throwable throwable) {
        DragonUtilsAPI.logError(message, throwable);
    }

    public static void logDebug(String message, Throwable throwable) {
        DragonUtilsAPI.logDebug(message, throwable);
    }
}
