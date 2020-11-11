package ch.cpnv.angrywirds.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class RubberBand extends Sprite {
    private static final String PICNAME = "rubber.png";
    private static final float THICKNESS = 12f;

    public RubberBand(){
        super(new Texture(PICNAME));
    }

    public void between (Vector2 ori, Vector2 dest) {
        Vector2 diff = dest.sub(ori);
        setBounds(ori.x,ori.y,diff.len(), THICKNESS);
        setOrigin(0,0);
        setRotation(diff.angle());
    }

}
