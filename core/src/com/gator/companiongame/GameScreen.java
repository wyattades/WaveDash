package com.gator.companiongame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gator.companiongame.GameStates.*;
import com.gator.companiongame.WorldObjects.Arcs;
import com.gator.companiongame.WorldObjects.Player;

/**
 * Directory: WaveDash/com.gator.companiongame/
 * Created by Wyatt on 1/21/2017.
 * Last edited by Wyatt on 1/21/2017.
 */

public class GameScreen implements Screen {

    private ShapeRenderer shapeRender;
    private Color background;
    private GameState gameState;
    private int transitionState;

    public float playTime, gameSpeed;
    public long startTime, difficultyTime;
    public Player player;
    public Arcs arcs;
    public Skin skin;
    public final WaveDash game;

    public enum State {
        START_MENU,
        PLAY,
        PAUSE,
        GAME_OVER
    }
    private State currentState, nextState;

    GameScreen(final WaveDash game) {
        this.game = game;

        shapeRender = new ShapeRenderer();

        background = Color.BLACK;

        skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));

        arcs = new Arcs();
        player = new Player();

        // Set initial state
        queueState(State.START_MENU);
    }

    @Override
    public void render(float delta) {

        // Update game

        if (transitionState == -1) {
            if (!gameState.transitionIn(delta)) {
                transitionState = 0;
            }
        } else if (transitionState == 0) {
            gameState.update(delta);
        } else if (transitionState == 1) {
            if (!gameState.transitionOut(delta)) {
                gameState.dispose();
                setState(nextState);
            }
        }

        // Render game

        // Set background color
        Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render shapeRenderer
        game.camera.update();
        shapeRender.setProjectionMatrix(game.camera.combined);

        // Render game objects
        arcs.render(shapeRender);
        player.render(shapeRender);

        // Render batch
//        game.camera.update();
//        game.batch.setProjectionMatrix(shapeRender.getProjectionMatrix());
//        game.batch.begin();

        // NOTE: do I need renderUI and renderShapes methods in GameState?
        gameState.render();

//        game.batch.end();

    }

    public void queueState(State newState) {
        if (gameState != null) {
            nextState = newState;
            transitionState = 1;
        } else {
            transitionState = -1;
            setState(newState);
        }
    }

    public void setState(State newState) {
        switch (newState) {
            case START_MENU:
                gameState = new StartMenuState(this);
                break;
            case PLAY:
                gameState = new PlayState(this);
                break;
            case PAUSE:
                gameState = new PauseState(this);
                break;
            case GAME_OVER:
                gameState = new GameOverState(this);
                break;
        }
        currentState = newState;
        transitionState = 0;
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
        if (currentState == State.PLAY) {
            setState(State.PAUSE);
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
