package com.zs.assignments.assignment7;

import com.zs.assignments.assignment7.config.DatabaseConfig;
import com.zs.assignments.assignment7.dao.QueryDao;
import com.zs.assignments.assignment7.dao.QueryDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class QueryService {
    QueryDao queryDao = new QueryDaoImpl();

    public void explainQuery() {
        queryDao.explainQuery();
    }

    public void explainAnalyzeQuery() {
        queryDao.explainAnalyzeQuery();
    }
}
