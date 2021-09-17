package esoterum.world.blocks.binary;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import esoterum.content.*;

public class WaitGate extends DelayGate{
    public WaitGate(String name){
        super(name);
    }

    public class WaitGateBuild extends DelayGateBuild{
        public boolean count;
        public float outputTime, outputTimer;

        @Override
        public void updateTile(){
            lastSignal = delayTimer >= delay();
            nextSignal = signal();

            if(signal()){
                count = true;
                outputTime = Mathf.clamp(outputTime + Time.delta, 0f, delay());
            }

            if(count && delayTimer < delay()){
                delayTimer += Time.delta;
            }else if(!signal()){
                outputTimer += Time.delta;
                if(outputTimer >= outputTime){
                    delayTimer = 0;
                    outputTimer = 0;
                    outputTime = 0;
                    count = false;
                }
            }
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            Draw.rect(connectionRegion, x, y, rotdeg());
            Draw.rect(topRegion, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, count ? 1f : 0f);
            Draw.rect(clock, x, y);
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.f(outputTime);
            write.f(outputTimer);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            outputTime = read.f();
            outputTimer = read.f();
        }
    }
}
