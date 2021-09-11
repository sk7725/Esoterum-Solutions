package esoterum.type;

import arc.scene.ui.layout.Table;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.ui.Styles;

public class BinaryClock extends BinarySwitch {
    public BinaryClock(String name){
        super(name);
        config(Float.class, (BinaryClockBuild tile, Float frequency) -> tile.frequency = frequency);
    }

    public class BinaryClockBuild extends BinarySwitchBuild {
        public float frequency = 1f;
        public float tTimer = 0f;

        @Override
        public void updateTile(){
            lastSignal = nextSignal;
            if(checkTimer()){
                nextSignal = !nextSignal;
                resetTimer();
            }
        }

        @Override
        public void created(){
            resetTimer();
        }

        public boolean checkTimer(){
            return Time.time >= tTimer;
        }

        public void resetTimer(){
            tTimer = Time.time + ((60 / frequency) / 2);
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
                if(frequency > 1)frequency--;
            }).size(40);
            table.label(() -> frequency + "hz")
                    .growX()
                    .fillX()
                    .center()
                    .size(80, 40);
            table.button("+", () -> {
                frequency++;
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
