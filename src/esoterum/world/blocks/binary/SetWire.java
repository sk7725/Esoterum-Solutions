package esoterum.world.blocks.binary;

import arc.*;
import arc.graphics.g2d.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.io.*;
import esoterum.content.*;
import mindustry.gen.*;
import mindustry.ui.*;

public class SetWire extends BinaryAcceptor{
    public SetWire(String name){
        super(name);
        configurable = true;

        config(byte[].class, (SetWireBuild b, byte[] i) -> {
           b.inputs = new boolean[]{
               i[0] == 1,
               i[1] == 1,
               i[2] == 1
           };
        });

        config(Integer.class, (SetWireBuild b, Integer i) -> {
            b.inputs[i] = !b.inputs[i];
        });
    }

    @Override
    public void load(){
        super.load();
        connectionRegion = Core.atlas.find("eso-connection-large");
    }

    @Override
    protected TextureRegion[] icons() {
        return new TextureRegion[]{
            region,
            topRegion,
            Core.atlas.find("eso-connection-across")
        };
    }

    public class SetWireBuild extends BinaryAcceptorBuild{
        public boolean[] inputs = new boolean[]{false, true, false};
        public int mode;

        @Override
        public void updateTile(){
            lastSignal = signal();
            nextSignal = signal();
        }

        @Override
        public boolean signal(){
            boolean
                left = inputs[0] && getSignal(nb[1]),
                back = inputs[1] && getSignal(nb[0]),
                right = inputs[2] && getSignal(nb[2]);
            return left || back || right;
        }

        @Override
        public boolean signalFront(){
            return lastSignal;
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            for(int i = 0; i < 3; i++){
                if(!inputs[i])continue;
                Draw.rect(connectionRegion, x, y, (90f + 90f * i) + rotdeg());
            }
            Draw.rect(connectionRegion, x, y, rotdeg());
            Draw.rect(topRegion, x, y, rotdeg());
        }

        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            table.setBackground(Styles.black5);
            String[] letters = {"L", "B", "R"};
            for(int i = 0; i < 3; i++){
                int ii = i;
                TextButton button = table.button(letters[i], () -> configure(ii)).size(40).get();
                button.getStyle().checked = Tex.buttonOver;
                button.update(() -> button.setChecked(inputs[ii]));
            }
        }

        @Override
        public byte[] config(){
            return new byte[]{
                (byte)(inputs[0] ? 1 : 0),
                (byte)(inputs[1] ? 1 : 0),
                (byte)(inputs[2] ? 1 : 0)
            };
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.i(mode);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            mode = read.i();
        }
    }
}
