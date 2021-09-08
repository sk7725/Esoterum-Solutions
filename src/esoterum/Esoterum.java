package esoterum;

import arc.util.*;
import esoterum.content.EsoBlocks;
import mindustry.ctype.ContentList;
import mindustry.mod.*;

public class Esoterum extends Mod{
    private static final ContentList[] content = {
            new EsoBlocks()
    };
    public Esoterum(){
        Log.info("Hi");
    }

    @Override
    public void loadContent(){
        Log.info("Loading esoterum.");
        for(ContentList list : content){
            list.load();
        }
    }

}
