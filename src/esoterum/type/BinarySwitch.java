package esoterum.type;

import arc.util.*;
import mindustry.gen.*;

public class BinarySwitch extends BinaryBlock {
    public BinarySwitch(String name){
        super(name);
        configurable = true;
        autoResetEnabled = false;

    }

    public class BinarySwitchBuild extends BinaryBuild {
        @Override
        public boolean configTapped() {
            lastSignal = !lastSignal;
            Log.info(lastSignal);
            Sounds.click.at(this);
            return true;
        }
    }
}
