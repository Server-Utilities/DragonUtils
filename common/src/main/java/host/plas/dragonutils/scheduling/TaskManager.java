package host.plas.dragonutils.scheduling;

import host.plas.dragonutils.DragonUtilsAPI;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class TaskManager {
    @Getter @Setter
    private static ConcurrentSkipListMap<Integer, BaseRunnable> currentRunnables = new ConcurrentSkipListMap<>();

    @Getter @Setter
    private static Timer timer;

    public static void start(BaseRunnable runnable) {
        currentRunnables.put(runnable.getIndex(), runnable);
    }

    public static void cancel(BaseRunnable runnable) {
        cancel(runnable.getIndex());
    }

    public static int getNextIndex() {
        return currentRunnables.size();
    }

    public static void tick() {
        for (BaseRunnable runnable : currentRunnables.values()) {
            try {
                runnable.tick();
            } catch (Throwable e) {
                DragonUtilsAPI.logInfo("Error while ticking runnable: " + runnable);
            }
        }
    }

    public static boolean isCancelled(int index) {
        return ! currentRunnables.containsKey(index);
    }

    public static void cancel(int index) {
        currentRunnables.remove(index);
    }

    public static BaseRunnable getRunnable(int index) {
        return currentRunnables.get(index);
    }

    public static void init() {
//        int tickingFrequency = BaseManager.getBaseConfig().getTickingFrequency();
        int tickingFrequency = 50;
        timer = new Timer(tickingFrequency, e -> {
//            if (getTimer().getDelay() != BaseManager.getBaseConfig().getTickingFrequency()) {
//                getTimer().setDelay(BaseManager.getBaseConfig().getTickingFrequency());
//            }

            tick();
        });
        timer.start();

        DragonUtilsAPI.logInfo("TaskManager is now initialized!");
    }

    public static void stop() {
        timer.stop();
        currentRunnables.forEach((index, runnable) -> runnable.cancel());

        DragonUtilsAPI.logInfo("TaskManager is now stopped!");
    }
}
