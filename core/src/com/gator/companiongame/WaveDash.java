package com.gator.companiongame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class WaveDash extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public OrthographicCamera camera;
    public Viewport viewport;

    public static final int WIDTH = 800,
                            HEIGHT = (int) (WIDTH * 9f/16f);

    public static float
            FONT_SIZE_LARGE = 0.175f,
            FONT_SIZE_MEDIUM = 0.06f,
            FONT_SIZE_SMALL = 0.04f;

    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private FreeTypeFontGenerator generator;

    public BitmapFont createFont(float size, Color color) {
        parameter.size = (int)Math.ceil(size);
        generator.scaleForPixelHeight((int)Math.ceil(size));
        BitmapFont bitmapFont = generator.generateFont(parameter);
        bitmapFont.setColor(color);
        return bitmapFont;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();

        // Set up font parameters
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Exo-Bold.otf"));
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font = createFont(FONT_SIZE_MEDIUM * WIDTH, Color.WHITE);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WaveDash.WIDTH, WaveDash.HEIGHT);

        viewport = new FillViewport(WaveDash.WIDTH, WaveDash.HEIGHT, camera);
        viewport.apply();

        this.setScreen(new GameScreen(this));

    }

    @Override
    public void render () {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}