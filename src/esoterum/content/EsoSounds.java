package esoterum.content;

import arc.*;
import arc.assets.*;
import arc.assets.loaders.SoundLoader.*;
import arc.audio.*;
import mindustry.*;

// thanks sh1p
public class EsoSounds {
    protected static Sound loadSound(String soundName) {
        String name = "sounds/" + soundName;
        String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";

        Sound sound = new Sound();

        AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundParameter(sound));
        desc.errored = Throwable::printStackTrace;

        return sound;
    }

    public static Sound beep = new Sound();
    public static Sound[] bells = new Sound[]{null, null, null, null, null};

    public static void load() {
        if(Vars.headless) return;

        beep = loadSound("beep");
        bells = new Sound[]{
                loadSound("notes/bells/bellc2"),
                loadSound("notes/bells/bellc3"),
                loadSound("notes/bells/bellc4"),
                loadSound("notes/bells/bellc5"),
                loadSound("notes/bells/bellc6")
        };
    }
}
