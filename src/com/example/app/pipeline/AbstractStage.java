package com.example.app.pipeline;

import java.util.LinkedList;
import java.util.Map;

public abstract class AbstractStage implements PipelineLog {

    private LinkedList<AbstractStage> children = new LinkedList<>();

    public void addChildStage(AbstractStage stage) {
        children.add(stage);
    }

    public void run(Map<String, Object> data) {
        logRun();
        doRun(data);
        for (int i = 0; i < children.size(); i++) {
            AbstractStage child = children.get(i);
            child.run(data);
        }
        logComplete();
    }


    protected void doRun(Map<String, Object> data) {
        // self impl
    }

    @Override
    public void logRun() {
        System.out.println(this.getClass().getName() + " run");
    }

    @Override
    public void logComplete() {
        System.out.println(this.getClass().getName() + " complete");
    }
}
