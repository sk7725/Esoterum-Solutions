package esoterum.content;

import arc.*;
import arc.graphics.*;
import arc.scene.*;
import arc.scene.ui.layout.*;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.*;
import mindustry.ui.dialogs.SettingsMenuDialog.*;

import static mindustry.Vars.*;

public class EsoSettings {
    private static final ColorPicker colorPicker = new ColorPicker();
    public SettingsTable eso;
    public Color oldColor = Pal.accent.cpy();

    public void init(){
        BaseDialog dialog = new BaseDialog("Esoterum Solutions");
        dialog.addCloseButton();
        eso = new SettingsTable();
        eso.table(a -> {
            a.row().button("Binary signal color", () -> {
                colorPicker.show(EsoVars.connectionColor, false, (Color col) -> {
                    Core.settings.put("esoterumsignalcolor", col.toString());
                    EsoVars.connectionColor.set(col);
                });
            }).growX();

            a.row().button("Reset signal color", () -> {
                Core.settings.put("esoterumsignalcolor", Pal.accent.toString());
                EsoVars.connectionColor.set(Pal.accent);
            }).growX();
        }).margin(14).width(240f).pad(6);

        eso.row().label(() -> "[gray]Esoterum v" + mods.getMod("eso").meta.version);
        dialog.cont.center().add(eso);

        Events.on(EventType.ResizeEvent.class, event -> {
            if(dialog.isShown() && Core.scene.getDialog() == dialog){
                dialog.updateScrollFocus();
            }
        });

        // thanks Meep
        ui.settings.shown(() -> {
            Table settingUi = (Table)((Group)((Group)(ui.settings.getChildren().get(1))).getChildren().get(0)).getChildren().get(0); //This looks so stupid lol
            settingUi.row();
            settingUi.button("Esoterum Solutions", Styles.cleart, dialog::show);
        });
    }
}
