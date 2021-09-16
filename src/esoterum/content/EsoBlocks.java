package esoterum.content;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import esoterum.type.*;
import mindustry.ctype.ContentList;
import mindustry.world.*;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.*;

public class EsoBlocks implements ContentList {
    public static Block
            // environment
            esoPanel, esoPanel1, esoPanel2, esoPanel3,
            esoPanelFlat, esoPanelOpen, esoPanelE, esoPanelS, esoPanelO,

            // binary
            esoBlock, esoButton, esoClickButton, esoClock,
            esoNode, esoJunction, esoRouter,
            esoWire, esoBuffer, esoAnd, esoAndB, esoAndC,
            esoNot, esoXor, esoLed,
            esoLatch, esoNoteBlock, esoDelayGate;

    public void load(){
        // region environment
        esoPanel = new Floor("chamber-panel", 0);
        esoPanelFlat = new Floor("chamber-panel-flat", 0);
        esoPanel1 = new Floor("chamber-panel-1", 0);
        esoPanel2 = new Floor("chamber-panel-2", 0);
        esoPanel3 = new Floor("chamber-panel-3", 0);

        esoPanelOpen = new Floor("chamber-panel-open", 3);

        esoPanelE = new Floor("chamber-panel-e", 0);
        esoPanelS = new Floor("chamber-panel-s", 0);
        esoPanelO = new Floor("chamber-panel-o", 0);
        // endregion environment

        // region binary
        esoBlock = new BinaryBlock("test-binary-block"){{
            buildVisibility = BuildVisibility.hidden;
        }};

        esoWire = new BinaryAcceptor("binary-wire"){
            @Override
            public void load(){
                super.load();
                connectionRegion = Core.atlas.find("eso-connection-large");
            }

            @Override
            protected TextureRegion[] icons() {
                return new TextureRegion[]{
                    region,
                    topRegion,
                    connectionRegion
                };
            }
        };

        esoClock = new BinaryClock("binary-clock");

        esoButton = new BinarySwitch("binary-switch");

        esoClickButton = new BinaryButton("binary-button");

        esoNode = new BinaryNode("binary-node"){{
            range = 48f;
        }};

        esoJunction = new BinaryJunction("binary-junction");

        esoRouter = new BinaryRouter("binary-router");

        // LOGIC GATES
        // Skipping OR because they're basically just two wires side by side

        esoAnd = new BinaryGate("binary-AND"){{
            variant = "A";
        }};

        esoAndB = new BinaryGate("binary-AND-B"){
            {
                variant = "B";
                inputs = new boolean[]{false, true, true};
            }

            @Override
            public void load() {
                super.load();
                topRegion = Core.atlas.find("eso-binary-AND-top");
            }

            @Override
            protected TextureRegion[] icons() {
                return new TextureRegion[]{
                        region,
                        topRegion,
                        Core.atlas.find("eso-gate-connections1"),
                        variantRegion
                };
            }

            @Override
            public boolean operation(boolean[] in) {
                return in[1] && in[2];
            }
        };

        esoAndC = new BinaryGate("binary-AND-C"){
            {
                variant = "C";
                inputs = new boolean[]{true, true, false};
            }

            @Override
            public void load() {
                super.load();
                topRegion = Core.atlas.find("eso-binary-AND-top");
            }

            @Override
            protected TextureRegion[] icons() {
                return new TextureRegion[]{
                        region,
                        topRegion,
                        Core.atlas.find("eso-gate-connections2"),
                        variantRegion
                };
            }

            @Override
            public boolean operation(boolean[] in) {
                return in[1] && in[0];
            }
        };

        esoNot = new BinaryGate("binary-NOT"){
            {
                drawSides = false;
                inputs = new boolean[]{false, true, false};
            }

            @Override
            public boolean operation(boolean[] in){
                return !in[1];
            }

            @Override
            protected TextureRegion[] icons() {
                return new TextureRegion[]{
                    region,
                    topRegion,
                    Core.atlas.find("eso-not-connections")
                };
            }
        };

        esoBuffer = new BinaryGate("binary-buffer"){
            {
                drawSides = false;
                inputs = new boolean[]{false, true, false};
            }

            //Non't - Farmer Thanos
            @Override
            public boolean operation(boolean[] in){
                return in[1];
            }

            @Override
            protected TextureRegion[] icons() {
                return new TextureRegion[]{
                        region,
                        topRegion,
                        Core.atlas.find("eso-not-connections")
                };
            }
        };

        esoXor = new BinaryGate("binary-XOR"){
            {
                drawSides = false;
            }

            @Override
            public boolean operation(boolean[] in){
                return in[0] ^ in[2];
            }
        };

        esoLed = new BinaryLed("binary-led");

        esoLatch = new BinaryLatch("binary-latch");

        esoNoteBlock = new NoteBlock("binary-note-block");

        esoDelayGate = new DelayGate("binary-delay-gate");
        // endregion binary
    }
}
