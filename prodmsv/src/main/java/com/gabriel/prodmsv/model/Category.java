package com.gabriel.prodmsv.model;

import lombok.Data;

@Data
public class Category {
    private int id;
    private String name;

    // No-argument constructor is provided by @Data
    // Add a parameterized constructor
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
