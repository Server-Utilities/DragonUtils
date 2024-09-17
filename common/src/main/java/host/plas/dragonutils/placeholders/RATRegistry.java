package host.plas.dragonutils.placeholders;

import host.plas.dragonutils.placeholders.callbacks.RATCallback;
import host.plas.dragonutils.placeholders.expansions.RATExpansion;
import host.plas.dragonutils.placeholders.replaceables.*;
import host.plas.dragonutils.utils.SourceUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.commands.CommandSource;
import tv.quaint.objects.AtomicString;
import tv.quaint.utils.MatcherUtils;

import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListMap;

public class RATRegistry {
    @Getter @Setter
    private static ConcurrentSkipListMap<String, AbstractReplaceable<? extends RATCallback>> replacements = new ConcurrentSkipListMap<>();

    public static void register(AbstractReplaceable<? extends RATCallback> replacement) {
        replacements.put(replacement.getHandledString().getRegex(), replacement);
    }

    public static void unregister(AbstractReplaceable<? extends RATCallback> replacement) {
        replacements.remove(replacement.getHandledString().getRegex());
    }

    public static void unregister(String from) {
        replacements.remove(from);
    }

    public static void unregisterAll() {
        replacements = new ConcurrentSkipListMap<>();
    }

    public static void unregisterAll(String identifier) {
        replacements.values().removeIf(replacement -> replacement instanceof IdentifiedReplaceable && ((IdentifiedReplaceable) replacement).getIdentifier().equals(identifier) ||
                replacement instanceof IdentifiedUserReplaceable && ((IdentifiedUserReplaceable) replacement).getIdentifier().equals(identifier));
    }

    public static void unregisterAllStartsWith(String startsWith) {
        replacements.values().removeIf(replacements -> replacements.getHandledString().getRegex().startsWith(startsWith));
    }

    public static AbstractReplaceable<? extends RATCallback> getReplacement(String from) {
        return replacements.get(from);
    }

    public static String fetch(String from) {
        AtomicString result = new AtomicString(from);
        getReplacements().forEach((s, replacement) -> {
            if (replacement == null) return;
            if (! replacement.isReplaceWorthy()) return;
            if (replacement instanceof GenericReplaceable) {
                GenericReplaceable generic = (GenericReplaceable) replacement;
                result.set(generic.fetch(result.get()));
            }
            if (replacement instanceof UserReplaceable) {
                UserReplaceable user = (UserReplaceable) replacement;
                result.set(user.fetchAs(result.get(), SourceUtils.getConsoleSource()));
            }
        });

        return result.get();
    }

    public static String fetchDirty(String from) {
        String temp = from;
        temp = fetch(temp);
        while (! Objects.equals(temp, from)) {
            from = temp;
            temp = fetch(temp);
        }
        return temp;
    }

    public static String fetch(String from, CommandSource user) {
        AtomicString result = new AtomicString(from);
        getReplacements().forEach((s, replacement) -> {
            if (replacement instanceof UserReplaceable) {
                UserReplaceable userReplacement = (UserReplaceable) replacement;
                result.set(userReplacement.fetchAs(result.get(), user));
            }
        });
        result.set(fetch(result.get()));

        return result.get();
    }

    public static String fetchDirty(String from, CommandSource user) {
        String temp = from;
        temp = fetch(temp, user);
        while (! Objects.equals(temp, from)) {
            from = temp;
            temp = fetch(temp, user);
        }
        return temp;
    }

    public static String getRegexWithExpansion(String regex, RATExpansion expansion) {
        return MatcherUtils.makeLiteral(expansion.getBuilder().getBoundingPrefix() + expansion.getBuilder().getIdentifier() + expansion.getBuilder().getSeparator())
                + regex + MatcherUtils.makeLiteral(expansion.getBuilder().getBoundingSuffix());
    }

    public static String getLiteralWithExpansion(String literal, RATExpansion expansion) {
        return expansion.getBuilder().getBoundingPrefix() + expansion.getBuilder().getIdentifier()
                + expansion.getBuilder().getSeparator() + literal + expansion.getBuilder().getBoundingSuffix();
    }
}
