package com.example.app.pipeline.impl;

import com.example.app.pipeline.AbstractPipeline;

public class Pipeline1 extends AbstractPipeline {

    @Override
    public void initialize() {
        Stage1 stage1 = new Stage1();
        Stage1_1 stage11 = new Stage1_1();
        Stage1_2 stage12 = new Stage1_2();
        stage1.addChildStage(stage11);
        stage1.addChildStage(stage12);
        addChildStage(stage1);

    }

}
