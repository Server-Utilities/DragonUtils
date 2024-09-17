package host.plas.dragonutils.placeholders.callbacks;

import net.minecraft.commands.CommandSource;

import java.util.function.BiFunction;

@FunctionalInterface
public interface UserPlaceholderCallback extends BiFunction<CallbackString, CommandSource, String>, RATCallback {
}
