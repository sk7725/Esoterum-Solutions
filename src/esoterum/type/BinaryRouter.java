package esoterum.type;

import arc.Core;
import arc.graphics.g2d.Draw;

public class BinaryRouter extends BinaryAcceptor{
    public BinaryRouter(String name){
        super(name);
        rotate = false;
        emits = true;
        drawConnection = false;
        connectionRegion = Core.atlas.find("eso-full-connections");
        emitAllDirections = true;
    }

    public class BinaryRouterBuild extends BinaryAcceptorBuild {
        @Override
        public void draw() {
            super.draw();
            Draw.rect(connectionRegion, x, y);
        }

        @Override
        public boolean signal() {
            return getSignal(nb[0]) | getSignal(nb[1]) | getSignal(nb[2]) | getSignal(nb[3]);
        }

        @Override
        public boolean getSignal(BinaryBlock.BinaryBuild to, BinaryBlock.BinaryBuild from){
            if(from instanceof BinaryNode.BinaryNodeBuild){
                if(((BinaryNode.BinaryNodeBuild) from).link == null) return super.getSignal(to, from);
            } else return super.getSignal(to, from);

            return false;
        }
    }
}
