package com.zs.assignments.assignment7.controllers;

import com.zs.assignments.assignment7.QueryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code QueryController} class manages query analysis operations.
 * It interacts with {@code QueryService} to perform query explanations and analysis.
 */
public class QueryController {
    private static final Logger LOGGER = LogManager.getLogger();
    private QueryService queryService;

    public QueryController() {
        queryService = new QueryService();
    }

    /**
     * Executes query analysis by calling methods from {@code QueryService}.
     * Logs information about the query analysis process.
     */
    public void analyzeQuery() {
        LOGGER.info("Analyzing query:");
        queryService.explainQuery();

        LOGGER.info("Analyzing query:");
        queryService.explainAnalyzeQuery();
    }
}
