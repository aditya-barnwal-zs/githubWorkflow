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

    public Boolean checkDate(String date){
        String dateRegex="Date:   \\b(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\b \\b(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\b \\b(0[1-9]|[1-2][0-9]|3[0-1])\\b \\b([0-1][0-9]|[2][2-4])\\b:[0-5][0-9]:[0-5][0-9] [1-9][0-9]{3} \\+0530";
        return Pattern.matches(dateRegex, date);
    }

    public Boolean checkmessage(String message){
        String messageRegex="    .+";
        return Pattern.matches(messageRegex, messageRegex);
    }
}
