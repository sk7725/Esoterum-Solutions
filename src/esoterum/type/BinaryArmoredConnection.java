package esoterum.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import esoterum.content.EsoVars;

public class BinaryArmoredConnection extends BinaryAcceptor{
    public BinaryArmoredConnection(String name){
        super(name);
        rotate = true;
        emits = true;
        emitAllDirections = false;
    }

    @Override
    public void load() {
        super.load();
        connectionRegion = Core.atlas.find("eso-not-connections");
    }

    public class BinaryArmoredConnectionBuild extends BinaryAcceptor.BinaryAcceptorBuild{
        public BinaryBlock.BinaryBuild back;

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.color(Color.white, EsoVars.connectionColor, lastSignal ? 1f : 0f);
            Draw.rect(connectionRegion, x, y, rotdeg());
            Draw.rect(topRegion, x, y, rotdeg());
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            back = validateNearby(back());
        }

        @Override
        public boolean signal() {
            return getSignal(back);
        }
    }
}
