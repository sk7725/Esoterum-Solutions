package esoterum.type;

import arc.math.*;
import arc.scene.ui.layout.Table;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.ui.Styles;

//BinaryCock
public class BinaryClock extends BinarySwitch {
    public BinaryClock(String name){
        super(name);
        config(Float.class, (BinaryClockBuild tile, Float frequency) -> tile.frequency = frequency);
    }

    public class BinaryClockBuild extends BinarySwitchBuild {
        public float frequency = 1f;

        @Override
        public void updateTile(){
            lastSignal = nextSignal;
            nextSignal = Mathf.sin(Time.time * (Mathf.PI2 / (60 / frequency))) >= 0f;
        }

        @Override
        public void created(){
            rotation(0);
        }

        @Override
        public Object config(){
            return frequency;
        }

        @Override
        public boolean configTapped(){
            return true;
        }

        @Override
        public void buildConfiguration(Table table) {
            table.setBackground(Styles.black5);
            table.button("-", () -> {
                frequency -= 0.1f;
                frequency = Math.round(frequency * 10) / 10f;

                if(frequency < 0.1f) frequency = 0.1f;
            }).size(40);
            table.label(() -> frequency + "hz").labelAlign(Align.center)
                    .growX()
                    .fillX()
                    .center()
                    .size(80, 40);
            table.button("+", () -> {
                frequency += 0.1f;
                frequency = Math.round(frequency * 10) / 10f;

                if(frequency > 69f) frequency = 69f;
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
                frequency = read.f();
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.bool(lastSignal);
            write.f(frequency);
        }
    }
}
