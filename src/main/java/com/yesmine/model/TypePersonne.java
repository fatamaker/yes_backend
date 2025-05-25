package com.yesmine.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TypePersonne {
    PHYSIQUE, MORALE;

    @JsonCreator
    public static TypePersonne fromString(String value) {
        return value == null ? null : TypePersonne.valueOf(value.toUpperCase());
    }
}
