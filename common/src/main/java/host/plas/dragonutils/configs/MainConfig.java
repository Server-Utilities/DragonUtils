package host.plas.dragonutils.configs;

import host.plas.dragonutils.DragonUtilsAPI;
import lombok.Getter;
import lombok.Setter;
import tv.quaint.storage.resources.flat.simple.SimpleConfiguration;

@Getter @Setter
public class MainConfig extends SimpleConfiguration {
    public MainConfig() {
        super("config.yml", DragonUtilsAPI.getMainEventable(), false);
    }

    @Override
    public void init() {
    }
}
