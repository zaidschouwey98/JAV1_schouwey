package ch.cpnv.angrywirds.activities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.List;

import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.model.Button;

public class Welcome extends Game implements InputProcessor {

    public static final float WORLD_WIDTH = 1600;
    public static final float WORLD_HEIGHT = 900;

    private SpriteBatch batch;
    private Texture background;
    private BitmapFont title;
    private Button frbutton,enbutton,spbutton;
    private List<Button> buttonList;
    private OrthographicCamera camera;

    public Welcome() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("background.jpg"));


        frbutton = new Button(new Vector2(500,500),300,300,"bubble.png","Français");
        enbutton = new Button(new Vector2(500,100),300,300,"bubble.png","Anglais");
        spbutton = new Button(new Vector2(500,800),300,300,"bubble.png","Anglais");
        //buttonList.add(frbutton);
        //buttonList.add(enbutton);
        //buttonList.add(spbutton);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        title= new BitmapFont();
        title.setColor(Color.ROYAL);
        title.getData().setScale(6);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void create() {

    }

    public void update() {
        float dt = Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render() {
        update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        title.draw(batch,"Language de ...",100,WORLD_HEIGHT-100);
        title.draw(batch,"à ...",WORLD_WIDTH/2,WORLD_HEIGHT-100);
        frbutton.draw(batch);
        enbutton.draw(batch);
        spbutton.draw(batch);
        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        /*for (Button btn:buttonList)
        {
            if(btn.isTouched(new Vector2(screenX,screenY))){

                AngryWirds.pages.push(new Play(btn.getValue()));
                // Then does smth
            }
        }*/

        AngryWirds.pages.push(new Play());
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
