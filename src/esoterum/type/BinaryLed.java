package esoterum.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.*;
import mindustry.graphics.*;

public class BinaryLed extends BinaryAcceptor {
    public boolean[] inputs = {true, true, true};
    public BinaryLed(String name){
        super(name);
        rotate = true;
        drawArrow = false;
        emits = false;
        drawConnection = false;
    }

    @Override
    protected TextureRegion[] icons() {
        return new TextureRegion[]{
            region,
            topRegion,
            Core.atlas.find("eso-gate-connections")
        };
    }

    public class BinaryLedBuild extends BinaryAcceptorBuild {

        @Override
        public void updateTile(){
            super.updateTile();
            lastSignal = nextSignal;
            nextSignal = signal();
        }

        @Override
        public boolean signal(){
            return false;
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.color(Tmp.c1.set(getSignal(nb[1]) ? 1f : 0f, getSignal(nb[0]) ? 1f : 0f, getSignal(nb[2]) ? 1f : 0f));
            Draw.rect(topRegion, x, y, rotdeg());
        }
    }
}
