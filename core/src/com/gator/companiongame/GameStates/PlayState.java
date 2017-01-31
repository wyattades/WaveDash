package com.gator.companiongame.GameStates;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.gator.companiongame.GameScreen;
import com.gator.companiongame.GameScreen.State;
import com.gator.companiongame.WaveDash;
import com.gator.companiongame.WorldObjects.Player;

import java.util.Locale;

/**
 * Directory: CompanionGame/com.gator.companiongame.GameStates/
 * Created by Wyatt on 1/26/2017.
 * Last edited by Wyatt on 1/26/2017.
 */
public class PlayState extends GameState {

    private Label counter;
    private int tmpPointer;
    private boolean pressLeft, pressRight;

    public PlayState(final GameScreen screen) {
        super(screen);

        screen.player = new Player();
        screen.arcs.setDefault();
        screen.arcs.setCollider(screen.player);
        screen.gameSpeed = 1.0f;
        screen.playTime = 0.0f;
        screen.startTime = screen.difficultyTime = TimeUtils.millis();

        Table table = new Table(screen.skin);
        table.setPosition(WaveDash.WIDTH / 2, WaveDash.HEIGHT * 0.8f, 0);

        counter = new Label("0.00", screen.skin);
        table.add(counter);

        stage.addActor(table);

        tmpPointer = 0;

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(tmpPointer == 0) {
                    tmpPointer = pointer;
                    if (x < WaveDash.WIDTH * 0.5f) {
                        pressLeft = true;
                    } else {
                        pressRight = true;
                    }
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (tmpPointer == pointer) {
                    tmpPointer = 0;
                    pressLeft = pressRight = false;
                }
            }
        });
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        final float _gameSpeed = screen.gameSpeed * delta;
        final long currentTime = TimeUtils.millis();

        screen.player.animate(_gameSpeed);

        screen.playTime = (currentTime - screen.startTime) * 0.001f;
        counter.setText(String.format(Locale.US, "%.2f", screen.playTime));

        if (currentTime - screen.difficultyTime > 10000f) { // 10 seconds
//            levelIteration++;
            screen.gameSpeed += 0.4f;
            screen.arcs.nextColorScheme();
            screen.difficultyTime = TimeUtils.millis();
        }

        int direction = 0;
        if (pressLeft) {
            direction = 1;
        } else if (pressRight) {
            direction = -1;
        }
        screen.arcs.update(_gameSpeed, direction);

        if (screen.arcs.isColliding()) {
            screen.queueState(State.GAME_OVER);
        }
    }
}
