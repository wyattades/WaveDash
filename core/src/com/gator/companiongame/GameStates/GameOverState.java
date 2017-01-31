package com.gator.companiongame.GameStates;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gator.companiongame.GameScreen;
import com.gator.companiongame.GameScreen.State;
import com.gator.companiongame.WaveDash;
import com.gator.companiongame.WorldObjects.Arcs;

/**
 * Directory: CompanionGame/com.gator.companiongame.GameStates/
 * Created by Wyatt on 1/27/2017.
 * Last edited by Wyatt on 1/27/2017.
 */
public class GameOverState extends GameState {

    public GameOverState(final GameScreen screen) {
        super(screen);

        Table table = new Table(screen.skin);
        table.setPosition(WaveDash.WIDTH / 2, WaveDash.HEIGHT / 2, 0);

        Label title = new Label("GAME OVER", screen.skin);
        table.add(title);
        table.row();

        TextButton restartButton = new TextButton("Restart?", screen.skin);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.queueState(State.PLAY);
                //TEMP
                screen.arcs = new Arcs();
                screen.arcs.setDefault();
            }
        });
        table.add(restartButton);
        table.row();

        TextButton toMenuButton = new TextButton("Return to Menu", screen.skin);
        toMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.queueState(State.START_MENU);
            }
        });
        table.add(toMenuButton);
        table.row();

        stage.addActor(table);
    }

}
