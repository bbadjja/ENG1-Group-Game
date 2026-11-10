package com.bbadjja.ENG1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Main extends Game {
    public final GlyphLayout GLYPH_LAYOUT = new GlyphLayout();

    public ExtendViewport viewport;
    public ExtendViewport fixedViewport;
    public OrthographicCamera camera;
    public SpriteBatch batch;
    public BitmapFont font;

    /**
     * Called when the game is started, sets up game elements such as screen
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        // Screen is 800x800
        // viewport = 8x8 so 100 pixels = 1 game unit
        viewport = new ExtendViewport(8, 8);
        // Forced to make fixedViewport use pixels so that font will render correctly
        fixedViewport = new ExtendViewport(800,800,camera);

        Texture fontTexture = new Texture(Gdx.files.internal("gameFont.png"));
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("gameFont.fnt"),new TextureRegion(fontTexture));
        font.getData().setScale(1f);

        this.setScreen(new GameScreen(this));
    }

    public void render(){
        super.render();
    }

    /**
     * Called when the game is closed, disposes all game elements
     */
    @Override
    public void dispose() {
        screen.dispose();
        batch.dispose();
    }
}
