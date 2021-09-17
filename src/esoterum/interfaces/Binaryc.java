package esoterum.interfaces;

import esoterum.world.blocks.binary.*;
import mindustry.gen.*;

public interface Binaryc {
    default boolean signal(){
        return false;
    }

    // gets the signal output of 'from' to 'to' depending on relative rotation.
    default boolean getSignalRelativeTo(BinaryBlock.BinaryBuild from, BinaryBlock.BinaryBuild to){
        if(!from.emits()) return false;
        if(!from.block.rotate && from.emitAllDirections()){
            return from.lastSignal;
        }
        // relativeTo() does not account for block rotation, so subtract it with the rotation.
        int rot = (from.relativeTo(to) - from.rotation) % 4;
        return switch(rot){
            case 0 -> from.signalFront();
            case 1 -> from.signalLeft();
            case 2 -> from.signalBack();
            case 3 -> from.signalRight();
            default -> false;
        };
    }

    // directional signal output from the block.
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

    // retrieves the signal output from a binary block
    default boolean getSignal(BinaryBlock.BinaryBuild to, BinaryBlock.BinaryBuild from){
        if(from == null)return false;
        return from.getSignalRelativeTo(from, to);
    }

    // checks if the building is a binary building
    default BinaryBlock.BinaryBuild validateNearby(Building b){
        if(b instanceof Binaryc) return (BinaryBlock.BinaryBuild) b;
        return null;
    }
}
