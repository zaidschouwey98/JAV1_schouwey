package ch.cpnv.angrywirds.model.Data;

import java.util.HashMap;

public class SemanticWord {
    private HashMap<String,String> values;

    // Add the value to the HashMap
    public void addTranslation(String language, String value){
        this.values.put(language,value);

    }

    // Get a value by the parameter language
    public String getValue(String language){
        return this.values.get(language);
    }
}
