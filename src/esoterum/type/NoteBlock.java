package esoterum.type;

import arc.audio.*;
import arc.scene.ui.layout.Table;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import esoterum.content.EsoSounds;
import mindustry.graphics.*;
import mindustry.ui.Styles;

public class NoteBlock extends BinaryAcceptor {
    public NoteBlock(String name){
        super(name);
        configurable = true;

        config(Integer.class, (NoteBlockBuild b, Integer note) -> {
            b.note = note;
        });
    }

    public class NoteBlockBuild extends BinaryAcceptorBuild {
        public Sound noteSound = EsoSounds.bell;
        public String[] notes = new String[]{
                "C4", "C4#", "D4",
                "D4#", "E4", "F4",
                "F4#", "G44", "G4#",
                "A4", "A4#", "B4",
                "C5"
        };
        public int note = 0;
        public float volume = 1f;
        // i don't know
        public boolean prev;

        @Override
        public void updateTile() {
            prev = lastSignal;
            lastSignal = nextSignal | getSignal(nb[0]);
            nextSignal = signal();
            if(lastSignal & prev != lastSignal) playSound();
        }

        public void playSound(){
            noteSound.play(volume, 1f + note / 12f, 0);
            Log.info("sound played");
        }

        @Override
        public void buildConfiguration(Table table) {
            table.setBackground(Styles.black5);
            table.button("-", () -> {
                note--;
                if(note < 0)note = 12;
            }).size(40);
            table.label(() -> notes[note]).labelAlign(Align.center)
                    .growX()
                    .fillX()
                    .center()
                    .size(80, 40);
            table.button("+", () -> {
                note++;
                if(note > 12)note = 0;
            }).size(40);
        }

        @Override
        public Object config() {
            return note;
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            if(nb[3] != null)Drawf.arrow(x, y, nb[3].x, nb[3].y, 2f, 2f, Pal.accent);
            if(nb[0] != null)Drawf.arrow(nb[0].x, nb[0].y, x, y, 2f, 2f, Pal.accent);
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            if(revision == 1){
                note = read.i();
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.i(note);
        }
    }
}
