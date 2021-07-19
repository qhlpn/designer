package com.example.app.event;

import java.util.Map;

public class HandlerImplB extends AbstractHandler {

    @Override
    public void doHandle(Map<String, Object> data) {
        System.out.println("audit handler ...");
    }

}
