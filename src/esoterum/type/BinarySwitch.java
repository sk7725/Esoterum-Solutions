package esoterum.type;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import esoterum.content.EsoSounds;
import esoterum.content.EsoVars;
import mindustry.gen.*;

public class BinarySwitch extends BinaryBlock {
    public BinarySwitch(String name){
        super(name);
        configurable = true;
        autoResetEnabled = false;
        rotate = false;
        emits = true;
        emitAllDirections = true;

        config(Boolean.class, (BinarySwitchBuild tile, Boolean value) -> tile.lastSignal = value);
    }

    public class BinarySwitchBuild extends BinaryBuild {
        @Override
        public boolean configTapped(){
            configure(!lastSignal);
            EsoSounds.beep.at(x, y);
            return false;
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            if(drawConnection) for (int i = 0; i < 4; i++) {
                Draw.rect(connectionRegion, x, y, 90 * i);
            }
            Draw.rect(topRegion, x, y);
        }

        @Override
        public Object config(){
            return lastSignal;
        }
    }
}
