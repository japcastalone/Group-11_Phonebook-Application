package com.gabriel.prodmsapp.model;

import lombok.Data;

@Data
public class Contact {
    int id;
    String name;
    String number;

    int categoryId;

    String categoryName;

    @Override
    public String toString(){
        return name;
    }
}