package com.zs.assignments.assignment5.services;

import com.zs.assignments.assignment5.models.Commit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ParserService {

    final static Logger LOGGER = LogManager.getLogger();
    final static HashMap<String, String> monthNumber = new HashMap<>() {{
        put("Jan", "01"); put("Feb", "02"); put("Mar", "03"); put("Apr", "04");
        put("May", "05"); put("Jun", "06"); put("Jul", "07"); put("Aug", "08");
        put("Sep", "09"); put("Oct", "10"); put("Nov", "11"); put("Dec", "12");
    }};

    public String extractCommitHash(String commitLine){
        String[] parts = commitLine.split(" ");
        return parts[1];
    }

    public String extractAuhtorName(String authorLine){
        String[] parts = authorLine.split(" ");
        String authorName= parts[1];
        for(int i=2;i< parts.length-1; i++)
            authorName+= " " + (parts[i]);

        return authorName;
    }

    public String extractAuhtorEmail(String authorLine){
        String[] parts = authorLine.split(" ");
        int emailIndex= parts.length-1;
        String email= parts[emailIndex].substring(0,parts[emailIndex].length()-1);

        return email;
    }

    public String extractDate(String dateLine){
        String[] parts = dateLine.split(" ");
        // Format YYYY-MM-DD
        String year= parts[7];
        String month= monthNumber.get(parts[4]);
        String dateofMonth = parts[5];
        String date= year + "-" + month  + "-" + dateofMonth;

        return date;
    }
    public void printRecord(HashMap<String, ArrayList<Commit>> record) {
        for (Map.Entry<String, ArrayList<Commit>> entry : record.entrySet()) {
            String email = entry.getKey();
            ArrayList<Commit> commits = entry.getValue();

            LOGGER.info("Author Email: " + email);
            for (Commit commit : commits) {
                LOGGER.info("  Commit Hash: " + commit.getCommitHash());
                LOGGER.info("  Author: " + commit.getAuthorName());
                LOGGER.info("  Date: " + commit.getDate());
                LOGGER.info("  Message: " + commit.getMessage());
                LOGGER.info("");
            }
        }
    }

    public Boolean checkCommit(String commit){
        String commitRegex="commit [a-zA-Z0-9]{40}";
        return Pattern.matches(commitRegex,commit);
    }

    public Boolean checkAuthor(String author){
        String authorRegex="Author: [a-zA-Z\\s]+ <[a-z.]+@[a-z]+.com>";
        return Pattern.matches(authorRegex,author);
    }

    public Boolean checkDate(String date){
        String dateRegex="Date:   \\b(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\b \\b(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\b \\b(0[1-9]|[1-2][0-9]|3[0-1])\\b \\b([0-1][0-9]|[2][2-4])\\b:[0-5][0-9]:[0-5][0-9] [1-9][0-9]{3} \\+0530";
        return Pattern.matches(dateRegex, date);
    }

    public Boolean checkMessage(String message){
        String messageRegex="    .+";
        return Pattern.matches(messageRegex, messageRegex);
    }
}
