package com.gator.companiongame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.gator.companiongame.GameStates.GameOverState;
import com.gator.companiongame.GameStates.GameState;
import com.gator.companiongame.GameStates.PlayState;
import com.gator.companiongame.GameStates.StartMenuState;
import com.gator.companiongame.WorldObjects.Arc;
import com.gator.companiongame.WorldObjects.Player;

/**
 * Directory: WaveDash/com.gator.companiongame/
 * Created by Wyatt on 1/21/2017.
 * Last edited by Wyatt on 1/21/2017.
 */

public class GameScreen implements Screen {



    private static final Color[] COLOR_SCHEMES = {
            new Color(1, 0, 0, 0), new Color(1, 0.5f, 0, 0), new Color(1, 1, 0, 0),
            new Color(0, 1, 0, 0), new Color(0, 1, 1, 0), new Color(0, 0, 1, 0),
            new Color(0.5f, 0, 1, 0), new Color(1, 0, 1, 0)
    };

    public final WaveDash game;

    private ShapeRenderer shapeRender;
    private Color background;
    public final int maxArcs, collisionArc;
    public int currentHue;
    public float playTime, spawnAngle, rotateAngle, gameSpeed, rotateDiff;
    public long startTime, difficultyTime;
    public Array<Arc> arcs;
    public Player player;
    public Skin skin;

    private GameState gameState;
    private int currentState;

    public GameScreen(final WaveDash game) {
        this.game = game;

        shapeRender = new ShapeRenderer();

        maxArcs = (int)(WaveDash.WIDTH / 1.3f / Arc.THICKNESS) + 2;
        collisionArc = (int)((Arc.y - Player.y) / Arc.THICKNESS);
        background = Color.BLACK;

        skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));

        reset();

        // Set initial state
        setState(GameState.START_MENU);
    }

    // Initialize game objects
    public void reset() {
        player = new Player();
        spawnAngle = 270f;
        rotateAngle = 0f;
        gameSpeed = 1f;
        rotateDiff = 11.25f;
        playTime = 0f;
        currentHue = 0;
        startTime = difficultyTime = TimeUtils.millis();
        arcs = new Array<Arc>(maxArcs);
        newArc(0.0f);
    }

    public void nextColorScheme() {
        currentHue++;
        if (currentHue >= COLOR_SCHEMES.length - 1) currentHue = 0;
    }

    public void newArc(float radius) {
        if (arcs.size >= maxArcs) {
            arcs.removeIndex(0);
        }
        if (MathUtils.random(4) == 0)
            rotateDiff *= -1;
        spawnAngle += rotateDiff;

        arcs.add(new Arc(spawnAngle, radius,
                Utils.randomLerpColor(COLOR_SCHEMES[currentHue], COLOR_SCHEMES[currentHue + 1])));
    }

    @Override
    public void render(float delta) {

        // Update game
        gameState.update(delta);

        // Set background color
        Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render shapeRenderer
        game.camera.update();
        shapeRender.setProjectionMatrix(game.camera.combined);

        // Render game objects
        for (Arc arc : arcs) {
            arc.render(shapeRender);
        }
        player.render(shapeRender);

        // Render batch
//        game.camera.update();
//        game.batch.setProjectionMatrix(shapeRender.getProjectionMatrix());
//        game.batch.begin();

        // NOTE: do I need renderUI and renderShapes methods in GameState?
        gameState.render();

//        game.batch.end();

    }

    public void setState(int newState) {

        currentState = newState;

        // TODO: do something fancy with transition in and out
        if (gameState != null) {
            gameState.dispose();
        }

        switch (newState) {
            case GameState.START_MENU:
                gameState = new StartMenuState(this);
                break;
            case GameState.PLAY:
                gameState = new PlayState(this);
                break;
            case GameState.PAUSE://TODO don't need state? just use boolean?
                gameState = new StartMenuState(this);
                break;
            case GameState.GAME_OVER:
                gameState = new GameOverState(this);
                break;
        }
    }

    @Override
    public void show() {
        // Play music
        gameState.show();
    }

    @Override
    public void resize(int width, int height) {
        gameState.resize(width, height);
    }

    @Override
    public void pause() {
        if (currentState == GameState.PLAY) {
            setState(GameState.PAUSE);
        }
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        gameState.dispose();
        shapeRender.dispose();
    }

}
