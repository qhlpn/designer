package com.example.app.event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

    private static volatile Server instance;
    private Map<String, Handler> handlerMap;
    private ThreadPoolExecutor pool;

    private Server() {
    }

    public static Server getInstance() {
        if (instance == null) {
            synchronized (Server.class) {
                if (instance == null) {
                    instance = new Server();
                    instance.configure();
                }
            }
        }
        return instance;
    }

    private void configure() {
        handlerMap = new HashMap<String, Handler>() {{
            put("audit", new HandlerImplB());
            put("pipeline", new HandlerImplA());
            // load from config
        }};
        pool = new ThreadPoolExecutor(10,
                10,
                10,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public void doEvent(Map<String, Object> data) {
        String module = (String) data.get("type");
        Handler handler = handlerMap.get(module);
        pool.execute(() -> {
            handler.handle(data);
            handler.close();
        });
    }

    public static void main(String[] args) {
        Server eventServer = Server.getInstance();
        eventServer.doEvent(new HashMap<String, Object>() {{
            put("type", "pipeline");
            put("isAsync", "true");
        }});
        eventServer.doEvent(new HashMap<String, Object>() {{
            put("type", "audit");
        }});


    }


}
