package com.gator.companiongame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

/**
 * Directory: CompanionGame/com.gator.companiongame/
 * Created by Wyatt on 1/25/2017.
 * Last edited by Wyatt on 1/25/2017.
 */
public class Utils {

    public static Color lerpColor(Color c1, Color c2, float blending) {
        float inverse_blending = 1.0f - blending;

        float r = c1.r * blending + c2.r * inverse_blending;
        float g = c1.g * blending + c2.g * inverse_blending;
        float b = c1.b * blending + c2.b * inverse_blending;

        return new Color (r, g, b, 1.0f);
    }

    public static Color randomLerpColor(Color c1, Color c2) {
        return lerpColor(c1, c2, MathUtils.random(1.0f));
    }

}
