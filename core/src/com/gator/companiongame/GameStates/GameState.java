package com.gator.companiongame.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gator.companiongame.GameScreen;

/**
 * Directory: CompanionGame/com.gator.companiongame.GameStates/
 * Created by Wyatt on 1/25/2017.
 * Last edited by Wyatt on 1/25/2017.
 */
public abstract class GameState {

    public static final int
            START_MENU = 0,
            PLAY = 1,
            PAUSE = 2,
            GAME_OVER = 3;

    final GameScreen screen;
    final Stage stage;

    public GameState(final GameScreen screen) {
        this.screen = screen;
        stage = new Stage(screen.game.viewport, screen.game.batch);
        show();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void update(float delta) {
        stage.act(delta);
    }

    public void render() {
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

    public boolean transitionIn() {
        return false;
    }

    public boolean transitionOut() {
        return false;
    }

}
