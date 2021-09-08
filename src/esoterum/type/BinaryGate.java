package esoterum.type;

public class BinaryGate extends BinaryAcceptor {
    // left, back, right
    public boolean[] inputs = {false, false, false};
    public BinaryGate(String name){
        super(name);
    }

    // Override when using
    public boolean operation(boolean[] in){
        // AND
        return in[0] && in[2];
    }

    // This is very messy and I want to die.
    public class BinaryGateBuild extends BinaryAcceptorBuild {
        public boolean[] results = new boolean[]{false, false, false};

        @Override
        public boolean signal(){
            results[0] = sLeft();
            results[1] = sBack();
            results[2] = sRight();
            return operation(results);
        }
    }
}
