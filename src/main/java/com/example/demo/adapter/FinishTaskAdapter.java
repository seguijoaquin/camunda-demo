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
public class FinishTaskAdapter implements JavaDelegate {

    private final TaskProcessor taskProcessor;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Finishing task");
        taskProcessor.finishTask(taskProcessor.findById(execution.getBusinessKey()).orElseThrow());
    }
}
