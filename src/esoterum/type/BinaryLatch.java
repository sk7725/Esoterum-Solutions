package esoterum.type;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import esoterum.content.EsoVars;
import mindustry.logic.LAccess;

public class BinaryLatch extends BinaryGate{
    public TextureRegion latchRegion;
    public BinaryLatch(String name){
        super(name);
        inputs = new boolean[]{true, false, true};
    }

    @Override
    public void load() {
        super.load();
        latchRegion = Core.atlas.find(name + "-latch");
    }

    @Override
    protected TextureRegion[] icons() {
        return new TextureRegion[]{
                region,
                topRegion,
                latchRegion
        };
    }

    public class BinaryLatchBuild extends BinaryGateBuild{
        public boolean store = false;

        @Override
        public void updateTile() {
            lastSignal = getSignal(nb[1]) | getSignal(nb[2]);
            store = signal();
        }

        @Override
        public boolean signal() {
            if(getSignal(nb[1]))return getSignal(nb[2]);
            return store;
        }

        @Override
        public boolean signalFront() {
            return store;
        }

        @Override
        public void draw() {
            super.draw();
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, store ? 1f : 0f);
            Draw.rect(latchRegion, x, y);
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void read(Reads read, byte revision){
            if(revision == 1){
                store = read.bool();
            }
        }

        @Override
        public void write(Writes write){
            write.bool(store);
        }

        @Override
        public double sense(LAccess sensor) {
            if(sensor == LAccess.enabled) return store ? 1 : 0;
            return super.sense(sensor);
        }
    }
}
