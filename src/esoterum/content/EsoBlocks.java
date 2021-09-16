package esoterum.content;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import esoterum.type.*;
import mindustry.ctype.ContentList;
import mindustry.world.*;
import mindustry.world.meta.*;

public class EsoBlocks implements ContentList {
    public static Block
        esoBlock, esoSwitch, esoButton, esoClock,
        esoNode, esoJunction, esoRouter,
        esoWire, esoBuffer, esoAnd, esoAndB, esoAndC,
        esoNot, esoXor, esoLed,
        esoLatch, esoNoteBlock, esoDelayGate, esoMonostable;

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

        // region AND
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
        // endregion AND

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

        esoDelayGate = new DelayGate("binary-delay-gate");

        esoMonostable = new BinaryMonostable("binary-monostable");
    }
}