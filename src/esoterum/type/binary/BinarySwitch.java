package esoterum.type.binary;

import arc.graphics.g2d.Draw;
import esoterum.content.EsoSounds;
import esoterum.content.EsoVars;

public class BinarySwitch extends BinaryBlock {
    public BinarySwitch(String name){
        super(name);
        configurable = true;
        autoResetEnabled = false;
        emits = true;
        emitAllDirections = true;
    }

    public class BinarySwitchBuild extends BinaryBuild {
        @Override
        public void placed() {
            enabled = false;
        }

        @Override
        public void updateTile(){
            lastSignal = enabled;
        }
        
        @Override
        public boolean configTapped(){
            enabled = !enabled;
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
            Draw.rect(topRegion, x, y, rotdeg());
        }

        @Override
        public void created() {
            super.created();
            rotation(0);
        }
    }
}
