package esoterum;

import arc.*;
import arc.util.*;
import esoterum.content.*;
import mindustry.ctype.ContentList;
import mindustry.game.EventType.FileTreeInitEvent;
import mindustry.mod.*;

public class Esoterum extends Mod{
    private static final ContentList[] content = {
            new EsoBlocks()
    };
    public Esoterum(){
        Log.info("Hi");
        Events.on(FileTreeInitEvent.class, h -> EsoSounds.load());
    }

    @Override
    public void loadContent(){
        Log.info("Loading esoterum.");
        for(ContentList list : content){
            list.load();
        }
    }

}
