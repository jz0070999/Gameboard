package com.springsimple.Rest.Response;

import java.util.List;

public class Response<T> {

    private boolean isSuccess; 
    private List<String> messages;
    private T data;

    public Response() {}

    public Response(boolean isSuccess, List<String> messages, T data) {
        this.isSuccess = isSuccess;
        this.messages = messages;
        this.data = data;
    }

    public boolean getIsSuccess(){
        return this.isSuccess;
    }
    public List<String> getMessage(){
        return this.messages;
    }
    public T getData(){
        return this.data;
    }




}
