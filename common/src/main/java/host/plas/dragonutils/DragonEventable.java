package host.plas.dragonutils;

import tv.quaint.objects.handling.derived.IModEventable;

import java.io.File;

public class DragonEventable implements IModEventable {
    @Override
    public File getDataFolder() {
        return DragonUtilsAPI.getModDirectory();
    }

    @Override
    public String getIdentifier() {
        return DragonUtilsAPI.MOD_ID;
    }
}
