package esoterum.interfaces;

import esoterum.type.*;
import mindustry.gen.*;

public interface Binaryc {
    default boolean signal(){
        return false;
    }

    default boolean canSignal(Building to, Building from){
        return from != null && (!from.block.rotate || from.front() == to);
    }

    default void getConnected(BinaryBlock.BinaryBuild to){
        to.connected.clear();
        for(Building other : to.proximity){
            if(other.block instanceof BinaryBlock && other.team == to.team){
                to.connected.add((BinaryBlock.BinaryBuild) other);
            }
        }
    }

}
