package com.zs.assignments.assignment5.controllers;

import com.zs.assignments.assignment5.models.Commit;
import com.zs.assignments.assignment5.services.ParserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ParserController {

    final static Logger LOGGER = LogManager.getLogger();
    final static Scanner sc = new Scanner(System.in);
    ParserService parserService=new ParserService();
    HashMap<String, ArrayList<Commit>> record= new HashMap<>();

    public void runParser(String filePath){
        readFile(filePath);

        int option;

        do{
            LOGGER.info("What you want to do:");
            LOGGER.info("""
                    1. Count of commits by each developer since date d\s
                    2. Count of commits by each developer since date d, for each day\s
                    3. developers who did not commit anything successively in 2 days\s
                    4. Exit""");
            option = sc.nextInt();

            switch (option){
                case 1:{

                }
                case 2:{
                    parserService.countOfCommits(record);
                }
                case 3:{

                }
                default:
                    LOGGER.warn("Choose correct option");
                    break;
            }
        }
        while(option!=4);

    }

    private void readFile(String filePath){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                Commit commit = readNextCommit(line, br);
                if(commit == null) return ;
                String email = commit.getAuthorEmail();
                record.computeIfAbsent(email, k -> new ArrayList<>()).add(commit);
            }
        } catch (IOException e) {
            LOGGER.error("Cannot read file");
        }
        LOGGER.info("everything good");
        parserService.printRecord(record);
    }
    private Commit readNextCommit(String line, BufferedReader br){
        try {
            String commitHash;
            String authorName;
            String authorEmail;
            String date;
            String message;

            if (!parserService.checkCommit(line)) {
                LOGGER.error("commit format is wrong");
                return null;
            }
            commitHash= parserService.extractCommitHash(line);

            line = br.readLine();
            if (!parserService.checkAuthor(line)) {
                LOGGER.error("Author format is wrong");
                return null;
            }
            authorName = parserService.extractAuhtorName(line);
            authorEmail = parserService.extractAuhtorEmail(line);

            line = br.readLine();
            if (!parserService.checkDate(line)) {
                LOGGER.error("Date format is wrong");
                return null;
            }
            date= parserService.extractDate(line);

            line = br.readLine();
            if (!line.trim().isEmpty()) {
                LOGGER.error("Empty line not available");
                return null;
            }
            line = br.readLine();
            if (!parserService.checkMessage(line)) {
                LOGGER.error("Message format is wrong");
                return null;
            }
            message=line;

            line = br.readLine();
            if (line!=null && !line.trim().isEmpty()) {
                LOGGER.error("Empty line not available");
                return null;
            }

            return new Commit(commitHash, authorName, authorEmail, date, message);
        }
        catch (IOException e){
            LOGGER.error("file not found");
        }
        return null;
    }
}
