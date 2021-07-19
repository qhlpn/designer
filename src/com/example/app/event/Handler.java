package com.example.app.event;

import java.util.Map;

public interface Handler {

    public void handle(Map<String, Object> data);

    public void close();
}
