package ca.cmpt213.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Tokimon class creates tokimon objects
 * that store tokimon information like
 * id, height, etc...
 * has getters and setters for said variables
 */
public class Tokimon {

    private int id;
    private String name;
    private int weight;
    private int height;
    private String ability;
    private int strength;
    private String color;

    public Tokimon(@JsonProperty("id") int id,
                   @JsonProperty("name") String name,
                   @JsonProperty("weight") int weight,
                   @JsonProperty("height") int height,
                   @JsonProperty("ability") String ability,
                   @JsonProperty("strength") int strength,
                   @JsonProperty("color") String color) {
        this.id = id;
        this.name = name;
        this.weight = Math.max(weight,1);
        this.height = Math.max(height,1);
        this.ability = ability;
        if(strength > 100){
            this.strength = 100;
        }
        else{
            this.strength = Math.max(strength, 0);
        }
        this.color = color;
    }

    //getters
    public int getId(){ return id;}

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public String getAbility() {
        return ability;
    }

    public int getStrength() {
        return strength;
    }

    public String getColor() {
        return color;
    }

    //setters

    public void setId(int id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
