package esoterum.type;

import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class BinaryJunction extends BinaryBlock{
    public BinaryJunction(String name){
        super(name);
        emits = false;
        rotate = false;
        drawConnection = false;
    }

    public class BinaryJunctionBuild extends BinaryBuild {
        public Seq<Building> builds = new Seq<>(8);

        @Override
        public boolean signal() {
            return false;
        }

        @Override
        public void draw() {
            super.draw();
        }

        @Override
        public void updateTile() {
            lastSignal = false;
            for(int i = 0; i < 8; i += 2){
                Building a = builds.get(i);
                Building b = builds.get(i + 1);
                if(canSignal(b, a)){
                    ((BinaryBuild) b).signalOverride = ((BinaryBuild) a).signal();
                    lastSignal |= ((BinaryBuild) a).signal();
                }
            }
        }

        @Override
        public boolean canSignal(Building to, Building from) {
            return from != null && to != null
                    && from.team == to.team
                    && ((BinaryBuild)from).emits()
                    && (!to.block.rotate || to.front() != this)
                    && (!from.block.rotate || from.front() == this);
        }

        @Override
        public void onProximityUpdate() {
            noSleep();
            builds.clear();
            for(int i = 0; i < 4; i++){
                Building a = nearby(i);
                Building b = nearby((i + 2) % 4);
                builds.add(a instanceof BinaryBuild ? a : null);
                builds.add(b instanceof BinaryBuild ? b : null);
            }
            Log.info("---------------");
            Log.info(builds);
        }
    }
}
