package esoterum.type.binary;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import esoterum.content.*;
import esoterum.util.*;
import mindustry.ui.*;

public class DelayGate extends BinaryAcceptor{
    public int maxSec = 60;

    public DelayGate(String name){
        super(name);
        configurable = true;

        config(Float.class, (DelayGateBuild b, Float delay) -> {
            float d = Mathf.floor(delay);
            b.delaySec = (int)d;
            b.delayTick = (int)((delay - d) * 60);
        });

        config(IntSeq.class, (DelayGateBuild b, IntSeq i) -> {
            b.delaySec = i.get(0);
            b.delayTick = i.get(1);
        });
    }

    @Override
    public void load() {
        super.load();

        connectionRegion = Core.atlas.find("eso-not-connections");
    }

    @Override
    protected TextureRegion[] icons() {
        return new TextureRegion[]{
            region,
            topRegion,
            connectionRegion
        };
    }

    public class DelayGateBuild extends BinaryAcceptorBuild {
        public float delayTimer = 0f;
        public int delaySec = 1, delayTick;

        @Override
        public void updateTile(){
            lastSignal = delayTimer > delay() && nextSignal;
            nextSignal = signal();

            if(signal()){
                if(delayTimer < delay()) delayTimer += Time.delta;
            }else{
                delayTimer = 0;
            }
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            Draw.rect(connectionRegion, x, y, rotdeg());
            Draw.rect(topRegion, x, y);
        }

        @Override
        public boolean signal() {
            return getSignal(nb[0]);
        }

        @Override
        public boolean signalFront() {
            return lastSignal;
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
                    e.label(() -> "Delay: " +
                        (delaySec > 0 ? EsoUtils.pluralValue(delaySec, "second") : "") +
                        (delaySec > 0 && delayTick > 0 ? " + " : delaySec == 0 && delayTick == 0 ? "None" : "") +
                        (delayTick > 0 ? EsoUtils.pluralValue(delayTick, "tick") : "")
                    ).color(Color.lightGray);
                };

                e.update(rebuild);
            }).left();
        }

        @Override
        public void buildConfiguration(Table table) {
            table.setBackground(Styles.black5);
            table.table(a -> {
                a.table(t -> {
                    t.button("-", () -> {
                        delaySec--;
                        delaySec = Math.max(delaySec, 0);
                    }).size(40);
                    TextField dField = t.field(delaySec + "s", s -> {
                            s = EsoUtils.extractNumber(s);
                            if(!s.isEmpty()){
                                delaySec = Mathf.floor(Float.parseFloat(s));
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
                            dField.setText(delaySec + "s");
                    });
                    t.button("+", () -> {
                        delaySec++;
                        delaySec = Math.min(delaySec, maxSec);
                    }).size(40);
                });
                a.row();
                Slider dSlider = a.slider(0, maxSec, 1, delaySec, newDelay -> delaySec = (int)newDelay).center().size(160, 40).get();
                dSlider.update(() -> dSlider.setValue(delaySec));
            });
            table.row();
            table.add("+");
            table.row();
            table.table(b -> {
                b.table(t -> {
                    t.button("-", () -> {
                        delayTick--;
                        delayTick = Math.max(delayTick, 0);
                    }).size(40);
                    TextField dField = t.field(delayTick + "s", s -> {
                            s = EsoUtils.extractNumber(s);
                            if(!s.isEmpty()){
                                delayTick = Mathf.floor(Float.parseFloat(s));
                                delayTick = Mathf.clamp(delayTick, 0, 60);
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
                            dField.setText(delayTick + "t");
                    });
                    t.button("+", () -> {
                        delayTick++;
                        delayTick = Math.min(delayTick, 60);
                    }).size(40);
                });
                b.row();
                Slider dSlider = b.slider(0, 60, 1, delaySec, newDelay -> delayTick = (int)newDelay).center().size(160, 40).get();
                dSlider.update(() -> dSlider.setValue(delayTick));
            });
        }

        public int delay(){
            return delaySec * 60 + delayTick;
        }

        @Override
        public Object config() {
            return IntSeq.with(delaySec, delayTick);
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.f(delayTimer);
            write.i(delaySec);
            write.i(delayTick);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            if(revision == 1){
                delayTimer = read.f();

                float delay = read.f();
                float d = Mathf.floor(delay);
                delaySec = (int)d;
                delayTick = (int)((delay - d) * 60);
            }
            if(revision >= 2){
                delayTimer = read.f();
                delaySec = read.i();
                delayTick = read.i();
            }
        }

        @Override
        public byte version() {
            return 2;
        }
    }
}
