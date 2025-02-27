package com.zs.assignments.assignment5.services;

import java.util.regex.Pattern;

public class ParserService {

    public String checkCommit(String commit){
        String commitRegex="commit [a-zA-Z0-9]{40}";
        if(!Pattern.matches(commitRegex,commit))
            return null;
        String[] parts= commit.split(" ");
        return parts[1];
    }

    public Boolean checkAuthor(String author){
        String authorRegex="Author: [a-zA-Z\\s]+ <[a-z.]+@[a-z]+.com>";
        return Pattern.matches(authorRegex,author);
    }
}
