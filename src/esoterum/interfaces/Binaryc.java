package esoterum.interfaces;

import esoterum.type.*;
import mindustry.gen.*;

public interface Binaryc {
    default boolean signal(){
        return false;
    }

    default boolean canSignal(Building to, Building from){
        return from != null
                && from.block instanceof BinaryBlock
                && from.team == to.team
                && (!from.block.rotate || from.front() == to)
                && ((BinaryBlock.BinaryBuild)from).emits();
    }
}
