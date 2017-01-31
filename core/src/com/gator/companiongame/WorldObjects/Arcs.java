package com.gator.companiongame.WorldObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.gator.companiongame.Utils;
import com.gator.companiongame.WaveDash;

/**
 * Directory: CompanionGame/com.gator.companiongame.WorldObjects/
 * Created by Wyatt on 1/28/2017.
 */
public class Arcs {

    private class Arc {
        float radius, angle;
        Color color;

        Arc(float angle, float radius, Color color) {
            this.radius = radius;
            this.angle = angle;
            this.color = color;
        }
    }

    // Constants
    private static final Color[] COLOR_SCHEMES = {
            new Color(1, 0, 0, 0), new Color(1, 0.5f, 0, 0), new Color(1, 1, 0, 0),
            new Color(0, 1, 0, 0), new Color(0, 1, 1, 0), new Color(0, 0, 1, 0),
            new Color(0.5f, 0, 1, 0), new Color(1, 0, 1, 0)
    };
    private static final float
            DELTA_ANGLE = 30.0f,
            THICKNESS = 30.0f,
            ANGULAR_SPEED = 42.0f;

    // Variables
    private Array<Arc> arcs;
    private float
            x, y,
            growthSpeed,
            playerRadiusAngle,
            angle,
            spawnAngle,
            rotateDiff;
    private int
            currentHue,
            collisionArc,
            maxArcs;

    public Arcs() {
        maxArcs = (int)(WaveDash.WIDTH / 1.3f / THICKNESS) + 2;
        arcs = new Array<Arc>(maxArcs);
        for (int i = maxArcs; i >= 0; i--) {
            arcs.add(new Arc(270.0f, i * THICKNESS,
                    Utils.randomLerpColor(COLOR_SCHEMES[currentHue], COLOR_SCHEMES[currentHue + 1])));
        }
    }

    public void setMenu() {
        x = WaveDash.WIDTH * 0.5f;
        y = WaveDash.HEIGHT;
        spawnAngle = 270.0f;
        angle = 180.0f;
        rotateDiff = 0.0f;
        currentHue = 0;
        growthSpeed = 14.0f;
    }

    public void setDefault() {
        x = WaveDash.WIDTH * 0.5f;
        y = WaveDash.HEIGHT * 0.75f;
        spawnAngle = 270f;
        angle = 0f;
        rotateDiff = 11.25f;
        currentHue = 0;
        growthSpeed = 100.0f;
    }

    public void setCollider(Player p) {
        collisionArc = (int)((y - p.y) / THICKNESS);
        playerRadiusAngle = (float) Math.toDegrees(2 * Math.asin(p.radius / (2 * (y - p.y))));
    }

    public void render(ShapeRenderer sr) {
        for (Arc arc : arcs) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(arc.color);
            sr.arc(x, y, arc.radius + 0.7f, angle + arc.angle + DELTA_ANGLE, 360f - 2f * DELTA_ANGLE, 40);
            sr.end();

            if (arc.radius - THICKNESS > 0) {
                sr.begin(ShapeRenderer.ShapeType.Filled);
                sr.setColor(Color.BLACK);
                sr.circle(x, y, arc.radius - THICKNESS, 40);
                sr.end();
            }
        }
    }

    public void update(float gameSpeed, int rotateDir) {

        for (Arc arc : arcs) {
            arc.radius += gameSpeed * growthSpeed;
        }

        float dist = arcs.get(arcs.size - 1).radius - THICKNESS;
        if (dist > 0.0f) {
            newArc(dist);
        }

        angle += gameSpeed * rotateDir * ANGULAR_SPEED;
    }

    public boolean isColliding() {

        if (arcs.size > collisionArc) {
            Arc collider = arcs.get(arcs.size - 1 - collisionArc);

            final float playerAngle = 270f;
            if (collider.angle + angle + DELTA_ANGLE < playerAngle + playerRadiusAngle ||
                    collider.angle + angle - DELTA_ANGLE > playerAngle - playerRadiusAngle) {
                return true;
            }
        }
        return false;
    }

    public void nextColorScheme() {
        currentHue++;
        if (currentHue >= COLOR_SCHEMES.length - 1) currentHue = 0;
    }

    private void newArc(float radius) {
        if (arcs.size >= maxArcs) {
            arcs.removeIndex(0);
        }
        if (MathUtils.random(4) == 0)
            rotateDiff *= -1;
        spawnAngle += rotateDiff;

        arcs.add(new Arc(spawnAngle, radius,
                Utils.randomLerpColor(COLOR_SCHEMES[currentHue], COLOR_SCHEMES[currentHue + 1])));
    }
}
