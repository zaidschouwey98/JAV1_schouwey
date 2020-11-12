package ch.cpnv.angrywirds.activities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.model.Bird;
import ch.cpnv.angrywirds.model.Data.Vocabulary;
import ch.cpnv.angrywirds.model.Data.Word;
import ch.cpnv.angrywirds.model.ObjectOutOfBoundsException;
import ch.cpnv.angrywirds.model.Panel;
import ch.cpnv.angrywirds.model.PhysicalObject;
import ch.cpnv.angrywirds.model.Pig;
import ch.cpnv.angrywirds.model.RubberBand;
import ch.cpnv.angrywirds.model.SceneCollapseException;
import ch.cpnv.angrywirds.model.Scenery;
import ch.cpnv.angrywirds.model.TNT;
import ch.cpnv.angrywirds.model.Wasp;
import ch.cpnv.angrywirds.providers.VocProvider;

import static ch.cpnv.angrywirds.model.Scenery.BLOCK_SIZE;

public class Play extends Game implements InputProcessor {

    public static Random alea; // random generator object. Static for app-wide use

    public static final float WORLD_WIDTH = 1600;
    public static final float WORLD_HEIGHT = 900;
    public static final int FLOOR_HEIGHT = 120;
    public static final int SLINGSHOT_POWER = 8;
    private final int AIMING_ZONE_X = 90+Bird.WIDTH;
    private final int AIMING_ZONE_Y = FLOOR_HEIGHT+170+Bird.HEIGHT;
    private static final int SLINGSHOT_WIDTH = 75;
    private static final int SLINGSHOT_HEIGHT = 225;
    private static final int SLINGSHOT_OFFSET = 100; // from left edge

    private Scenery scene;
    private Bird tweety;
    private Wasp waspy;
    private VocProvider vocSource = VocProvider.getInstance();
    private Vocabulary voc;
    private Panel panel;
    private int scoreVal;

    private SpriteBatch batch;
    private Texture background;
    private Rectangle aimingZone;
    private Texture slingshot1;
    private Texture slingshot2;
    private RubberBand rubberBand1;
    private RubberBand rubberBand2;
    private BitmapFont scoreDisp;

    private OrthographicCamera camera;

    public Play()
    {
        alea = new Random();

        tweety = new Bird(new Vector2(AIMING_ZONE_X-Bird.WIDTH, AIMING_ZONE_Y-Bird.HEIGHT), new Vector2(0, 0));
        waspy = new Wasp(new Vector2(WORLD_WIDTH / 2, WORLD_HEIGHT / 2), new Vector2(0, 0));
        voc = vocSource.pickAVoc();
        //voc.pickAWord();

        scene = new Scenery();
        //scene.addFloor();
        for (int i = 0; i < 150; i++) {
            try {
                scene.dropElement(new PhysicalObject(new Vector2(alea.nextFloat() * WORLD_WIDTH, 0), BLOCK_SIZE, BLOCK_SIZE, "block.png"));
            } catch (ObjectOutOfBoundsException e)
            {
                Gdx.app.log("ANGRY", "Object out of bounds: " + e.getMessage());
            }
            catch (SceneCollapseException e)
            {
                Gdx.app.log("ANGRY", "Unstable object: " + e.getMessage());
            }
        }
        int TNTLeft = 5;
        while (TNTLeft > 0) {
            try {
                scene.dropElement(new TNT(new Vector2(alea.nextFloat() * WORLD_WIDTH, FLOOR_HEIGHT + BLOCK_SIZE), 0));
                TNTLeft--;
            } catch (ObjectOutOfBoundsException e) {
                Gdx.app.log("ANGRY", "TNT out of bounds: " + e.getMessage());
            } catch (SceneCollapseException e) {
                Gdx.app.log("ANGRY", "Unstable TNT: " + e.getMessage());
            }
        }
        int pigsLeft = 5;

        //while (pigsLeft > 0) {
            //try {
             //   scene.dropElement(new Pig(new Vector2(alea.nextFloat() * WORLD_WIDTH, FLOOR_HEIGHT + BLOCK_SIZE),voc.pickAWord()));
//            } catch (ObjectOutOfBoundsException e) {
             //   Gdx.app.log("ANGRY", "Pig out of bounds: " + e.getMessage());
           // } catch (SceneCollapseException e) {
            //    Gdx.app.log("ANGRY", "Unstable pig: " + e.getMessage());
           // }
      //  }

        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("background.jpg"));
        aimingZone = new Rectangle(0,0,AIMING_ZONE_X,AIMING_ZONE_Y);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        //panel = new Panel(scene.pickAWord());
        scoreVal = 3; // allow a few mistakes before game over
        scoreDisp= new BitmapFont();
        scoreDisp.setColor(Color.BLACK);
        scoreDisp.getData().setScale(2);

        slingshot1 = new Texture(Gdx.files.internal("slingshot1.png"));
        slingshot2 = new Texture(Gdx.files.internal("slingshot2.png"));
        rubberBand1 = new RubberBand();
        rubberBand2 = new RubberBand();

        Gdx.input.setInputProcessor(this);

    }
    @Override
    public void create() {

    }

    public void update() {
        float dt = Gdx.graphics.getDeltaTime();
        if (dt < 1.0f) // ignore major lapses like the one at the beginning of the app
        {

            // --------- Bird
            if (tweety.getState() == Bird.State.FLYING) {
                tweety.accelerate(dt);
                tweety.move(dt);
                if (tweety.getX() > WORLD_WIDTH) tweety.reset();
                PhysicalObject phob = scene.objectHitBy(tweety);
                if (phob != null) {
                    if (phob instanceof Pig) {
                        if (((Pig)phob).getWord() == panel.getWord()) {
                            scoreVal++;
                            //Word tes = ((Pig) phob).getWord();
                            //tes.setFound();
                            scene.removeElement(phob);
                        } else {
                            scoreVal--;
                            if (scoreVal == 0) { // Game over
                                AngryWirds.pages.push(new GameOver());
                            }
                        }
                    } else {
                        scene.removeElement(phob);
                    }
                    tweety.reset();
                }
            }

            // --------- Wasp
            waspy.accelerate(dt);
            waspy.move(dt);

            // --------- Rubberbands
            rubberBand1.between(new Vector2(tweety.getX() + 20, tweety.getY() + 10), new Vector2(SLINGSHOT_OFFSET + SLINGSHOT_WIDTH, SLINGSHOT_HEIGHT + FLOOR_HEIGHT - 40));
            rubberBand2.between(new Vector2(tweety.getX() + 20, tweety.getY() + 10), new Vector2(SLINGSHOT_OFFSET + 15, SLINGSHOT_HEIGHT + FLOOR_HEIGHT - 40));

        }

    }

    @Override
    public void render() {
        update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        batch.draw(slingshot1, SLINGSHOT_OFFSET, FLOOR_HEIGHT, SLINGSHOT_WIDTH, SLINGSHOT_HEIGHT);
        if (tweety.getState() == Bird.State.AIMING) rubberBand1.draw(batch);
        tweety.draw(batch);
        if (tweety.getState() == Bird.State.AIMING) rubberBand2.draw(batch);
        waspy.draw(batch);
        scene.draw(batch);
        //panel.draw(batch);
        batch.draw(slingshot2, SLINGSHOT_OFFSET, FLOOR_HEIGHT, SLINGSHOT_WIDTH, SLINGSHOT_HEIGHT);
        displayScore(batch);
        batch.end();
    }

    private void displayScore(SpriteBatch batch)
    {
        scoreDisp.draw(batch, "Score: "+scoreVal, WORLD_WIDTH-150, WORLD_HEIGHT-60);
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
        Vector3 pt3 = camera.unproject(new Vector3(screenX, screenY, 0)); // Convert from screen coordinates to camera coordinate
        Vector2 pointTouched = new Vector2(pt3.x, pt3.y);
        if (aimingZone.contains(pointTouched)) {
            tweety.startAim(pointTouched);
        } else {
            scene.handleTouchDown(pointTouched); // handle effect on pigs
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 pt3 = camera.unproject(new Vector3(screenX, screenY, 0)); // Convert from screen coordinates to camera coordinate
        Vector2 pointTouched = new Vector2(pt3.x, pt3.y);
        if (aimingZone.contains(pointTouched)) {
            tweety.launchFrom(pointTouched);
        } else {
            scene.handleTouchUp(pointTouched); // handle effect on pigs
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 pt3 = camera.unproject(new Vector3(screenX, screenY, 0)); // Convert from screen coordinates to camera coordinate
        Vector2 pointTouched = new Vector2(pt3.x, pt3.y);
        if (aimingZone.contains(pointTouched)) {
            tweety.drag(pointTouched);
        }
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
