package com.example.demo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "task")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Task {
    @Id
    private String id;
    private String name;
    private String owner;
    private Integer difficulty;
    private String status;
    private OffsetDateTime startedAt;
    private OffsetDateTime finishedAt;

    public boolean isVeryDifficult() {
        return difficulty >= 10;
    }
}
