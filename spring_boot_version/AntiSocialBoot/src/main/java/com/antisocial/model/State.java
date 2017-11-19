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

    public static List<String> getAccountStates(){
        List<String> list = new ArrayList<>();
        list.add(State.ACTIVE.getName());
        list.add(State.BANNED.getName());
        list.add(State.DELETED.getName());
        list.add(State.REGISTER.getName());
        return list;
    }


}
