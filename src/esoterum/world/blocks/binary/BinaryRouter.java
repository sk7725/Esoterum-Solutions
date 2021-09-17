package esoterum.world.blocks.binary;

import arc.*;
import arc.graphics.g2d.*;
import esoterum.content.*;

public class BinaryRouter extends BinaryAcceptor{
    public BinaryRouter(String name){
        super(name);
        rotate = false;
        emits = true;
        drawConnection = false;
        emitAllDirections = true;
    }

    @Override
    public void load() {
        super.load();
        connectionRegion = Core.atlas.find("eso-full-connections");
    }

    public class BinaryRouterBuild extends BinaryAcceptorBuild {
        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            Draw.rect(connectionRegion, x, y);
            Draw.rect(topRegion, x, y);
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
