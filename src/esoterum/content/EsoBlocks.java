package esoterum.content;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import esoterum.type.binary.*;
import esoterum.type.environment.*;
import mindustry.ctype.ContentList;
import mindustry.world.*;
import mindustry.world.meta.*;

public class EsoBlocks implements ContentList {
    public static Block
            // environment
            esoPanel, esoPanel1, esoPanel2, esoPanel3,
            esoPanelFlat, esoPanelOpen, esoPanelE, esoPanelS, esoPanelO,

            // binary
            esoBlock, esoButton, esoSwitch, esoClock,
            esoNode, esoJunction, esoRouter,
            esoWire, esoBuffer, esoAnd, esoAndB, esoAndC,
            esoNot, esoXor, esoLed,
            esoLatch, esoNoteBlock,
            esoLock, esoMonostable,
            esoDelayGate, esoWaitGate;

    public void load(){
        // region environment
        esoPanel = new nonBlendFloor("chamber-panel", 0);
        esoPanelFlat = new nonBlendFloor("chamber-panel-flat", 0);
        esoPanel1 = new nonBlendFloor("chamber-panel-1", 0);
        esoPanel2 = new nonBlendFloor("chamber-panel-2", 0);
        esoPanel3 = new nonBlendFloor("chamber-panel-3", 0);

        esoPanelOpen = new nonBlendFloor("chamber-panel-open", 3);

        esoPanelE = new nonBlendFloor("chamber-panel-e", 0);
        esoPanelS = new nonBlendFloor("chamber-panel-s", 0);
        esoPanelO = new nonBlendFloor("chamber-panel-o", 0);
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

        esoSwitch = new BinarySwitch("binary-switch");

        esoButton = new BinaryButton("binary-button");

        esoClock = new BinaryClock("binary-clock");

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

        esoMonostable = new BinaryMonostable("binary-monostable");

        esoLock = new BinaryLock("binary-lock");

        esoDelayGate = new DelayGate("binary-delay-gate");

        esoWaitGate = new WaitGate("binary-wait-gate");
        //endregion binary
    }
}