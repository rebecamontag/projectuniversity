package com.rebecamontag.projectuniversity.enumeration;

public enum Gender {

    FEMALE ("Female"),
    MALE ("Male");

    private String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
