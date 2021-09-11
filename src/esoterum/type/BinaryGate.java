package esoterum.type;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import esoterum.content.EsoVars;

public class BinaryGate extends BinaryAcceptor {
    // left, back, right
    public boolean[] inputs = {true, false, true};
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
            Core.atlas.find("eso-gate-connections")
        };
    }

    public class BinaryGateBuild extends BinaryAcceptorBuild {
        public boolean[] results = new boolean[]{false, false, false};

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
            for(int i = 0; i < 3; i++){
                if(!inputs[i])continue;
                Draw.color(Color.white, EsoVars.connectionColor, lastSignal ? 1f : 0f);
                Draw.rect(connectionRegion, x, y, (90f + 90f * i) + rotdeg() );
            }
            Draw.color(Color.white, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            Draw.rect(topRegion, x, y, rotdeg());
            Draw.rect(connectionRegion, x, y, rotdeg() );
        }
    }
}
