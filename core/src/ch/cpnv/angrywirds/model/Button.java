package ch.cpnv.angrywirds.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import ch.cpnv.angrywirds.activities.Play;
import ch.cpnv.angrywirds.model.Data.SemanticWord;

public class Button extends TextualObject {
    private BitmapFont font;
    private String value;
    private float width;
    private float height;
    private Vector2 position;



    public Button(Vector2 position, float width, float height, String picname, String text) {
        super(position, width, height, picname, text);
        setBounds(200,200,200,200);
        this.position = position;
        this.width = width;
        this.height = height;
        this.value = text;
        font= new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2);
    }
    public String getValue(){
        return this.value;
    }
    public boolean isTouched(Vector2 point){
        if((point.x >= position.x-50 && point.x <= position.x+50)&&(point.y >= position.y-50 && point.y <= position.y+50))
            return true;
        else
            return false;
    }


    public void draw(Batch batch)
    {
        super.draw(batch);
        font.draw(batch, getValue(),width-50,height+25);
    }
}


