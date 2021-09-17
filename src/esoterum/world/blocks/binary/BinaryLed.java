package esoterum.world.blocks.binary;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class BinaryLed extends BinaryAcceptor {
    public boolean[] inputs = {true, true, true};
    public BinaryLed(String name){
        super(name);
        rotate = true;
        drawArrow = false;
        emits = true;
        drawConnection = false;
        configurable = true;

        config(Integer.class, (BinaryLedBuild tile, Integer value) -> tile.color = new Color(value));
    }

    @Override
    protected TextureRegion[] icons() {
        return new TextureRegion[]{
            region,
            topRegion,
            Core.atlas.find("eso-full-connections")
        };
    }

    public class BinaryLedBuild extends BinaryAcceptorBuild {
        public Color color = Color.white;

        @Override
        public void buildConfiguration(Table table){
            table.button(Icon.pencil, () -> {
                ui.picker.show(Tmp.c1.set(color), false, res -> configure(res.rgba()));
                deselect();
            }).size(40f);
        }

        @Override
        public void updateTile(){
            super.updateTile();
            lastSignal = nextSignal;
            nextSignal = signal();
        }

        @Override
        public boolean signal(){
            return getSignal(nb[1]) | getSignal(nb[0]) | getSignal(nb[2]);
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.color(new Color(getSignal(nb[1]) ? color.r : 0f, getSignal(nb[0]) ? color.g : 0f, getSignal(nb[2]) ? color.b : 0f));
            Draw.rect(topRegion, x, y, rotdeg());
        }

        @Override
        public boolean signalFront() {
            return nb[0] != null && nb[0].lastSignal;
        }

        @Override
        public Number config(){
            return color.rgba();
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(color.rgba());
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            color = new Color(read.i());
        }

    }
}
