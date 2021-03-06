package com.pixelthieves.elementtd.graphics.tutorial.task;

import com.pixelthieves.elementtd.App;
import com.pixelthieves.elementtd.graphics.tutorial.Notice;
import com.pixelthieves.elementtd.graphics.tutorial.Tutorial;
import com.pixelthieves.elementtd.graphics.ui.Ui;
import com.pixelthieves.elementtd.manager.TowerManager;


/**
 * Created by Tomas on 12/2/13.
 */
public class ConfirmThatUpgrade extends NoticeTask {

    public ConfirmThatUpgrade(Tutorial tutorial) {
        super(tutorial);
    }

    @Override
    protected Notice buildNotice() {
        Ui ui = tutorial.getUi();
        return new Notice(ui, ui.getBuyButton(), Notice.Orientation.BOTTOM_RIGHT, "Confirm buying the upgrade",
                Notice.Placement.STATIC);
    }

    @Override
    public boolean checkConditions(App entity) {
        TowerManager towerManager = entity.getTowerManager();
        return towerManager.getSelectedTowerType() != null && towerManager.getClicked() != null;
    }

}
