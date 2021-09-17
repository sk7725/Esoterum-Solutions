package esoterum.world.blocks.binary;

import arc.graphics.g2d.*;
import arc.util.io.*;
import esoterum.content.*;

public class BinarySwitch extends BinaryBlock {
    public BinarySwitch(String name){
        super(name);
        configurable = true;
        autoResetEnabled = false;
        emits = true;
        emitAllDirections = true;

        config(Boolean.class, (BinarySwitchBuild b, Boolean o) -> {
           b.output = o;
        });
    }

    public class BinarySwitchBuild extends BinaryBuild{
        public boolean output;

        @Override
        public void updateTile(){
            lastSignal = output;
        }
        
        @Override
        public boolean configTapped(){
            configure(!output);
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
        public Object config(){
            return output;
        }

        @Override
        public void created() {
            super.created();
            rotation(0);
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.bool(output);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            if(revision >= 2){
                output = read.bool();
            }
        }

        @Override
        public byte version(){
            return 2;
        }
    }
}
