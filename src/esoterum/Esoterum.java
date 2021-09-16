package esoterum;

import arc.*;
import arc.graphics.*;
import arc.util.*;
import esoterum.content.*;
import mindustry.game.*;
import mindustry.mod.*;

public class Esoterum extends Mod{
    public Esoterum(){
        Events.on(EventType.FileTreeInitEvent.class, h -> EsoSounds.load());
        Events.on(EventType.ClientLoadEvent.class, h -> new EsoSettings().init());
    }

    @Override
    public void loadContent(){
        Color.valueOf(
            EsoVars.connectionColor,
            Core.settings.getString("esoterumsignalcolor", "ffd37f")
        );

        Color.valueOf(
            EsoVars.connectionOffColor,
            Core.settings.getString("esoterumnosignalcolor", "ffffff")
        );

        Log.info("Loading esoterum.");
        new EsoBlocks().load();
    }
}
