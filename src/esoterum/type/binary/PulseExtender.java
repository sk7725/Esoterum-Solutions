package esoterum.type.binary;

import arc.*;
import arc.util.*;

public class PulseExtender extends DelayGate{
    public PulseExtender(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();

        clock = Core.atlas.find("clear");
    }

    public class PulseExtenderBuild extends DelayGateBuild{
        @Override
        public void updateTile(){
            lastSignal = delayTimer > 0f || signal();
            nextSignal = signal();

            if(signal()){
                delayTimer = delay();
            }else{
                delayTimer -= Time.delta;
            }
        }
    }
}
