package com.springsimple.Rest.Request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MoveRequest {

    private int column;
    private int row;

    @JsonCreator
    public MoveRequest(@JsonProperty("column") int column, @JsonProperty("row") int row) {
        this.column = column;
        this.row = row;
    }


    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }


}
