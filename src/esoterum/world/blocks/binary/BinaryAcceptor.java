package esoterum.world.blocks.binary;

import mindustry.graphics.*;

public class BinaryAcceptor extends BinaryBlock {
    public BinaryAcceptor(String name){
        super(name);
        rotate = true;
        drawArrow = true;
        emits = true;
    }

    public class BinaryAcceptorBuild extends BinaryBuild {

        @Override
        public void updateTile(){
            lastSignal = nextSignal | getSignal(nb[0]);
            nextSignal = signal();
        }

        @Override
        public void drawSelect() {
            if(rotate && nb[3] != null)Drawf.arrow(x, y, nb[3].x, nb[3].y, 2f, 2f, Pal.accent);
        }

        @Override
        public boolean signal(){
            return getSignal(nb[1]) | getSignal(nb[2]);
        }

        @Override
        public boolean signalFront() {
            return (nb[0] != null ? nb[0].rotation == rotation || !nb[0].block.rotate ? getSignal(nb[0]) : lastSignal : lastSignal) | nextSignal;
        }
    }
}
