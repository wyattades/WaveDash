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
 * Created by Wyatt on 1/26/2017.
 * Last edited by Wyatt on 1/26/2017.
 */
public class StartMenuState extends GameState {

    public StartMenuState(final GameScreen screen) {
        super(screen);

        screen.arcs = new Arcs();
        screen.arcs.setMenu();

        Table table = new Table(screen.skin);
        table.setPosition(WaveDash.WIDTH / 2, WaveDash.HEIGHT / 2, 0);

        Label title = new Label("WAVE DASH", screen.skin);
        table.add(title);
        table.row();

        TextButton startButton = new TextButton("Start Game", screen.skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.queueState(State.PLAY);
            }
        });
        table.add(startButton);
        table.row();

        TextButton helpButton = new TextButton("Help", screen.skin);
        helpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                screen.setState();
            }
        });
        table.add(helpButton);

        stage.addActor(table);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        screen.arcs.update(delta, 0);
    }
}
