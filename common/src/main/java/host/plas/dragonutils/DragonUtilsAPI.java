package host.plas.dragonutils;

import host.plas.dragonutils.configs.LoggingConfig;
import host.plas.dragonutils.configs.MainConfig;
import host.plas.dragonutils.events.MainListener;
import host.plas.dragonutils.ratapi.LuckpermsExpansion;
import host.plas.dragonutils.ratapi.StreamlineExpansion;
import host.plas.dragonutils.scheduling.TaskManager;
import host.plas.dragonutils.text.HexInit;
import host.plas.dragonutils.utils.LoggingUtils;
import lombok.Getter;
import lombok.Setter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import tv.quaint.objects.handling.derived.IModEventable;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

@Getter @Setter
public final class DragonUtilsAPI {
    public static final String MOD_ID = "dragonutils";

    @Getter @Setter
    private static Optional<LuckPerms> lpOptional;

    @Getter @Setter
    private static MinecraftServer server;

    @Getter @Setter
    private static MainConfig config;

    @Getter @Setter
    private static MainListener mainListener;

    @Getter @Setter
    private static IModEventable mainEventable;

    public static void init() {
        setUpEventable(new DragonEventable());

        // do more stuff
    }

    public static void setUpEventable(IModEventable eventable) {
        setMainEventable(eventable);

        getMainEventable().initializeDataFolder();
    }

    public static void onServerStart(MinecraftServer server) {
        try {
            setServer(server); // has to be set before anything else

            tryGetLuckPerms();

            config = new MainConfig();
            LoggingConfig.load(); // Has to be loaded after MainConfig
            LoggingConfig.save();

            TaskManager.init();

            HexInit.init();

            mainListener = new MainListener();

            new StreamlineExpansion();
            new LuckpermsExpansion();
        } catch (Exception e) {
            LoggingUtils.logSystem("Failed to start DragonUtils.");
            LoggingUtils.logSystem(e.getMessage());
            LoggingUtils.logSystem(Arrays.toString(e.getStackTrace()));
        }
    }

    public static void tryGetLuckPerms() {
        try {
            lpOptional = Optional.of(LuckPermsProvider.get());
        } catch (IllegalStateException e) {
            logInfo("Failed to get LuckPerms API.");

            lpOptional = Optional.empty();
        } catch (Exception e) {
            logInfo("Failed to get LuckPerms API for:");
            e.printStackTrace();

            lpOptional = Optional.empty();
        } finally {
            ifLuckPerms(luckPerms -> {
                logInfo("Found and hooked into LuckPerms API.");
            });
        }
    }

    public static void setLuckPerms(LuckPerms luckPerms) {
        lpOptional = Optional.of(luckPerms);
    }

    public static void ifLuckPerms(Consumer<LuckPerms> consumer) {
        lpOptional.ifPresent(consumer);
    }

    public static boolean hasLuckPerms() {
        return getLpOptional().isPresent();
    }

    public static List<ServerPlayer> getOnlinePlayers() {
        return server.getPlayerList().getPlayers();
    }

    public static ConcurrentSkipListMap<String, ServerPlayer> getOnlinePlayersByUUID() {
        ConcurrentSkipListMap<String, ServerPlayer> players = new ConcurrentSkipListMap<>();

        getOnlinePlayers().forEach(player -> {
            players.put(player.getUUID().toString(), player);
        });

        return players;
    }

    public static File getServerDirectory() {
        File dir = System.getProperty("user.dir") != null ? new File(System.getProperty("user.dir")) : new File(".");

        if (! dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    public static File getNodsDirectory() {
        File serverDir = getServerDirectory();
        File modsDir = new File(serverDir, "mods");

        if (! modsDir.exists()) {
            modsDir.mkdirs();
        }

        return modsDir;
    }

    public static File getModDirectory() {
        File modsDir = getNodsDirectory();
        File modDir = new File(modsDir, DragonUtilsAPI.MOD_ID);

        if (! modDir.exists()) {
            modDir.mkdirs();
        }

        return modDir;
    }

    public static void logInfo(String message) {
        LoggingUtils.logInfo(message);
    }

    public static void logWarn(String message) {
        LoggingUtils.logWarn(message);
    }

    public static void logError(String message) {
        LoggingUtils.logError(message);
    }

    public static void logDebug(String message) {
        LoggingUtils.logDebug(message);
    }

    public static void logInfo(StackTraceElement[] stackTraceElements) {
        LoggingUtils.logInfo(stackTraceElements);
    }

    public static void logWarn(StackTraceElement[] stackTraceElements) {
        LoggingUtils.logWarn(stackTraceElements);
    }

    public static void logError(StackTraceElement[] stackTraceElements) {
        LoggingUtils.logError(stackTraceElements);
    }

    public static void logDebug(StackTraceElement[] stackTraceElements) {
        LoggingUtils.logDebug(stackTraceElements);
    }

    public static void logInfo(Throwable throwable) {
        LoggingUtils.logInfo(throwable);
    }

    public static void logWarn(Throwable throwable) {
        LoggingUtils.logWarn(throwable);
    }

    public static void logError(Throwable throwable) {
        LoggingUtils.logError(throwable);
    }

    public static void logDebug(Throwable throwable) {
        LoggingUtils.logDebug(throwable);
    }

    public static void logInfo(String message, Throwable throwable) {
        LoggingUtils.logInfo(message, throwable);
    }

    public static void logWarn(String message, Throwable throwable) {
        LoggingUtils.logWarn(message, throwable);
    }

    public static void logError(String message, Throwable throwable) {
        LoggingUtils.logError(message, throwable);
    }

    public static void logDebug(String message, Throwable throwable) {
        LoggingUtils.logDebug(message, throwable);
    }
}
