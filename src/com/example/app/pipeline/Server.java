package com.example.app.pipeline;

import com.example.app.pipeline.impl.Pipeline1;

import java.util.HashMap;
import java.util.Map;

public class Server {

    private static Map<String, Class> pipelines = new HashMap<String, Class>() {{
        put("pipeline1", Pipeline1.class);
    }};

    public void doPipeline(String pipelineId, Map<String, Object> data) {
        try {
            AbstractPipeline pipeline = (AbstractPipeline) pipelines.get(pipelineId).newInstance();
            pipeline.initialize();
            pipeline.run(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Server().doPipeline("pipeline1", null);
    }

}
