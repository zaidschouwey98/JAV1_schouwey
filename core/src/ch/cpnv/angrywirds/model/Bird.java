package ch.cpnv.angrywirds.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.activities.Play;

/**
 * Created by Xavier on 06.05.18.
 */

public final class Bird extends MovingObject {

    public enum State {
        READY,
        AIMING,
        FLYING
    }

    private static final String PICNAME = "bird.png";
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;
    private static final int MIN_LAUNCH_SPEED = 100;

    private State state = State.READY;
    private Vector2 dragOffset; // Touch location "within" the bird. Help keep dragging animation clean
    private Vector2 aimOrigin;  // Touch point of start of aiming
    private Vector2 origin;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public Bird() {
        super(new Vector2(0, 0), WIDTH, HEIGHT, PICNAME, new Vector2(0, 0));
    }

    public Bird(Vector2 position, Vector2 speed) {
        super(position, WIDTH, HEIGHT, PICNAME, speed);
        origin = position.cpy();
    }

    /**
     * Puts the bird back in its original spot and state
     */
    public void reset() {
        setPosition(origin.x,origin.y);
        state=State.READY;
        speed = new Vector2(0,0);
    }

    public void startAim (Vector2 position) {
        if (state == State.READY) {
            aimOrigin = position.cpy(); // Remember where the aiming started !!!!!! To make a copy is VERY important because the position variable will be destroyed at the end of the method
            dragOffset = position.sub(getX(), getY()); // !!!!! Must be after aimOrigin because sub alters the object
            state = State.AIMING;
        }
    }

    public void drag (Vector2 position) {
        if (state == State.AIMING) {
            setPosition(position.x - dragOffset.x, position.y - dragOffset.y);
        }
    }

    public void launchFrom (Vector2 position) {
        if (state == State.AIMING) {
            speed = aimOrigin.sub(position).scl(Play.SLINGSHOT_POWER);
            Gdx.app.log("ANGRY","Speed="+speed.len());
            if (speed.len() > MIN_LAUNCH_SPEED) {
                state = State.FLYING;
            } else {
                reset();
            }
        }
    }

    @Override
    public void accelerate(float dt) {
        speed.y += GRAVITY*dt; // That bird is a poor glider
    }

}
