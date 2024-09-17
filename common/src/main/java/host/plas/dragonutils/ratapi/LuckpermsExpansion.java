package host.plas.dragonutils.ratapi;

import host.plas.dragonutils.DragonUtilsAPI;
import host.plas.dragonutils.placeholders.expansions.RATExpansion;
import host.plas.dragonutils.placeholders.replaceables.IdentifiedUserReplaceable;
import host.plas.dragonutils.utils.SourceUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.query.QueryMode;
import net.luckperms.api.query.QueryOptions;
import net.minecraft.server.level.ServerPlayer;
import tv.quaint.objects.AtomicString;

import java.util.Optional;
import java.util.UUID;

public class LuckpermsExpansion extends RATExpansion {
    public LuckpermsExpansion() {
        super(new RATExpansionBuilder("luckperms"));
        DragonUtilsAPI.logInfo(getClass().getSimpleName() + " is registered!");
    }

    @Override
    public void init() {
        new IdentifiedUserReplaceable(this, "prefix", (s, user) -> {
            if (user == null) return s.string();
            if (! (user instanceof ServerPlayer)) return s.string();
            return SourceUtils.getLuckPermsPrefix(((ServerPlayer) user).getUUID().toString());
        }).register();
        new IdentifiedUserReplaceable(this, "suffix", (s, user) -> {
            if (user == null) return s.string();
            if (user instanceof ServerPlayer) {
                return SourceUtils.getLuckPermsSuffix(((ServerPlayer) user).getUUID().toString());
            }
            return s.string();
        }).register();

        new IdentifiedUserReplaceable(this, "primary_group", (s, user) -> {
            try {
                if (! (user instanceof ServerPlayer)) return s.string();
                UUID uuid = null;

                try {
                    uuid = UUID.fromString(((ServerPlayer) user).getUUID().toString());

                    if (uuid == null) return s.string();
                } catch (Exception e) {
                    // is console.
                    return s.string();
                }

                LuckPerms api = LuckPermsProvider.get();
                User u = api.getUserManager().getUser(uuid);
                if (u == null) return s.string();

                return u.getPrimaryGroup();
            } catch (Exception e) {
                e.printStackTrace();
                return s.string();
            }
        }).register();

        new IdentifiedUserReplaceable(this, "highest_group", (s, user) -> {
            try {
                if (! (user instanceof ServerPlayer)) return s.string();
                UUID uuid = null;

                try {
                    uuid = UUID.fromString(((ServerPlayer) user).getUUID().toString());

                    if (uuid == null) return s.string();
                } catch (Exception e) {
                    // is console.
                    return s.string();
                }

                LuckPerms api = LuckPermsProvider.get();
                User u = api.getUserManager().getUser(uuid);
                if (u == null) return s.string();

                Optional<Group> group = u.getInheritedGroups(QueryOptions.builder(QueryMode.CONTEXTUAL).build()).stream().findFirst();

                return group.map(Group::getName).orElse(s.string());
            } catch (Exception e) {
                e.printStackTrace();
                return s.string();
            }
        }).register();

        new IdentifiedUserReplaceable(this, "[m][e][t][a][_]" + "(.*?)", 1, (s, user) -> {
            try {
                if (! (user instanceof ServerPlayer)) return s.string();
                String params = s.get();

                UUID uuid;
                try {
                    uuid = UUID.fromString(((ServerPlayer) user).getUUID().toString());
                } catch (Exception e) {
                    // is console.
                    return s.string();
                }

                LuckPerms api = LuckPermsProvider.get();
                User u = api.getUserManager().getUser(uuid);
                if (u == null) return s.string();

                AtomicString s1 = new AtomicString("");
                u.getNodes(NodeType.META).forEach(node -> {
                    if (node.getMetaKey().equals(params)) {
                        s1.set(node.getMetaValue());
                    }
                });

                if (! s1.get().isEmpty() || !s1.get().isBlank()) {
                    return s1.get();
                }

                Group group = api.getGroupManager().getGroup(u.getPrimaryGroup());
                if (group == null) return s.string();

                group.getNodes(NodeType.META).forEach(node -> {
                    if (node.getMetaKey().equals(params)) {
                        s1.set(node.getMetaValue());
                    }
                });

                if (! s1.get().isEmpty() || !s1.get().isBlank()) {
                    return s1.get();
                }

                return s.string();
            } catch (Exception e) {
                e.printStackTrace();
                return s.string();
            }
        }).register();
    }
}
