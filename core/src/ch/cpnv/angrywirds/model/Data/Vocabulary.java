package ch.cpnv.angrywirds.model.Data;

import java.util.ArrayList;

import ch.cpnv.angrywirds.AngryWirds;


public class Vocabulary {
    private String vocName;
    private ArrayList<SemanticWord> words;

    public Vocabulary(String vocName){
        this.vocName = vocName;
        this.words = new ArrayList<SemanticWord>();
    }
    public void addSemanticWord(SemanticWord semword){
        words.add(semword);
    }


    public SemanticWord pickAWord() {
      return words.get(AngryWirds.alea.nextInt(words.size()));
    }
}







