package esoterum.type;

import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.scene.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.ui.*;

//BinaryCock
public class BinaryClock extends BinarySwitch {
    public BinaryClock(String name){
        super(name);
        config(Float.class, (BinaryClockBuild tile, Float interval) -> tile.interval = interval);
    }

    public class BinaryClockBuild extends BinarySwitchBuild {
        public float interval = 1f; //In seconds

        @Override
        public void updateTile(){
            lastSignal = nextSignal;
            nextSignal = Mathf.sin(Time.time * (Mathf.PI2 / (60 * interval))) >= 0f;
        }

        @Override
        public void created(){
            rotation(0);
        }

        @Override
        public Object config(){
            return interval;
        }

        @Override
        public boolean configTapped(){
            return true;
        }

        @Override
        public void buildConfiguration(Table table) {
            table.setBackground(Styles.black5);
            table.button("-", () -> {
                interval -= 0.1f;
                interval = Math.round(interval * 10f) / 10f;

                if(interval < 0.1f) interval = 0.1f;
            }).size(40);
            TextField iField = table.field(interval + "s", s -> {
                    if(!s.isEmpty()){
                        s = s.replaceAll("[^\\d.]", ""); //God, I love google. I have no idea what the first part even is.
                        interval = Float.parseFloat(s);
                    }
                }).labelAlign(Align.center)
                .growX()
                .fillX()
                .center()
                .size(80, 40)
                .get();
            iField.update(() -> {
                Scene stage = iField.getScene();
                if(!(stage != null && stage.getKeyboardFocus() == iField)) iField.setText(Strings.autoFixed(interval, 2) + "s");
            });
            table.button("+", () -> {
                interval += 0.1f;
                interval = Math.round(interval * 10f) / 10f;
            }).size(40);
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            if(revision == 1){
                nextSignal = lastSignal = read.bool();
                interval = read.f();
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.bool(lastSignal);
            write.f(interval);
        }
    }
}
