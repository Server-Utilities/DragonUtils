package host.plas.dragonutils.placeholders.replaceables;

import host.plas.dragonutils.placeholders.RATRegistry;
import host.plas.dragonutils.placeholders.callbacks.UserPlaceholderCallback;
import host.plas.dragonutils.placeholders.expansions.RATExpansion;
import lombok.Getter;
import tv.quaint.utils.MatcherUtils;

@Getter
public class IdentifiedUserReplaceable extends UserReplaceable {
    private final String identifier;

    public IdentifiedUserReplaceable(String identifier, String separator, String from, UserPlaceholderCallback callback) {
        super(identifier + separator + from, callback);
        this.identifier = identifier;
    }

    public IdentifiedUserReplaceable(RATExpansion expansion, String from, UserPlaceholderCallback callback) {
        super(RATRegistry.getLiteralWithExpansion(from, expansion), callback);
        this.identifier = expansion.getBuilder().getIdentifier();
    }

    public IdentifiedUserReplaceable(String identifier, String separator, String regex, int groups, UserPlaceholderCallback callback) {
        super(MatcherUtils.makeLiteral(identifier + separator) + regex, groups, callback);
        this.identifier = identifier;
    }

    public IdentifiedUserReplaceable(RATExpansion expansion, String regex, int groups, UserPlaceholderCallback callback) {
        super(RATRegistry.getRegexWithExpansion(regex, expansion), groups, callback);
        this.identifier = expansion.getBuilder().getIdentifier();
    }
}
