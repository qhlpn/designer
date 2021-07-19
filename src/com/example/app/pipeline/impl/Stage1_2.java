package com.example.app.pipeline.impl;

import com.example.app.pipeline.AbstractStage;

import java.util.Map;

public class Stage1_2 extends AbstractStage {

    @Override
    protected void doRun(Map<String, Object> data) {
        System.out.println("stage 1.2");
    }

}
