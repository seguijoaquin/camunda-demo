package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskRequest {
    private final String name;
    private final String owner;
    private final Integer difficulty;
}
