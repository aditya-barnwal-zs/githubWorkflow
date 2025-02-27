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
}
