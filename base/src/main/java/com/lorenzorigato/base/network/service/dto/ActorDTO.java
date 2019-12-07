package com.lorenzorigato.base.network.service.dto;

public class ActorDTO {

    int id;
    String name;
    int gender;
    String character;
    String profile_path;

    public ActorDTO(int id, String name, int gender, String character, String profile_path) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.character = character;
        this.profile_path = profile_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
