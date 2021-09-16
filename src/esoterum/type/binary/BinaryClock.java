package esoterum.type.binary;

import arc.graphics.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import esoterum.util.*;
import mindustry.ui.*;

//BinaryCock
public class BinaryClock extends BinarySwitch {
    public BinaryClock(String name){
        super(name);

        config(Float.class, (BinaryClockBuild b, Float interval) -> {
            float i = Mathf.floor(interval);
            b.intervalSec = (int)i;
            b.intervalTick = (int)((interval - i) * 60);
        });

        config(IntSeq.class, (BinaryClockBuild tile, IntSeq interval) -> {
            tile.intervalSec = interval.get(0);
            tile.intervalTick = interval.get(1);
        });
    }

    public class BinaryClockBuild extends BinarySwitchBuild {
        public int intervalSec = 1, intervalTick;

        @Override
        public void updateTile(){
            lastSignal = nextSignal;
            nextSignal = Mathf.sin(Time.time * (Mathf.PI2 / interval())) >= 0f;
        }

        @Override
        public void displayBars(Table table){
            super.displayBars(table);
            table.row();
            table.table(e -> {
                Runnable rebuild = () -> {
                    e.clearChildren();
                    e.row();
                    e.left();
                    e.label(() -> "Interval: " +
                        (intervalSec > 0 ? EsoUtils.pluralValue(intervalSec, "second") : "") +
                        (intervalSec > 0 && intervalTick > 0 ? " + " : intervalSec == 0 && intervalTick == 0 ? "None" : "") +
                        (intervalTick > 0 ? EsoUtils.pluralValue(intervalTick, "tick") : "")
                    ).color(Color.lightGray);
                };

                e.update(rebuild);
            }).left();
        }

        public int interval(){
            return 60 * intervalSec + intervalTick;
        }

        @Override
        public void created(){
            rotation(0);
        }

        @Override
        public Object config(){
            return IntSeq.with(intervalSec, intervalTick);
        }

        @Override
        public boolean configTapped(){
            return true;
        }

        @Override
        public void buildConfiguration(Table table) {
            table.setBackground(Styles.black5);
            table.table(a -> {
                a.table(t -> {
                    t.button("-", () -> {
                        intervalSec--;
                        intervalSec = Math.max(intervalSec, 0);
                    }).size(40);
                    TextField dField = t.field(intervalSec + "s", s -> {
                            s = EsoUtils.extractNumber(s);
                            if(!s.isEmpty()){
                                intervalSec = Mathf.floor(Float.parseFloat(s));
                            }
                        }).labelAlign(Align.center)
                        .growX()
                        .fillX()
                        .center()
                        .size(80, 40)
                        .get();
                    dField.update(() -> {
                        Scene stage = dField.getScene();
                        if(!(stage != null && stage.getKeyboardFocus() == dField))
                            dField.setText(intervalSec + "s");
                    });
                    t.button("+", () -> {
                        intervalSec++;
                    }).size(40);
                });
            });
            table.row();
            table.add("+");
            table.row();
            table.table(b -> {
                b.table(t -> {
                    t.button("-", () -> {
                        intervalTick--;
                        intervalTick = Math.max(intervalTick, 0);
                    }).size(40);
                    TextField dField = t.field(intervalTick + "s", s -> {
                            s = EsoUtils.extractNumber(s);
                            if(!s.isEmpty()){
                                intervalTick = Mathf.floor(Float.parseFloat(s));
                                intervalTick = Mathf.clamp(intervalTick, 0, 60);
                            }
                        }).labelAlign(Align.center)
                        .growX()
                        .fillX()
                        .center()
                        .size(80, 40)
                        .get();
                    dField.update(() -> {
                        Scene stage = dField.getScene();
                        if(!(stage != null && stage.getKeyboardFocus() == dField))
                            dField.setText(intervalTick + "t");
                    });
                    t.button("+", () -> {
                        intervalTick++;
                        intervalTick = Math.min(intervalTick, 60);
                    }).size(40);
                });
                b.row();
            });
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.bool(lastSignal);
            write.i(intervalSec);
            write.i(intervalTick);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            if(revision == 1){
                nextSignal = lastSignal = read.bool();

                float interval = read.f();
                float i = Mathf.floor(interval);
                intervalSec = (int)i;
                intervalTick = (int)((interval - i) * 60);
            }
            if(revision >= 2){
                nextSignal = lastSignal = read.bool();
                intervalSec = read.i();
                intervalTick = read.i();
            }
        }

        @Override
        public byte version() {
            return 2;
        }
    }
}
