package esoterum.type;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import esoterum.content.*;
import mindustry.ui.*;

public class DelayGate extends BinaryAcceptor{
    float minDelay = 0.1f, maxDelay = 60f;

    public DelayGate(String name){
        super(name);
        configurable = true;

        config(Float.class, (DelayGateBuild b, Float f) -> b.delay = f);
    }

    public class DelayGateBuild extends BinaryAcceptorBuild {
        public float delayTimer = 0f;
        public float delay = 1f;

        @Override
        public void updateTile() {
            if(signal()){
                if(delayTimer < delay * 60)delayTimer += Time.delta;
            }else{
                delayTimer = 0;
            }

            lastSignal = delayTimer > delay * 60&& nextSignal;
            nextSignal = signal();
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.color(EsoVars.connectionOffColor, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            Draw.rect(connectionRegion, x, y, rotdeg());
            Draw.rect(connectionRegion, x, y, rotdeg() - 180);
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
        public void displayBars(Table table) {
            super.displayBars(table);
            table.row();
            table.table(e -> {
                Runnable rebuild = () -> {
                    e.clearChildren();
                    e.row();
                    e.left();
                    e.label(() -> "Delay: " + delay * 60 + " ticks / " + delay + "s").color(Color.lightGray);
                };

                e.update(rebuild);
            }).left();
        }

        @Override
        public void buildConfiguration(Table table) {
            table.setBackground(Styles.black5);
            table.table(t -> {
                t.button("-", () -> {
                    delay -= 0.1f;
                    delay = Mathf.clamp(Math.round(delay * 10) / 10f, minDelay, maxDelay);
                }).size(40);
                TextField dField = t.field(delay + "s", s -> {
                    if(!s.isEmpty()){
                        s = s.replaceAll("[^\\d.]", ""); //God, I love google. I have no idea what the first part even is.
                        delay = Float.parseFloat(s);
                        delay = Mathf.clamp(delay, minDelay, maxDelay);
                    }
                }).labelAlign(Align.center)
                    .growX()
                    .fillX()
                    .center()
                    .size(80, 40)
                    .get();
                dField.update(() -> {
                    Scene stage = dField.getScene();
                    if(!(stage != null && stage.getKeyboardFocus() == dField)) dField.setText(delay + "s");
                });
                t.button("+", () -> {
                    delay += 0.1f;
                    delay = Mathf.clamp(Math.round(delay * 10) / 10f, minDelay, maxDelay);
                }).size(40);
            });
            table.row();
            Slider dSlider = table.slider(minDelay, maxDelay, 0.1f, delay, (float newDelay) -> {
                delay = Math.round(newDelay * 10) / 10f;
            }).center().size(160, 40).get();
            dSlider.update(() -> {
              dSlider.setValue(delay);
            });
        }

        @Override
        public Object config() {
            return delay;
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            if(revision == 1){
                delayTimer = read.f();
                delay = read.f();
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.f(delayTimer);
            write.f(delay);
        }
    }
}
