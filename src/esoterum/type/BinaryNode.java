package esoterum.type;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.graphics.*;

public class BinaryNode extends BinaryAcceptor{
    public BinaryNode(String name){
        super(name);
        rotate = false;
        configurable = true;
        drawConnection = false;
    }

    public class BinaryNodeBuild extends BinaryAcceptorBuild {
        public BinaryNodeBuild source = null;
        public BinaryNodeBuild dest = null;
        @Override
        public void updateTile(){
            lastSignal = nextSignal;
            if(source == null) {
                nextSignal = signal();
            }else{
                nextSignal = source.signal();
            }
        }

        @Override
        public boolean signal(){
            return sBack() | sLeft() | sRight() | sFront();
        }

        @Override
        public void draw() {
            super.draw();
            if(source != null) {
                Draw.z(Layer.power);
                Draw.color(Color.white, Color.green, lastSignal ? 1f : 0f);
                Lines.stroke(1f);
                Lines.line(x, y, source.x, source.y);
                Fill.circle(x, y, 1.5f);
                Fill.circle(source.x, source.y, 1.5f);

                float time = (Time.time / 60f) % 3f;
                Fill.circle(
                        Mathf.lerp(source.x, x, time / 3f),
                        Mathf.lerp(source.y, y, time / 3f),
                        1.5f
                );
            }
        }

        // TODO: redo connection checks
        @Override
        public boolean onConfigureTileTapped(Building other) {
            if(other != null && linkValid(other)){
                BinaryNodeBuild bOther = (BinaryNodeBuild) other;
                if(source == bOther){
                    bOther.dest = null;
                    source = null;
                    return true;
                }
                if(dest == bOther){
                    bOther.source = null;
                    dest = null;
                    return true;
                }
                if(bOther.source == null && bOther.dest == null && source == null && dest == null){
                    bOther.source = this;
                    dest = bOther;
                    return true;
                }
            }
            return other == null;
        }

        @Override
        public void drawConfigure() {
            Draw.color(Color.white);
            Draw.z(Layer.overlayUI);
            Lines.stroke(1f);
            Lines.circle(x, y, 5f);
            if(dest != null)Lines.circle(dest.x, dest.y, 6f);
            if(source!= null)Lines.circle(source.x, source.y, 6f);
            Drawf.dashCircle(x, y, 48, Color.white);
            Draw.reset();
        }

        @Override
        public void onRemoved() {
            // reset source or destination if it exists
            if(source != null){
                source.dest = null;
                source = null;
            }
            if(dest != null){
                dest.source = null;
                dest = null;
            }
        }

        public boolean linkValid(Building other){
            return other instanceof BinaryNodeBuild
                    && this != other
                    && Mathf.dst(x, y, other.x, other.y) <= 48f;
        }
    }
}
