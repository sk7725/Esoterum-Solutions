package esoterum.type;

public class BinaryAcceptor extends BinaryBlock {
    public BinaryAcceptor(String name) {
        super(name);
        rotate = true;
        drawArrow = true;
    }

    public class BinaryAcceptorBuild extends BinaryBuild {

        @Override
        public void updateTile() {
            lastSignal = nextSignal;
            nextSignal = signal();
        }
        @Override
        public boolean signal() {
            for(BinaryBuild other : connected){
                if(other.lastSignal && canSignal(tile.build, other) && other != frontBuild)return true;
            }
            return false;
        }
    }
}
