package esoterum.type;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.gen.Building;
import mindustry.graphics.*;

public class BinaryNode extends BinaryAcceptor{
    public BinaryNode(String name){
        super(name);
        rotate = false;
        configurable = true;
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
                Lines.stroke(2);
                Lines.line(x, y, source.x, source.y);
            }
        }

        @Override
        public boolean onConfigureTileTapped(Building other) {
            if(linkValid(other)){
                BinaryNodeBuild bOther = (BinaryNodeBuild) other;
                if(bOther == source){
                    source = null;
                    bOther.dest = null;
                    return true;
                }
                if(bOther.source == this){
                    bOther.source = null;
                    dest = null;
                    return true;
                }
                if(bOther.source == null){
                    if(dest != bOther && dest != null)dest.source = null;
                    bOther.source = this;
                    dest = bOther;
                    return true;
                }
            }
            return self() != other;
        }

        @Override
        public void drawConfigure() {
            Draw.color(Color.white);
            Draw.z(Layer.overlayUI);
            Lines.stroke(1f);
            Lines.circle(x, y, 5f);
            if(dest != null)Lines.circle(dest.x, dest.y, 5f);
            if(source!= null)Lines.circle(source.x, source.y, 5f);
            Drawf.dashCircle(x, y, 40, Color.white);
            Draw.reset();
        }

        @Override
        public void onRemoved() {
            if(source != null)source.dest = null;
            source = null;
            if(dest != null)dest.source = null;
            dest = null;
        }

        public boolean linkValid(Building other){
            return other instanceof BinaryNodeBuild && this != other && Mathf.dst(x, y, other.x, other.y) <= 40f;
        }
    }
}
