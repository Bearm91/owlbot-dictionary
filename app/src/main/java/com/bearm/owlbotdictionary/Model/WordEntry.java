package com.bearm.owlbotdictionary.Model;


public class WordEntry {

    public String definition;
    public String type;
    public String image;
    public String example;

    public WordEntry(String definition, String type, String image, String example) {
        this.definition = definition;
        this.type = type;
        this.image = image;
        this.example = example;
    }
}
