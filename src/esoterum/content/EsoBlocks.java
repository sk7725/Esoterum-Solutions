package esoterum.content;

import esoterum.type.*;
import mindustry.ctype.ContentList;
import mindustry.world.*;
import mindustry.world.meta.*;

public class EsoBlocks implements ContentList {
    public static Block
        esoBlock, esoButton, esoAcceptorTest;

    public void load(){
        esoBlock = new BinaryBlock("test-binary-block"){{
            buildVisibility = BuildVisibility.hidden;
        }};
        esoAcceptorTest = new BinaryAcceptor("binary-wire"){{
            buildVisibility = BuildVisibility.shown;
        }};
        esoButton = new BinarySwitch("binary-switch"){{
            buildVisibility = BuildVisibility.shown;
        }};
    }
}