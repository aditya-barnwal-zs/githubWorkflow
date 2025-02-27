package com.zs.assignments.assignment5.controllers;

import com.zs.assignments.assignment5.services.ParserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParserController {
    ParserService parserService=new ParserService();
    Logger logger = LogManager.getLogger();

    public void runParser(String filePath){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                if(!readCommit(line, br))
                    break;
            }
        } catch (IOException e) {
            logger.error("Cannot read file");
        }
        logger.info("everything good");
    }

    private boolean readCommit(String line, BufferedReader br){
        try {
            if (!parserService.checkCommit(line)) {
                logger.error("commit format is wrong");
                return false;
            }

            line = br.readLine();
            if (!parserService.checkAuthor(line)) {
                logger.error("Author format is wrong");
                return false;
            }

            line = br.readLine();
            if (!parserService.checkDate(line)) {
                logger.error("Date format is wrong");
                return false;
            }

            line = br.readLine();
            if (!line.trim().isEmpty()) {
                logger.error("Empty line not available");
                return false;
            }

            line = br.readLine();
            if (!parserService.checkMessage(line)) {
                logger.error("Message format is wrong");
                return false;
            }

            line = br.readLine();
            if (line!=null && !line.trim().isEmpty()) {
                logger.error("Empty line not available");
                return false;
            }
        }
        catch (IOException e){
            logger.error("file not found");
        }
        return true;
    }
}
