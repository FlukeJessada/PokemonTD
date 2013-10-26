package com.xkings.pokemontd.system.autonomous;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.xkings.core.component.PositionComponent;
import com.xkings.core.component.RangeComponent;
import com.xkings.core.component.SizeComponent;
import com.xkings.core.graphics.camera.CameraHandler;

/**
 * Created by Tomas on 10/4/13.
 */
public class RenderDebugSystem extends EntityProcessingSystem {
    private final CameraHandler camera;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer(5000);

    @Mapper
    ComponentMapper<PositionComponent> positionMapper;
    @Mapper
    ComponentMapper<SizeComponent> sizeMapper;

    public RenderDebugSystem(CameraHandler camera) {
        super(Aspect.getAspectForAll(PositionComponent.class, SizeComponent.class));
        this.camera = camera;
    }

    @Override
    protected void begin() {
        shapeRenderer.setProjectionMatrix(camera.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    }

    @Override
    protected void end() {
        shapeRenderer.end();
    }

    @Override
    protected void process(Entity e) {
        Vector3 position = positionMapper.get(e).getPoint();
        Vector3 size = sizeMapper.get(e).getPoint();
        shapeRenderer.rect(position.x - size.x / 2, position.y - size.y / 2, size.x, size.y);
    }

}
