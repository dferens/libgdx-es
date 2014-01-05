package com.dferens.rocketgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.dferens.core.GameManager;

public class GdxGame extends Game {
    class MainScreen implements Screen {

        private final GameManager gameManager;

        public MainScreen(GameManager gameManager) {
            this.gameManager = gameManager;
        }

        @Override
        public void render(float delta) {
            this.gameManager.process(delta);
        }

        @Override
        public void resize(int width, int height) { }

        @Override
        public void show() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void hide() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void pause() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void resume() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void dispose() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
    @Override
    public void create() {
        GameManager gameManager = new RocketGameManager();
        this.setScreen(new MainScreen(gameManager));
    }
}
