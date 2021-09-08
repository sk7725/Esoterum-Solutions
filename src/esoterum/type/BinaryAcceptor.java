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
            boolean out = false;
            for (boolean b : new boolean[]{sBack(), sLeft(), sRight()}) {
                out |= b;
            }
            return out;
        }
    }
}
