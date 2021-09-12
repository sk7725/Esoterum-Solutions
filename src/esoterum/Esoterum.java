package esoterum;

import arc.*;
import arc.graphics.*;
import arc.util.*;
import esoterum.content.*;
import mindustry.ctype.ContentList;
import mindustry.game.EventType;
import mindustry.mod.*;

import static mindustry.Vars.*;

public class Esoterum extends Mod{
    private static final ContentList[] content = {
            new EsoBlocks()
    };
    public Esoterum(){
        Log.info("Hi");
        Events.on(EventType.FileTreeInitEvent.class, h -> EsoSounds.load());
        Events.on(EventType.ClientLoadEvent.class, h -> new EsoSettings().init());
    }

    @Override
    public void loadContent(){
        Color col = Color.valueOf(Core.settings.getString("esoterumsignalcolor", "ffd37f"));
        EsoVars.connectionColor.set(col);

        Log.info("Loading esoterum.");
        for(ContentList list : content){
            list.load();
        }
    }

}
