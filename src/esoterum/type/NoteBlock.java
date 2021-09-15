package esoterum.type;

import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.Draw;
import arc.math.geom.*;
import arc.scene.ui.layout.Table;
import arc.struct.IntSeq;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import esoterum.content.EsoSounds;
import esoterum.content.EsoVars;
import mindustry.graphics.*;
import mindustry.ui.Styles;

public class NoteBlock extends BinaryAcceptor {
    public NoteBlock(String name){
        super(name);
        configurable = true;

        config(IntSeq.class, (NoteBlockBuild tile, IntSeq ints) -> {
            tile.note = ints.get(0);
            tile.volume = ints.get(1);
            tile.noteOctave = ints.get(2);
            tile.noteSample = ints.get(3);
        });
    }

    public class NoteBlockBuild extends BinaryAcceptorBuild {
        public Sound[][] noteSamples = new Sound[][]{EsoSounds.bells};

        public int note = 0;
        public int volume = 10;
        public int noteSample = 0;
        public int noteOctave = 2;

        public String[] notes = new String[]{
                "C%o", "C%o#", "D%o",
                "D%o#", "E%o", "F%o",
                "F%o#", "G%o", "G%o#",
                "A%o", "A%o#", "B%o"
        };

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
            noteSamples[noteSample][noteOctave].play((float) volume / 10f, 1f + note / 12f, 0);
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
                    e.labelWrap("Note: " + String.format(notes[note], noteOctave + 2)).color(Color.lightGray);
                };

                e.update(rebuild);
            }).left();
        }

        @Override
        public void buildConfiguration(Table table) {
            table.setBackground(Styles.black5);
            table.button("-", () -> {
                note--;
                if(note < 0){
                    note = 11;
                    noteOctave--;
                    if(noteOctave < 0)noteOctave = 4;
                }
            }).size(40);
            table.label(() -> String.format(notes[note], noteOctave + 2)).labelAlign(Align.center)
                    .growX()
                    .fillX()
                    .center()
                    .size(80, 40);
            table.button("+", () -> {
                note++;
                if(note > 11){
                    note = 0;
                    noteOctave++;
                    if(noteOctave > 4)noteOctave = 0;
                }
            }).size(40);
        }

        @Override
        public Object config() {
            return new IntSeq(new int[]{note, volume, noteOctave, noteSample});
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            if(nb[3] != null)Drawf.arrow(x, y, nb[3].x, nb[3].y, 2f, 2f, Pal.accent);
            if(nb[0] != null)Drawf.arrow(nb[0].x, nb[0].y, x, y, 2f, 2f, Pal.accent);
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
        public byte version() {
            return 1;
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);

            if(revision == 1){
                note = read.i();
                volume = read.i();
                noteOctave = read.i();
                noteSample = read.i();
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);

            write.i(note);
            write.i(volume);
            write.i(noteOctave);
            write.i(noteSample);
        }
    }
}
