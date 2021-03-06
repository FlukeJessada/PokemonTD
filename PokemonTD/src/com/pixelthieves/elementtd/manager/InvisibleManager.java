package com.pixelthieves.elementtd.manager;

import com.pixelthieves.core.logic.Updateable;
import com.pixelthieves.elementtd.App;
import com.pixelthieves.elementtd.system.autonomous.InvisibleSystem;


/**
 * User: Seda
 * Date: 17.10.13
 * Time: 15:52
 */

public class InvisibleManager implements Updateable {
    private final App app;
    private boolean visibility;

    public InvisibleManager(App app) {
        this.app = app;
    }

    @Override
    public void update(float delta) {
        app.getWorld().getSystem(InvisibleSystem.class).start(visibility);
        switchVisibility();
    }

    private void switchVisibility() {
        this.visibility = !visibility;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void setActive(boolean active) {
    }
}
