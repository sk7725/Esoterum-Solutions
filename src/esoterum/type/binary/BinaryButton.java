package esoterum.type.binary;

import arc.graphics.g2d.*;
import esoterum.content.*;

public class BinaryButton extends BinaryBlock{
    public float duration = 60f;

    public BinaryButton(String name){
        super(name);
        configurable = true;
        autoResetEnabled = false;
        rotate = false;
        emits = true;
        emitAllDirections = true;
      
        config(Boolean.class, (BinaryButtonBuild b, Boolean on) -> {
            b.lastSignal = on;
            b.timer = duration;
            EsoSounds.beep.at(b.x, b.y);
        });
    }

    public class BinaryButtonBuild extends BinaryBuild{
        public float timer;

        @Override
        public void updateTile(){
            super.updateTile();
            if((timer -= delta()) <= 0){
                lastSignal = false;
            }
        }
      
        @Override
        public boolean configTapped(){
            configure(true);
            return false;
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            if(drawConnection) for (int i = 0; i < 4; i++){
                Draw.rect(connectionRegion, x, y, 90 * i);
            }
            Draw.rect(topRegion, x, y);
        }
    }
}
