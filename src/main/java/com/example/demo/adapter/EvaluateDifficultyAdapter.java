package com.example.demo.adapter;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class EvaluateDifficultyAdapter implements JavaDelegate {

    TaskRepository taskRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Evaluating difficulty");

        Task task = taskRepository.findById(execution.getBusinessKey()).orElseThrow();

        execution.setVariable("isVeryDifficult", task.isVeryDifficult());
    }
}
