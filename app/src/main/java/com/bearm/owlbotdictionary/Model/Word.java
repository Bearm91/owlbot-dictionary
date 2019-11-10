package com.bearm.owlbotdictionary.Model;

public class Word {

    public String word;
    private String[] definition;
    private String[] type;
    private String image;
    public String pronunciation;


    public Word(String word, String[] definition, String[] type, String image, String pronunciation) {
        this.word = word;
        this.definition = definition;
        this.image = image;
        this.type = type;
        this.pronunciation = pronunciation;
    }

    public Word(String word, String[] definition, String[] type, String pronunciation) {
        this.word = word;
        this.definition = definition;
        this.type = type;
        this.pronunciation = pronunciation;
    }

    public Word(String word, String pronunciation) {
        this.word = word;
        this.pronunciation = pronunciation;
    }
}