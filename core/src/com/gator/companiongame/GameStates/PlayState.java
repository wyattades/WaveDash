package com.gator.companiongame.GameStates;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.gator.companiongame.GameScreen;
import com.gator.companiongame.WaveDash;
import com.gator.companiongame.WorldObjects.Arc;

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

        // TEMP!!
        screen.reset();

        Table table = new Table(screen.skin);
        table.setPosition(WaveDash.WIDTH / 2, WaveDash.HEIGHT, 0);

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

        float dist = screen.arcs.get(screen.arcs.size - 1).radius - Arc.THICKNESS;
        if (dist > 0.0f) {
            screen.newArc(dist);
        }

        if (currentTime - screen.difficultyTime > 10000f) { // 10 seconds
//            levelIteration++;
            screen.gameSpeed += 0.4f;
            screen.nextColorScheme();
            screen.difficultyTime = TimeUtils.millis();
        }

        final float rotateSpeed = _gameSpeed * 28f;
        if (pressLeft) {
            screen.rotateAngle += rotateSpeed;
        } else if (pressRight) {
            screen.rotateAngle -= rotateSpeed;
        }

        if (screen.arcs.size > screen.collisionArc &&
                screen.arcs.get(screen.arcs.size - 1 - screen.collisionArc).collide()) {
            screen.setState(GAME_OVER);
        }

        for (Arc arc : screen.arcs) {
            arc.setOffset(screen.rotateAngle);
            arc.grow(_gameSpeed);
        }
    }
}
