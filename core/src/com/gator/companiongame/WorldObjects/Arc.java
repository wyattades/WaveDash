package com.gator.companiongame.WorldObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gator.companiongame.WaveDash;

/**
 * Directory: CompanionGame/com.gator.companiongame/
 * Created by Wyatt on 1/24/2017.
 * Last edited by Wyatt on 1/24/2017.
 */
public class Arc {

    private static final float
            SPEED = 75f;
    private static final float x = WaveDash.WIDTH * 0.5f;
    public static final float y = WaveDash.HEIGHT * 0.75f;
    private static final float DELTA_ANGLE = 30f;

    private static final float PLAYER_RADIUS_ANGLE =
            (float) Math.toDegrees(2 * Math.asin(Player.radius / (2 * (y - Player.y))));

    public static final float
            THICKNESS = 30f;

    private float angle;
    public float radius;
    private float offset;
    public Color color;

    public Arc(float angle, float radius, Color color) {
        this.angle = angle;
        this.radius = radius;
        this.color = color;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public void grow(float gameSpeed) {
        radius += SPEED * gameSpeed;
    }

    public boolean collide() {

        final float playerAngle = 270f;
        return (offset + angle + DELTA_ANGLE < playerAngle + PLAYER_RADIUS_ANGLE ||
                offset + angle - DELTA_ANGLE > playerAngle - PLAYER_RADIUS_ANGLE);
    }

    public void render(ShapeRenderer sr) {

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(color);
        sr.arc(x, y, radius + 0.7f, offset + angle + DELTA_ANGLE, 360f - 2f * DELTA_ANGLE, 40);
        sr.end();

        if (radius - THICKNESS > 0) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.BLACK);
            sr.circle(x, y, radius - THICKNESS, 40);
            sr.end();
        }
    }
}
