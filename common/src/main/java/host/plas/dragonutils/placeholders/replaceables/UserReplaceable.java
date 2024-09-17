package host.plas.dragonutils.placeholders.replaceables;

import host.plas.dragonutils.placeholders.callbacks.CallbackString;
import host.plas.dragonutils.placeholders.callbacks.UserPlaceholderCallback;
import net.minecraft.commands.CommandSource;
import tv.quaint.objects.AtomicString;

public class UserReplaceable extends AbstractReplaceable<UserPlaceholderCallback> {
    public UserReplaceable(String from, UserPlaceholderCallback callback) {
        super(from, callback);
    }

    public UserReplaceable(String from, int groups, UserPlaceholderCallback callback) {
        super(from, groups, callback);
    }

    public String fetchAs(String string, CommandSource user) {
        if (user == null) return string;
        if (! isReplaceWorthy()) return string;

        addTimesReplaced(getHandledString().count(string));
        AtomicString atomicString = new AtomicString(string);
        getHandledString().regexMatches(string).forEach((s) -> {
            if (getCallback() == null) return;

            atomicString.set(atomicString.get().replace(s, getCallback().apply(new CallbackString(s, getHandledString()), user)));
        });
        return atomicString.get();
    }
}
