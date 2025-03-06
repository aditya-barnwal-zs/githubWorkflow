package com.zs.assignments.assignment7;

import com.zs.assignments.assignment7.config.DatabaseConfig;
import com.zs.assignments.assignment7.controllers.Controller;

public class MainApplication {
    public static void main(String[] args) {
        DatabaseConfig databaseConfig = DatabaseConfig.getDatabaseConfig();
        Controller controller = new Controller();
        controller.runProgram();

    }
}
