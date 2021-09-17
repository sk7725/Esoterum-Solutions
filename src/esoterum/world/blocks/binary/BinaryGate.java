package esoterum.world.blocks.binary;

import arc.*;
import arc.graphics.g2d.*;
import esoterum.content.*;

public class BinaryGate extends BinaryAcceptor {
    /** left, back, right */
    public boolean[] inputs = {true, false, true};
    public String variant = null;
    public boolean drawSides = true;
    public TextureRegion variantRegion;
    public TextureRegion[] sides = new TextureRegion[3];

    public BinaryGate(String name){
        super(name);
    }

    // Override when using
    public boolean operation(boolean[] in){
        // AND
        return in[0] && in[2];
    }

    @Override
    protected TextureRegion[] icons() {
        return new TextureRegion[]{
            region,
            topRegion,
            Core.atlas.find("eso-gate-connections"),
            variant != null ? variantRegion : Core.atlas.find("clear")
        };
    }

    @Override
    public void load() {
        super.load();
        variantRegion = Core.atlas.find("eso-variant-" + variant);
        for(int i = 0; i < 2; i++){
            sides[i] = Core.atlas.find(name + "-side" + i);
        }
    }

    public class BinaryGateBuild extends BinaryAcceptorBuild {
        public boolean[] results = new boolean[]{false, false, false};

        @Override
        public void updateTile() {
            lastSignal = nextSignal;
            nextSignal = signal();
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            if(variant != null){
                Draw.rect(variantRegion, x, y);
            }
        }

        @Override
        public boolean signal(){
            results[0] = getSignal(nb[1]);
            results[1] = getSignal(nb[0]);
            results[2] = getSignal(nb[2]);
            return operation(results);
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            for(int i = 0; i < 3; i++){
                if(!inputs[i])continue;
                Draw.rect(connectionRegion, x, y, (90f + 90f * i) + rotdeg());
            }
            Draw.rect(topRegion, x, y, rotdeg());
            if(drawSides){
                int j = 0;
                for(int i = 0; i < 3; i++){
                    if(!inputs[i]) continue;
                    Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, results[i] ? 1f : 0f);
                    Draw.rect(sides[j], x, y, rotdeg());
                    j++;
                }
            }
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            Draw.rect(connectionRegion, x, y, rotdeg() );
        }

        @Override
        public boolean signalFront() {
            return lastSignal;
        }
    }
}
