package com.lorenzorigato.base.network.service.dto;

public class GenreDTO {

    // Class attributes ****************************************************************************
    int id;
    String name;


    // Constructor *********************************************************************************
    public GenreDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }


    // Class methods *******************************************************************************
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

    @Override
    public String toString() {
        return "GenreDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
