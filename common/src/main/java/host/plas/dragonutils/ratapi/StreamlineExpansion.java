package host.plas.dragonutils.ratapi;

import host.plas.dragonutils.DragonUtilsAPI;
import host.plas.dragonutils.placeholders.RATRegistry;
import host.plas.dragonutils.placeholders.expansions.RATExpansion;
import host.plas.dragonutils.placeholders.replaceables.IdentifiedReplaceable;
import host.plas.dragonutils.placeholders.replaceables.IdentifiedUserReplaceable;
import host.plas.dragonutils.utils.SourceUtils;
import net.minecraft.commands.CommandSource;
import net.minecraft.server.level.ServerPlayer;
import tv.quaint.utils.MatcherUtils;

import java.util.Optional;

public class StreamlineExpansion extends RATExpansion {
    public StreamlineExpansion() {
        super(new RATExpansionBuilder("streamline"));
        DragonUtilsAPI.logInfo(getClass().getSimpleName() + " is registered!");
    }

    @Override
    public void init() {
//        new IdentifiedReplaceable(this, "version", (s) -> SLAPI.getInstance().getPlatform().getVersion()).register();
        new IdentifiedReplaceable(this, "players_max", (s) -> String.valueOf(DragonUtilsAPI.getServer().getMaxPlayers())).register();
        new IdentifiedReplaceable(this, "players_online", (s) -> String.valueOf(DragonUtilsAPI.getOnlinePlayers().size())).register();

        new IdentifiedReplaceable(this, MatcherUtils.makeLiteral("parse_") + "(.*?)", 1, (s) -> {
            try {
                if (s.get().contains(":::")) {
                    String[] things = s.get().split(":::", 2);
                    Optional<CommandSource> userOptional = SourceUtils.getSenderByName(things[0]);
                    if (userOptional.isEmpty()) return s.string();
                    String parse = things[1].replace("*/*", "%");
                    return RATRegistry.fetchDirty(parse, userOptional.get());
                } else {
                    return RATRegistry.fetchDirty(s.get());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return s.string();
            }
        }).register();

//        new IdentifiedUserReplaceable(this, "user_ping", (s, user) -> {
//            if (user == null) return s.string();
//            if (! (user instanceof ServerPlayer)) return s.string();
//            else return String.valueOf(((ServerPlayer) user).getPing());
//        }).register();
//        new IdentifiedUserReplaceable(this, "user_online", (s, user) -> {
//            if (user == null) return s.string();
//            if (! (user instanceof ServerPlayer)) return s.string();
//
//            ((ServerPlayer) user).isOnline() ?
//                    "true" : "false"
//        }).register();
        new IdentifiedUserReplaceable(this, "user_uuid", (s, user) -> {
            if (user == null) return s.string();
            if (! (user instanceof ServerPlayer)) return s.string();
            return ((ServerPlayer) user).getUUID().toString();
        }).register();

        new IdentifiedUserReplaceable(this, "user_absolute", (s, user) -> SourceUtils.getAbsolute(user)).register();
//        new IdentifiedUserReplaceable(this, "user_absolute_onlined", (s, user) -> UserUtils.getOffOnAbsolute(user)).register();
//        new IdentifiedUserReplaceable(this, "user_formatted", (s, user) -> UserUtils.getFormatted(user)).register();
//        new IdentifiedUserReplaceable(this, "user_formatted_onlined", (s, user) -> UserUtils.getOffOnFormatted(user)).register();

//        new IdentifiedUserReplaceable(this, "user_prefix", (s, user) -> UserUtils.getLuckPermsPrefix(user.getCurrentName())).register();
//        new IdentifiedUserReplaceable(this, "user_suffix", (s, user) -> UserUtils.getLuckPermsSuffix(user.getCurrentName())).register();

//        new IdentifiedUserReplaceable(this, "user_level",
//                (s, user) -> user instanceof StreamPlayer ? String.valueOf(user.getLeveling().getLevel()) : s.string()).register();
//        new IdentifiedUserReplaceable(this, "user_xp_current",
//                (s, user) -> user instanceof StreamPlayer ? String.valueOf(user.getLeveling().getCurrentExperience()) : s.string()).register();
//        new IdentifiedUserReplaceable(this, "user_xp_total",
//                (s, user) -> user instanceof StreamPlayer ? String.valueOf(user.getLeveling().getTotalExperience()) : s.string()).register();
//        new IdentifiedUserReplaceable(this, "user_play_seconds",
//                (s, user) -> user instanceof StreamPlayer ? String.valueOf(((StreamPlayer) user).getPlaySecondsAsString()) : s.string()).register();
//        new IdentifiedUserReplaceable(this, "user_play_minutes",
//                (s, user) -> user instanceof StreamPlayer ? String.valueOf(((StreamPlayer) user).getPlayMinutesAsString()) : s.string()).register();
//        new IdentifiedUserReplaceable(this, "user_play_hours",
//                (s, user) -> user instanceof StreamPlayer ? String.valueOf(((StreamPlayer) user).getPlayHoursAsString()) : s.string()).register();
//        new IdentifiedUserReplaceable(this, "user_play_days",
//                (s, user) -> user instanceof StreamPlayer ? String.valueOf(((StreamPlayer) user).getPlayDaysAsString()) : s.string()).register();
//        new IdentifiedUserReplaceable(this, "user_ip",
//                (s, user) -> user instanceof StreamPlayer ? String.valueOf(((StreamPlayer) user).getCurrentIp()) : s.string()).register();
//        new IdentifiedUserReplaceable(this, "user_points", (s, user) -> String.valueOf(user.getPoints())).register();
//        new IdentifiedUserReplaceable(this, "user_server", (s, user) -> String.valueOf(user.getServerName())).register();
//        new IdentifiedUserReplaceable(this, "user_tags", (s, user) -> user.getMeta().getTagsAsString()).register();
//
//        new IdentifiedUserReplaceable(this, "[?][R][:](.*?)", 1, (s, user) -> {
//            try {
//                String params = s.get();
//                params = params
//                        .replace("[[", "%")
//                        .replace("]]", "%")
//                        .replace("{{", "%")
//                        .replace("}}", "%")
//                        .replace("*/*", "%")
//                ;
//                return ModuleUtils.parseOnProxy(user, params);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return s.string();
//            }
//        }).register();
    }
}
