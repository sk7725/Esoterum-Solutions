package esoterum.type;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import mindustry.gen.Building;

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
    
    public class BinaryGateBuild extends BinaryAcceptorBuild {
        public boolean[] results = new boolean[]{false, false, false};

        @Override
        public boolean signal(){
            results[0] = sLeft();
            results[1] = sBack();
            results[2] = sRight();
            return operation(results);
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            for(int i = 0; i < 3; i++){
                if(!inputs[i])continue;
                Draw.color(Color.white, Color.green, lastSignal ? 1f : 0f);
                Draw.rect(connectionRegion, x, y, (90f + 90f * i) + rotdeg() );
            }
            Draw.color(Color.white, Color.green, lastSignal ? 1f : 0f);
            Draw.rect(topRegion, x, y, rotdeg());
            Draw.rect(connectionRegion, x, y, rotdeg() );
        }
    }
}
