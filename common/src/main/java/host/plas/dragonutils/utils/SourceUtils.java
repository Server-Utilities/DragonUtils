package host.plas.dragonutils.utils;

import host.plas.dragonutils.DragonUtilsAPI;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.node.types.SuffixNode;
import net.minecraft.commands.CommandSource;
import net.minecraft.server.level.ServerPlayer;
import tv.quaint.objects.AtomicString;
import tv.quaint.utils.MathUtils;

import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

public class SourceUtils {
    public static CommandSource getConsoleSource() {
        return DragonUtilsAPI.getServer();
    }

    public static boolean isValidUuid(String possibleUUID) {
        try {
            UUID.fromString(possibleUUID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getLuckPermsPrefix(String username) {
        AtomicString string = new AtomicString("");

        DragonUtilsAPI.ifLuckPerms(api -> {
            User user;
            if (isValidUuid(username)) user = api.getUserManager().getUser(UUID.fromString(username));
            else user = api.getUserManager().getUser(username);
            if (user == null) {
                string.set("");
            }

            String prefix;

            Group group = api.getGroupManager().getGroup(user.getPrimaryGroup());
            if (group == null) {
                TreeMap<Integer, String> preWeight = new TreeMap<>();

                for (PrefixNode node : user.getNodes(NodeType.PREFIX)) {
                    preWeight.put(node.getPriority(), node.getMetaValue());
                }

                prefix = preWeight.get(MathUtils.getCeilingInt(preWeight.keySet()));

                if (prefix == null) {
                    prefix = "";
                }

                string.set(prefix);
            }


            TreeMap<Integer, String> preWeight = new TreeMap<>();

            for (PrefixNode node : group.getNodes(NodeType.PREFIX)) {
                preWeight.put(node.getPriority(), node.getMetaValue());
            }

            for (PrefixNode node : user.getNodes(NodeType.PREFIX)) {
                preWeight.put(node.getPriority(), node.getMetaValue());
            }

            prefix = preWeight.get(MathUtils.getCeilingInt(preWeight.keySet()));

            if (prefix == null) {
                prefix = "";
            }

            string.set(prefix);
        });

        return string.get();
    }

    public static String getLuckPermsSuffix(String username){
        AtomicString string = new AtomicString("");

        DragonUtilsAPI.ifLuckPerms(api -> {
            User user;
            if (isValidUuid(username)) user = api.getUserManager().getUser(UUID.fromString(username));
            else user = api.getUserManager().getUser(username);
            if (user == null) {
                string.set("");
            }

            String suffix;

            Group group = api.getGroupManager().getGroup(user.getPrimaryGroup());
            if (group == null) {
                TreeMap<Integer, String> sufWeight = new TreeMap<>();

                for (SuffixNode node : user.getNodes(NodeType.SUFFIX)) {
                    sufWeight.put(node.getPriority(), node.getMetaValue());
                }

                suffix = sufWeight.get(MathUtils.getCeilingInt(sufWeight.keySet()));

                if (suffix == null) {
                    suffix = "";
                }

                string.set(suffix);
            }

            TreeMap<Integer, String> sufWeight = new TreeMap<>();

            for (SuffixNode node : group.getNodes(NodeType.SUFFIX)) {
                sufWeight.put(node.getPriority(), node.getMetaValue());
            }

            for (SuffixNode node : user.getNodes(NodeType.SUFFIX)) {
                sufWeight.put(node.getPriority(), node.getMetaValue());
            }

            suffix = sufWeight.get(MathUtils.getCeilingInt(sufWeight.keySet()));

            if (suffix == null) {
                suffix = "";
            }

            string.set(suffix);
        });

        return string.get();
    }

    public static Optional<CommandSource> getSenderByName(String thing) {
        return Optional.ofNullable(DragonUtilsAPI.getServer().getPlayerList().getPlayerByName(thing));
    }

    public static Optional<CommandSource> getSenderByUuid(String thing) {
        return Optional.ofNullable(DragonUtilsAPI.getServer().getPlayerList().getPlayer(UUID.fromString(thing)));
    }

    public static String getAbsolute(CommandSource user) {
        if (user == null) return "";
        if (! (user instanceof ServerPlayer)) return "CONSOLE";

        return ((ServerPlayer) user).getName().getString();
    }
}
