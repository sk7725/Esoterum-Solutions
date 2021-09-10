package esoterum.content;

import arc.Core;
import esoterum.type.*;
import mindustry.ctype.ContentList;
import mindustry.world.*;
import mindustry.world.meta.*;

public class EsoBlocks implements ContentList {
    public static Block
        esoBlock, esoButton, esoNode, esoJunction, esoRouter, esoWire, esoAnd, esoNot, esoXor, esoLed;

    public void load(){
        esoBlock = new BinaryBlock("test-binary-block"){{
            buildVisibility = BuildVisibility.hidden;
        }};

        esoWire = new BinaryAcceptor("binary-wire"){
            @Override
            public void load(){
                super.load();
                connectionRegion = Core.atlas.find("eso-connection-large");
            }
        };

        esoButton = new BinarySwitch("binary-switch");

        esoNode = new BinaryNode("binary-node");

        esoJunction = new BinaryJunction("binary-junction");

        esoRouter = new BinaryRouter("binary-router");

        // LOGIC GATES
        // Skipping OR because they're basically just two wires side by side
        esoAnd = new BinaryGate("binary-AND");

        esoNot = new BinaryGate("binary-NOT"){
            {
                inputs = new boolean[]{false, true, false};
            }

            @Override
            public boolean operation(boolean[] in){
                return !in[1];
            }
        };

        esoXor = new BinaryGate("binary-XOR"){
            @Override
            public boolean operation(boolean[] in){
                return in[0] ^ in[2];
            }
        };

        esoLed = new BinaryLed("binary-led");
    }
}
