package com.antisocial.model;


import java.util.ArrayList;
import java.util.List;

public enum State {
    ACTIVE("ACTIVE"),
    BANNED("BANNED"),
        DELETED("DELETED"),
            REGISTER("REGISTER");

    private String state;

    private State(final String state){
        this.state = state;
    }

    public String getState(){
        return this.state;
    }

    @Override
    public String toString(){
        return this.state;
    }

    public String getName(){
        return this.name();
    }


}
