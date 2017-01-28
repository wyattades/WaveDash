package com.gator.companiongame.GameStates;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gator.companiongame.GameScreen;
import com.gator.companiongame.WaveDash;

/**
 * Directory: CompanionGame/com.gator.companiongame.GameStates/
 * Created by Wyatt on 1/27/2017.
 * Last edited by Wyatt on 1/27/2017.
 */
public class PauseState extends GameState {

    public PauseState(final GameScreen screen) {
        super(screen);

        Table table = new Table(screen.skin);
        table.setPosition(WaveDash.WIDTH / 2, WaveDash.HEIGHT / 2, 0);

        TextButton resumeButton = new TextButton("Resume", screen.skin);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.setState(PLAY);
            }
        });
        table.add(resumeButton);
    }

}
