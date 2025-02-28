package com.zs.assignments.assignment5.controllers;

import com.zs.assignments.assignment5.exceptions.FormatNotCorrectException;
import com.zs.assignments.assignment5.models.Commit;
import com.zs.assignments.assignment5.services.AnalyticService;
import com.zs.assignments.assignment5.services.ParserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Controller class responsible for handling the parsing logic.
 * It reads the file, processes commits, and provides analytics based on user input.
 */
public class ParserController {
    final static Logger LOGGER = LogManager.getLogger();
    final static Scanner SCANNER = new Scanner(System.in);
    final static String DAYS = "Mon|Tue|Wed|Thu|Fri|Sat|Sun";
    final static String MONTHS = "Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec";
    final static HashMap<String, String> regex = new HashMap<>() {{
        put("commitLine", "commit [a-zA-Z0-9]{40}");
        put("authorLine", "Author: [a-zA-Z-\\s]+ <[a-z.]+@[a-z]+.com>");
        put("dateLine", "Date:   (" + DAYS + ") (" + MONTHS + ") (0[1-9]|[1-2][0-9]|3[0-1]) ([0-1][0-9]|[2][2-4]):[0-5][0-9]:[0-5][0-9] [1-9][0-9]{3} \\+0530");
        put("messageLine", "    .+");
    }};

    ParserService parserService = new ParserService();
    AnalyticService analyticService = new AnalyticService();
    HashMap<String, ArrayList<Commit>> record = new HashMap<>();

    /**
     * Starts the parsing process by reading the input file and handling user operations.
     *
     * @param filePath the path to the file containing commit data
     */
    public void runParser(String filePath) {
        if (!readFile(filePath))
            return;

        int option;

        do {
            LOGGER.info("What you want to do:");
            LOGGER.info("""
                    1. Count of commits by each developer since date d\s
                    2. Count of commits by each developer since date d, for each day\s
                    3. developers who did not commit anything successively in 2 days\s
                    4. Exit""");
            option = SCANNER.nextInt();
            handleOperation(option);
        }
        while (option != 4);

    }

    /**
     * Handles user input and performs the corresponding operation.
     *
     * @param option the selected operation option
     */
    private void handleOperation(int option) {
        switch (option) {
            case 1: {
                LOGGER.info("Enter the date:");
                String sinceDate = SCANNER.next();
                if (!parserService.isValidDate(sinceDate)) {
                    LOGGER.warn("Date format is not correct");
                    return;
                }
                analyticService.countOfCommit(record, sinceDate);
                break;
            }
            case 2: {
                LOGGER.info("Enter the date:");
                String sinceDate = SCANNER.next();
                if (!parserService.isValidDate(sinceDate)) {
                    LOGGER.warn("Date format is not correct");
                    return;
                }
                analyticService.countOfCommitsForEachDay(record, sinceDate);
                break;
            }
            case 3: {
                LOGGER.info("Enter the date:");
                String sinceDate = SCANNER.next();
                if (!parserService.isValidDate(sinceDate)) {
                    LOGGER.warn("Date format is not correct");
                    return;
                }
                analyticService.detectDevWithInactivity(record, sinceDate);
                break;
            }
            case 4:
                LOGGER.info("Exiting... ");
            default:
                LOGGER.warn("Choose correct option");
                break;
        }
    }

    /**
     * Reads the commit log file and processes each commit.
     *
     * @param filePath the path to the file to be read
     * @return true if the file was read successfully, false otherwise
     */
    private boolean readFile(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            Integer currentLine = 1;
            while ((line = br.readLine()) != null) {
                Commit commit = readNextCommit(line, br, currentLine);
                if (commit == null)
                    throw new IOException("Cannot read File");

                String email = commit.getAuthorEmail();
                record.computeIfAbsent(email, k -> new ArrayList<>()).add(commit);

                currentLine++;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        LOGGER.info("File read successfully");
        return true;
    }

    /**
     * Reads a commit block from a given line
     *
     * @param line        current line
     * @param currentLine line number
     * @return new commit object or null if get an error
     * @throws FormatNotCorrectException if format of the line is not correct
     */
    private Commit readNextCommit(String line, BufferedReader br, Integer currentLine) {
        try {
            String commitHash;
            String authorName;
            String authorEmail;
            String date;
            String message;

            if (!parserService.checkFormat(line, regex.get("commitLine")))
                throw new FormatNotCorrectException("commit format is wrong at " + currentLine);
            commitHash = parserService.extractCommitHash(line);

            line = br.readLine();
            currentLine++;
            if (!parserService.checkFormat(line, regex.get("authorLine")))
                throw new FormatNotCorrectException("Author format is wrong at " + currentLine);
            authorName = parserService.extractAuthorName(line);
            authorEmail = parserService.extractAuthorEmail(line);

            line = br.readLine();
            currentLine++;
            if (!parserService.checkFormat(line, regex.get("dateLine")))
                throw new FormatNotCorrectException("Date format is wrong at " + currentLine);
            date = parserService.extractDate(line);
            if (!parserService.isValidDate(date))
                throw new FormatNotCorrectException("Date is not valid at " + currentLine);

            line = br.readLine();
            currentLine++;
            if (!line.trim().isEmpty())
                throw new FormatNotCorrectException("Empty line not available at " + currentLine);

            line = br.readLine();
            currentLine++;
            if (!parserService.checkFormat(line, regex.get("messageLine")))
                throw new FormatNotCorrectException("Message format is wrong at " + currentLine);
            message = line;

            line = br.readLine();
            currentLine++;
            if (line != null && !line.trim().isEmpty())
                throw new FormatNotCorrectException("Empty line not available at " + currentLine);

            return new Commit(commitHash, authorName, authorEmail, date, message);
        } catch (IOException | FormatNotCorrectException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
