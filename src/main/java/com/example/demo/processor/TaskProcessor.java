package com.example.demo.processor;

import com.example.demo.model.Task;
import com.example.demo.model.TaskRequest;
import com.example.demo.model.TaskResponse;
import com.example.demo.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class TaskProcessor {

    private final TaskRepository taskRepository;
    private final RuntimeService runtimeService;
    private final HistoryService historyService;

    public TaskResponse createTask(TaskRequest request) {
        String id = UUID.randomUUID().toString();
        taskRepository.save(Task.builder()
                .id(id)
                .difficulty(request.getDifficulty())
                .name(request.getName())
                .owner(request.getOwner())
                .startedAt(OffsetDateTime.now())
                .status("CREATED")
                .build()
        );
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("createTask", id);

        boolean failed = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.getId())
                .activityId("failedEvent")
                .count() > 0;

        return failed ? new TaskResponse(id, "failed") : new TaskResponse(id, "finished");
    }

    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAll() {
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Task startTask(Task task) {
        Task startedTask = task.toBuilder()
                .status("STARTED")
                .build();
        return taskRepository.save(startedTask);
    }

    public Task finishTask(Task task) {
        Task finishedTask = task.toBuilder()
                .finishedAt(OffsetDateTime.now())
                .status("FINISHED")
                .build();
        return taskRepository.save(finishedTask);
    }

    public Task changeOwner(Task task) {
        Task ownerChangedTask = task.toBuilder()
                .owner(task.getOwner() + "'s Manager")
                .build();
        return taskRepository.save(ownerChangedTask);
    }
}
