package com.example.app.pipeline;

import java.util.LinkedList;
import java.util.Map;

public abstract class AbstractPipeline implements PipelineLog {

    protected LinkedList<AbstractStage> stages = new LinkedList<>();

    public void addChildStage(AbstractStage stage) {
        stages.add(stage);
    }

    public void initialize() {
    }

    public void run(Map<String, Object> data) {
        logRun();
        for (AbstractStage stage : stages) {
            stage.run(data);
        }
        logComplete();
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
