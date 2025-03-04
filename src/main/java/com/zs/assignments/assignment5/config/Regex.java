package com.zs.assignments.assignment5.config;

import java.util.HashMap;

public class Regex {
    final static String DAYS = "Mon|Tue|Wed|Thu|Fri|Sat|Sun";
    final static String MONTHS = "Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec";
    public final static HashMap<String, String> regex = new HashMap<>() {{
        put("commitLine", "commit [a-zA-Z0-9]{40}");
        put("authorLine", "Author: [a-zA-Z-\\s]+ <[a-z.]+@[a-z]+.com>");
        put("dateLine", "Date:   (" + DAYS + ") (" + MONTHS + ") (0[1-9]|[1-2][0-9]|3[0-1]) ([0-1][0-9]|[2][2-4]):[0-5][0-9]:[0-5][0-9] [1-9][0-9]{3} \\+0530");
        put("messageLine", "Assignment [0-1]+ | [A-Z]+ | .+");
    }};
}
