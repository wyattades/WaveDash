package com.gator.companiongame.WorldObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gator.companiongame.WaveDash;

/**
 * Directory: CompanionGame/com.gator.companiongame/
 * Created by Wyatt on 1/24/2017.
 * Last edited by Wyatt on 1/24/2017.
 */
public class Player {

    private static final float
            SPEED = 18f;

    public static final float
            x = WaveDash.WIDTH * 0.5f,
            y = WaveDash.HEIGHT * 0.34f,
            radius = Arc.THICKNESS * 0.4f;

    public float angle;
    public Color color;

    public Player() {
        angle = 45f;
        color = Color.WHITE;
    }

    public void animate(float gameSpeed) {
        angle += SPEED * gameSpeed;
    }

    public void render(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(color);
        sr.rect(x - radius, y - radius, radius, radius,
                radius * 2f, radius * 2f, 1f, 1f, angle);
        sr.end();
    }

}
