package com.zs.assignments.assignment5.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class parserController {
    public void runParser(String filePath){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

}
