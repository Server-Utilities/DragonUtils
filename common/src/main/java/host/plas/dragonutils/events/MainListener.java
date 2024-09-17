package host.plas.dragonutils.events;

import host.plas.dragonutils.DragonUtilsAPI;
import lombok.Getter;
import lombok.Setter;
import tv.quaint.events.BaseEventHandler;
import tv.quaint.events.BaseEventListener;

@Getter @Setter
public class MainListener implements BaseEventListener {
    public MainListener() {
        BaseEventHandler.bake(this, DragonUtilsAPI.getMainEventable());
        DragonUtilsAPI.logInfo("&cMainListener &fhas been registered.");
    }
}
