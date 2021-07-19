package com.example.app.event;

import java.io.Closeable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractHandler implements Handler {

    private Worker asyncWork = new Worker(this);

    @Override
    public void handle(Map<String, Object> data) {
        boolean async = Boolean.parseBoolean((String) Optional.ofNullable(data.get("isAsync")).orElse("false"));
        if (async && asyncWork != null) {
            asyncWork.handle(data);
        } else {
            doHandle(data);
        }
    }


    public abstract void doHandle(Map<String, Object> data);

    @Override
    public void close() {
        asyncWork.close();
    }


    private static class Worker implements Runnable, Closeable {

        private AbstractHandler handler;

        private ConcurrentLinkedQueue<Map<String, Object>> queue = new ConcurrentLinkedQueue<>();

        private Thread thread;

        private volatile boolean close = false;


        public Worker(AbstractHandler handler) {
            this.handler = handler;
            thread = new Thread(this);
            thread.start();
        }


        private void handle(Map<String, Object> data) {
            queue.add(data);
        }

        @Override
        public void run() {
            while (!close) {
                if (!queue.isEmpty()) {
                    Map<String, Object> data = queue.poll();
                    handler.doHandle(data);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void close() {
            close = true;
        }
    }

}
