package com.example.demo.adapter;

import com.example.demo.processor.TaskProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class StartTaskAdapter implements JavaDelegate {

    private final TaskProcessor taskProcessor;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Starting task");
        taskProcessor.startTask(taskProcessor.findById(execution.getBusinessKey()).orElseThrow());
    }
}
