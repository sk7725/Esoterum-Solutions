package esoterum.world.blocks.binary;

import arc.graphics.g2d.*;
import esoterum.content.*;

public class BinaryJunction extends BinaryBlock{
    public BinaryJunction(String name){
        super(name);
        emits = true;
        rotate = false;
        drawConnection = false;
        emitAllDirections = false;
    }

    public class BinaryJunctionBuild extends BinaryBuild {

        @Override
        public boolean signal() {
            return getSignal(nb[1]) | getSignal(nb[2]) | getSignal(nb[0]) | getSignal(nb[3]);
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            Draw.rect(topRegion, x, y, rotdeg());
        }

        @Override
        public void updateTile() {
            lastSignal = nextSignal;
            nextSignal = signal();
        }

        @Override
        public boolean signalLeft() {
            return getSignal(nb[2]);
        }

        @Override
        public boolean signalRight() {
            return getSignal(nb[1]);
        }

        @Override
        public boolean signalFront() {
            return getSignal(nb[0]);
        }

        @Override
        public boolean signalBack() {
            return getSignal(nb[3]);
        }

        @Override
        public void created() {
            super.created();
            rotation(0);
        }
    }
}
