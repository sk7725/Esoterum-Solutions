package esoterum.world.blocks.environment;

import mindustry.world.blocks.environment.*;

public class nonBlendFloor extends Floor {
    public nonBlendFloor(String name, int variants){
        super(name, variants);
    }

    @Override
    protected boolean doEdge(Floor other) {
        return !(other instanceof nonBlendFloor) && super.doEdge(other);
    }
}
