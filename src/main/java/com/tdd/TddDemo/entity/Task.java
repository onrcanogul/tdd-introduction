package com.tdd.TddDemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Task {
    @Id
    private UUID id;
    private String text;

    public Task(String text) {
        this.id = UUID.randomUUID();
        this.text = text;
    }

    public Task() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
