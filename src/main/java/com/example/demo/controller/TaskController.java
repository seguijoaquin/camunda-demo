package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.TaskRequest;
import com.example.demo.model.TaskResponse;
import com.example.demo.processor.TaskProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class TaskController {

    private final TaskProcessor taskProcessor;

    public TaskController(TaskProcessor taskProcessor) {
        this.taskProcessor = taskProcessor;
    }

    @RequestMapping(path = "/tasks", method = RequestMethod.POST)
    public ResponseEntity<TaskResponse> createTask(@RequestBody(required = false) TaskRequest taskRequest) {
        return ResponseEntity.status(CREATED).body(taskProcessor.createTask(taskRequest));
    }

    @RequestMapping(path = "/tasks/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> getTask(@PathVariable String id) {
        Optional<Task> optionalTask = taskProcessor.findById(id);
        if (optionalTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(OK).body(optionalTask.get());
    }

    @RequestMapping(path = "/tasks", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.status(OK).body(taskProcessor.getAll());
    }

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public ResponseEntity<String> ping() {
        return ResponseEntity.status(OK).body("pong");
    }

}
