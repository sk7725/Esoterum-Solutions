package esoterum.type;

import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.ui.Styles;

public class DelayGate extends BinaryAcceptor{
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
            table.labelWrap("Delay: " + delay * 60 + " ticks / " + delay + " seconds").color(Color.lightGray);
        }

        @Override
        public void buildConfiguration(Table table) {
            table.setBackground(Styles.black5);
            table.button("-", () -> {
                delay -= 0.1f;
                delay = Math.round(delay * 10) / 10f;

                if (delay < 0.1f) delay = 0.1f;
            }).size(40);
            table.label(() -> delay + "s").labelAlign(Align.center)
                    .growX()
                    .fillX()
                    .center()
                    .size(80, 40);
            table.button("+", () -> {
                delay += 0.1f;
                delay = Math.round(delay * 10) / 10f;

                if (delay > 69f) delay = 69f;
            }).size(40);
            table.row();
            table.slider(0.1f, 69f, 0.1f, delay, (float newDelay) -> {
                delay = Math.round(newDelay * 10) / 10f;
            }).center().size(160, 40);
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
