package com.gabriel.prodmsv.model;

import lombok.Data;

@Data
public class Contact {
    int id;
    String name;
    String number;
    int CategoryId;
    String CategoryName;

    @Override
    public String toString(){
        return name;
    }
}