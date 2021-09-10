package esoterum.type;

public class BinaryRouter extends BinaryAcceptor{
    public BinaryRouter(String name){
        super(name);
        rotate = false;
        emits = true;
        drawConnection = false;
    }

    public class BinaryRouterBuild extends BinaryAcceptorBuild {
        @Override
        public boolean signal() {
            return super.signal() | sFront();
        }
    }
}
