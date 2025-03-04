package com.zs.assignments.assignment5.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a commit in Git Log.
 * Stores commit details such as hash, author, date, and message.
 */
@Getter
@Setter
public class Commit {
    private String commitHash;
    private String authorName;
    private String authorEmail;
    private String date;
    private String message;

    /**
     * Constructs a new Commit instance.
     *
     * @param commitHash  the unique hash of the commit
     * @param authorName  the name of the author
     * @param authorEmail the email of the author
     * @param date        the date of the commit in YYYY-MM-DD format
     * @param message     the commit message
     */
    public Commit(String commitHash, String authorName, String authorEmail, String date, String message) {
        this.commitHash = commitHash;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.date = date;
        this.message = message;
    }
}
