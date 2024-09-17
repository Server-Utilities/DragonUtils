package host.plas.dragonutils.text;

import host.plas.dragonutils.DragonUtilsAPI;

public class HexInit {
    public static void init() {
        TextManager.registerHexPolicy(new HexPolicy("{#", "}"));
        TextManager.registerHexPolicy(new HexPolicy("<#", ">"));
        TextManager.registerHexPolicy(new HexPolicy("&#", ""));

        DragonUtilsAPI.logInfo("Registered " + TextManager.getHexPolicies().size() + " hex policies.");
    }
}
