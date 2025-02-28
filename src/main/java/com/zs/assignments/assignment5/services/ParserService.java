package com.zs.assignments.assignment5.services;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Provides methods to check format and extract data from the given line
 */
public class ParserService {

    /**
     * Mapping of month name to numerical representations.
     */
    final static HashMap<String, String> MONTH_NUMBER = new HashMap<>() {{
        put("Jan", "01");
        put("Feb", "02");
        put("Mar", "03");
        put("Apr", "04");
        put("May", "05");
        put("Jun", "06");
        put("Jul", "07");
        put("Aug", "08");
        put("Sep", "09");
        put("Oct", "10");
        put("Nov", "11");
        put("Dec", "12");
    }};

    /**
     * Validates whether a given line matches to given regex pattern.
     *
     * @param line  the input line to validate
     * @param regex the regex pattern to match against
     * @return true if the line matches the pattern, false otherwise
     */
    public boolean checkFormat(String line, String regex) {
        return line != null && Pattern.matches(regex, line);
    }

    /**
     * Validates whether a given string represents a valid date in the format YYYY-MM-DD.
     *
     * @param date the date string to validate
     * @return true if the date is valid, false otherwise
     */
    public boolean isValidDate(String date) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false); // Ensures strict date validation
        try {
            sdf.parse(date);
            return true;
        } catch (java.text.ParseException e) {
            return false;
        }
    }

    public String extractCommitHash(String commitLine) {
        String[] parts = commitLine.split(" ");
        return parts[1];
    }

    public String extractAuthorName(String authorLine) {
        String[] parts = authorLine.split(" ");
        String authorName = parts[1];
        for (int i = 2; i < parts.length - 1; i++)
            authorName += " " + (parts[i]);
        return authorName;
    }

    public String extractAuthorEmail(String authorLine) {
        String[] parts = authorLine.split(" ");
        int emailIndex = parts.length - 1;
        String email = parts[emailIndex].substring(1, parts[emailIndex].length() - 1);
        return email;
    }

    public String extractDate(String dateLine) {
        String[] parts = dateLine.split(" ");
        // Format YYYY-MM-DD
        String year = parts[7];
        String month = MONTH_NUMBER.get(parts[4]);
        String dateofMonth = parts[5];
        String date = year + "-" + month + "-" + dateofMonth;
        return date;
    }
}
