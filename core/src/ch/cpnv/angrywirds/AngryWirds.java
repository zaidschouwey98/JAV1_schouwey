package ch.cpnv.angrywirds;

import com.badlogic.gdx.Game;

import java.util.Random;
import java.util.Stack;

import ch.cpnv.angrywirds.activities.Play;
import ch.cpnv.angrywirds.activities.Welcome;
import ch.cpnv.angrywirds.model.Data.SemanticWord;
import ch.cpnv.angrywirds.model.Data.SemanticWordTest;

public class AngryWirds extends Game {
    private SemanticWordTest test;

    public static Random alea; // random generator object. Static for app-wide use

    public static Stack<Game> pages;

    @Override
    public void create() {
        test = new SemanticWordTest();
        SemanticWordTest.addTranslationTest();
        SemanticWordTest.getValueTest();
        alea = new Random();
        pages = new Stack<Game>();
        pages.push(new Welcome());
    }

    @Override
    public void render() {
        pages.peek().render();
    }

}
