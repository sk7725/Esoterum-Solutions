package esoterum.type;

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
            lastSignal = nextSignal;
            nextSignal = signal();
        }

        @Override
        public void drawSelect() {
            if(rotate){
                if(front() != null){
                    Drawf.arrow(x, y, front().x, front().y, 2f, 2f, Pal.accent);
                }
            }
        }

        @Override
        public boolean signal(){
            return getSignal(nb[0]) | getSignal(nb[1]) | getSignal(nb[2]);
        }

        // IT'S FUCKING INSTANT
        // IT'S NOT SUPPOSED TO BE BUT I'LL TAKE IT
        @Override
        public boolean signalFront() {
            return signal();
        }
    }
}
