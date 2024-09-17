package host.plas.dragonutils.placeholders.replaceables;

import host.plas.dragonutils.placeholders.callbacks.CallbackString;
import host.plas.dragonutils.placeholders.callbacks.PlaceholderCallback;
import org.jetbrains.annotations.Nullable;
import tv.quaint.objects.AtomicString;

public class GenericReplaceable extends AbstractReplaceable<PlaceholderCallback> {
    public GenericReplaceable(@Nullable String from, int groups, @Nullable PlaceholderCallback callback) {
        super(from, groups, callback);
    }

    public GenericReplaceable(@Nullable String from, @Nullable PlaceholderCallback callback) {
        super(from, callback);
    }

    public String fetch(String string) {
        if (! isReplaceWorthy()) return string;

        addTimesReplaced(getHandledString().count(string));
        AtomicString atomicString = new AtomicString(string);
        getHandledString().regexMatches(string).forEach((s) -> {
            if (getCallback() == null) return;

            atomicString.set(atomicString.get().replace(s, getCallback().apply(new CallbackString(s, getHandledString()))));
        });
        return atomicString.get();
    }
}
