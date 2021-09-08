package esoterum.type;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.struct.Seq;
import esoterum.interfaces.*;
import mindustry.gen.*;
import mindustry.world.*;

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
    }

    @Override
    public void load() {
        super.load();
        region = Core.atlas.find("eso-binary-base");
        connectionRegion = Core.atlas.find("eso-connection");
        topRegion = Core.atlas.find(name + "-top");
    }

    public class BinaryBuild extends Building implements Binaryc {
        public boolean lastSignal;
        public boolean nextSignal;
        public Seq<BinaryBuild> connected = new Seq<>(4);
        public BinaryBuild frontBuild = null;

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.color(Color.white, Color.green, lastSignal ? 1f : 0f);
            Draw.rect(topRegion, x, y, rotation * 90f);
            if(drawConnection){
                for(BinaryBlock.BinaryBuild other : connected){
                    Draw.rect(connectionRegion, x, y, relativeTo(other) * 90f);
                }
            }
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            frontBuild = (BinaryBuild) front();
            getConnected((BinaryBuild) tile.build);

        }
    }
}
