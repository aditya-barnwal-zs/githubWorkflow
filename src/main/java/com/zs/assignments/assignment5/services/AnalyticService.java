package com.zs.assignments.assignment5.services;

import com.zs.assignments.assignment5.models.Commit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides analytics on the commit history
 */
public class AnalyticService {
    final static Logger LOGGER = LogManager.getLogger();

    /**
     * Counts the number of commits by each developer since a given date
     *
     * @param record    a map containing developer emails as keys and list of their commit history as values
     * @param sinceDate the starting date (in YYYY-MM-DD format) from which commits are counted
     */
    public void countOfCommitsForEachDay(HashMap<String, ArrayList<Commit>> record, String sinceDate) {
        for (Map.Entry<String, ArrayList<Commit>> entry : record.entrySet()) {
            ArrayList<Commit> userCommits = entry.getValue();
            String authorEmail = entry.getKey();
            String authorName = userCommits.getFirst().getAuthorName();

            LinkedHashMap<String, Integer> commitCount = new LinkedHashMap<>();
            for (Commit commit : userCommits) {
                String currentDate = commit.getDate();
                if (currentDate.compareTo(sinceDate) < 0) break;
                commitCount.put(currentDate, commitCount.getOrDefault(currentDate, 0) + 1);
            }
            LOGGER.info(authorName + " " + authorEmail);
            commitCount.forEach((date, count) ->
                    LOGGER.info("   ├── " + date + " - " + count)
            );

        }
    }

    /**
     * Counts the total number of commits made by each developer since a given date.
     *
     * @param record    the commit history data
     * @param sinceDate the start date in YYYY-MM-DD format
     */

    public void countOfCommit(HashMap<String, ArrayList<Commit>> record, String sinceDate) {
        for (Map.Entry<String, ArrayList<Commit>> entry : record.entrySet()) {
            ArrayList<Commit> userCommits = entry.getValue();
            String authorName = userCommits.getFirst().getAuthorName();

            int commitCount = 0;
            for (Commit commit : userCommits) {
                String currentDate = commit.getDate();
                if (currentDate.compareTo(sinceDate) < 0) break;
                commitCount++;
            }
            LOGGER.info(authorName + " - " + commitCount);
        }
    }

    /**
     * Deetct the developers who has been inactive for consecutive 2 days since a given date.
     *
     * @param record    the commit history data
     * @param sinceDate the start date in YYYY-MM-DD format
     */
    public void detectDevWithInactivity(HashMap<String, ArrayList<Commit>> record, String sinceDate) {
        ArrayList<String> developers = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Commit>> entry : record.entrySet()) {
            ArrayList<Commit> userCommits = entry.getValue();

            String lastDate = userCommits.getFirst().getDate();
            for (Commit commit : userCommits) {
                String currentDate = commit.getDate();
                if (currentDate.compareTo(sinceDate) < 0) break;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(currentDate, formatter);
                LocalDate endDate = LocalDate.parse(lastDate, formatter);

                long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
                if (daysBetween > 2) {
                    developers.add(commit.getAuthorName());
                    break;
                }
                lastDate = currentDate;
            }
        }
        LOGGER.info("Developers who did not commit anything successively are: ");
        for (String name : developers)
            LOGGER.info("   ├── " + name);
    }

    /**
     * Print all the commits of every developers
     *
     * @param record the commit history data
     */
    public void printRecord(HashMap<String, ArrayList<Commit>> record) {
        for (Map.Entry<String, ArrayList<Commit>> currentRecord : record.entrySet()) {

            String email = currentRecord.getKey();
            ArrayList<Commit> commits = currentRecord.getValue();

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
}
