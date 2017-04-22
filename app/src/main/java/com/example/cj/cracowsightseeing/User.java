package com.example.cj.cracowsightseeing;

public class User {
    private String name;
    private Integer level;
    private Integer score;

    User(String name, Integer level, Integer score) {
        this.name = name;
        this.score = score;
        this.level = level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScore() {
        return this.score;
    }
}
