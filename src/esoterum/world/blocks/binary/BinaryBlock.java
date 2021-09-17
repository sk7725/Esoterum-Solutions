package esoterum.world.blocks.binary;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.util.io.*;
import esoterum.content.*;
import esoterum.interfaces.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class BinaryBlock extends Block {
    public TextureRegion connectionRegion;
    public TextureRegion topRegion;
    public boolean drawConnection;
    public boolean emits = false;
    public boolean emitAllDirections;

    public BinaryBlock(String name){
        super(name);
        solid = true;
        update = true;
        destructible = true;
        drawDisabled = false;
        drawConnection = true;
        buildVisibility = BuildVisibility.sandboxOnly;
        alwaysUnlocked = true;
        category = Category.logic;
        conveyorPlacement = true;
        emitAllDirections = false;
    }

    @Override
    public void load(){
        super.load();
        region = Core.atlas.find("eso-binary-base");
        connectionRegion = Core.atlas.find("eso-connection");
        topRegion = Core.atlas.find(name + "-top");
    }

    @Override
    protected TextureRegion[] icons() {
        return new TextureRegion[]{
            region,
            topRegion,
            Core.atlas.find("eso-full-connections")
        };
    }

    @Override
    public boolean canReplace(Block other) {
        if(other.alwaysReplace) return true;
        return (other != this || rotate) && other instanceof BinaryBlock && size == other.size;
    }

    public class BinaryBuild extends Building implements Binaryc {
        public boolean lastSignal;
        public boolean nextSignal;
        // used for drawing
        public BinaryBuild[] nb = new BinaryBuild[]{null, null, null, null};

        @Override
        // this hurts me
        public void draw(){
            Draw.rect(region, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            if(drawConnection) for (BinaryBuild b : nb) {
                if(b == null || b.team != team) continue;
                if(!b.block.rotate || (b.front() == this || b.back() == this) || front() == b){
                    if(!(b.back() == this && front() != b) || !b.block.rotate){
                        Draw.rect(connectionRegion, x, y, relativeTo(b) * 90);
                    }
                }
            }
            Draw.rect(topRegion, x, y, rotdeg());
        }

        public boolean getSignal(BinaryBuild b){
            return getSignal(this, b);
        }

        public boolean emits(){
            return emits;
        }

        public boolean emitAllDirections(){
            return emitAllDirections;
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            nb[0] = validateNearby(back());
            nb[1] = validateNearby(left());
            nb[2] = validateNearby(right());
            nb[3] = validateNearby(front());
        }

        @Override
        public void displayBars(Table table) {
            super.displayBars(table);
            table.table(e -> {
                Runnable rebuild = () -> {
                    e.clearChildren();
                    e.row();
                    e.left();
                    e.label(() -> "State: " + (lastSignal ? "1" : "0")).color(Color.lightGray);
                };

                e.update(rebuild);
            }).left();
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void read(Reads read, byte revision){
            if(revision == 1){
                lastSignal = nextSignal = read.bool();
            }
        }

        @Override
        public void write(Writes write){
            write.bool(nextSignal);
        }

        @Override
        public double sense(LAccess sensor) {
            if(sensor == LAccess.enabled) return signal() ? 1 : 0;
            return super.sense(sensor);
        }
    }
}
