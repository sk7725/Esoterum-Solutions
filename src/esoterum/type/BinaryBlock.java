package esoterum.type;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import esoterum.interfaces.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class BinaryBlock extends Block {
    public TextureRegion connectionRegion;
    public TextureRegion topRegion;
    public boolean drawConnection;

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

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            if(drawConnection) for (Building b : new Building[]{back(), left(), right(), front()}) {
                if(b == null || !(b.block instanceof BinaryBlock) || b.team != team )continue;
                Draw.color(Color.white, Color.green, (b == front() && lastSignal) || (((BinaryBuild) b).lastSignal && (b.front() == this || !b.block.rotate)) ? 1f : 0f);
                Draw.rect(connectionRegion, x, y, relativeTo(b) * 90);
            }
            Draw.color(Color.white, Color.green, lastSignal ? 1f : 0f);
            Draw.rect(topRegion, x, y, rotation * 90f);
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

    }
}
