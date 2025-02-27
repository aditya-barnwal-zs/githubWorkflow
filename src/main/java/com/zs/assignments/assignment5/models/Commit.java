package com.zs.assignments.assignment5.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Commit {
    String commitHash;
    String author;
    String date;
    String message;

    public Commit(String commitHash, String author, String date, String message) {
        this.commitHash = commitHash;
        this.author = author;
        this.date = date;
        this.message = message;
    }
}
