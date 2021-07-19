package com.example.app.event;

import java.util.Map;

public class HandlerImplA extends AbstractHandler {

    @Override
    public void doHandle(Map<String, Object> data) {
        System.out.println("pipeline handler ....");
    }

}
