package esoterum.type.binary;

import arc.*;
import arc.graphics.g2d.*;
import esoterum.content.*;

public class BinaryLock extends BinaryAcceptor{
    public TextureRegion lock, side;

    public BinaryLock(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();
        connectionRegion = Core.atlas.find("eso-full-connections");
        lock = Core.atlas.find(name + "-lock");
        side = Core.atlas.find(name + "-side");
    }

    public class BinaryLockBuild extends BinaryAcceptorBuild{
        @Override
        public void updateTile(){
            if(!locked()){
                lastSignal = signal();
            }
            nextSignal = signal();
        }

        @Override
        public boolean signal(){
            return getSignal(nb[0]);
        }

        @Override
        public boolean signalFront(){
            return lastSignal;
        }

        public boolean locked(){
            return getSignal(nb[1]) || getSignal(nb[2]);
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            Draw.rect(connectionRegion, x, y, rotdeg());
            Draw.rect(topRegion, x, y, rotdeg());
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, locked() ? 1f : 0f);
            Draw.rect(lock, x, y, rotdeg());
        }
    }
}
