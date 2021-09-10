package esoterum.type;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.io.*;
import esoterum.interfaces.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class BinaryBlock extends Block {
    public TextureRegion connectionRegion;
    public TextureRegion topRegion;
    public boolean drawConnection;
    public boolean emits = false;

    public BinaryBlock(String name){
        super(name);
        solid = true;
        update = true;
        destructible = true;
        drawDisabled = false;
        drawConnection = true;
        buildVisibility = BuildVisibility.shown;
    }

    @Override
    public void load(){
        super.load();
        region = Core.atlas.find("eso-binary-base");
        connectionRegion = Core.atlas.find("eso-connection");
        topRegion = Core.atlas.find(name + "-top");
    }

    public class BinaryBuild extends Building implements Binaryc {
        public boolean lastSignal;
        public boolean nextSignal;
        // used for overriding signals, will come in useful for junctions and other stuff
        public boolean signalOverride;
        // used for drawing
        public Building[] nb = new Building[]{null, null, null, null};

        @Override
        // this hurts me
        public void draw(){
            Draw.rect(region, x, y);
            if(drawConnection) for (Building b : nb) {
                if(!(b instanceof BinaryBuild) || b.team != team) continue;
                if(!b.block.rotate || (b.front() == this || b.back() == this) || front() == b){
                    if(!(b.back() == this && front() != b) || !b.block.rotate){
                        Draw.color(Color.white, Color.green, ((BinaryBuild) b).lastSignal ? 1f : 0f);
                        Draw.rect(connectionRegion, x, y, relativeTo(b) * 90);
                    }
                }
            }
            Draw.color(Color.white, Color.green, lastSignal ? 1f : 0f);
            Draw.rect(topRegion, x, y, rotdeg());
        }

        public boolean sLeft(){
            return canSignal(this, left()) && ((BinaryBuild) left()).lastSignal;
        }

        public boolean sRight(){
            return canSignal(this, right()) && ((BinaryBuild) right()).lastSignal;
        }

        public boolean sBack(){
            return canSignal(this, back()) && ((BinaryBuild) back()).lastSignal;
        }

        public boolean sFront(){
            return canSignal(this, front()) && ((BinaryBuild) front()).lastSignal;
        }

        public boolean sNearby(int rot){
            int side = (rot + rotation) % 4;
            if(nearby(side) == null)return false;
            return canSignal(this, nearby(side)) && ((BinaryBuild) nearby(side)).lastSignal;
        }

        public boolean emits(){
            return emits;
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            nb[0] = back();
            nb[1] = left();
            nb[2] = right();
            nb[3] = front();
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void read(Reads read, byte revision){
            if(revision == 1){
                lastSignal = read.bool();
            }
        }

        @Override
        public void write(Writes write){
            write.bool(lastSignal);
        }
    }
}
