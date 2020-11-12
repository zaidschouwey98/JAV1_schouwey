package ch.cpnv.angrywirds.model.Data;

public class Language {
    private String displayName;
    private String ISO_639_1;

    public Language(String ISO, String name){
        this.displayName = name;
        this.ISO_639_1 = ISO;
    }

    // Get the name of the language
    public String getDisplayName(){
        return this.displayName;
    }

    // Get the iso
    public String getISO_639_1(){
        return this.ISO_639_1;
    }
}
