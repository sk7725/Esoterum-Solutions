package esoterum.interfaces;

import arc.util.*;
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

    default boolean getSignalRelativeTo(BinaryBlock.BinaryBuild from, BinaryBlock.BinaryBuild to){
        if(!from.emits()) return false;
        if(!from.block.rotate && from.emitAllDirections()){
            return from.lastSignal;
        }
        int rot = (from.relativeTo(to) - to.rotation) % 4;
        Log.info(rot);
        return switch(rot){
            case 0 -> from.signalFront();
            case 1 -> from.signalLeft();
            case 2 -> from.signalBack();
            case 3 -> from.signalRight();
            default -> false;
        };
    }

    default boolean signalFront(){
        return false;
    }
    default boolean signalLeft(){
        return false;
    }
    default boolean signalBack(){
        return false;
    }
    default boolean signalRight(){
        return false;
    }

    default boolean getSignal(BinaryBlock.BinaryBuild to, BinaryBlock.BinaryBuild from){
        if(from == null)return false;
        return from.getSignalRelativeTo(from, to);
    }
}
