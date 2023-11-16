package com.assignment.logparser.scanner;

import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import com.assignment.logparser.interfaces.DataScannerInterface;
import com.assignment.logparser.model.LogObject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * parsing based on regular expressions
 */
@Component
public class RegExScanner implements DataScannerInterface {

    private static final Logger LOG = LoggerFactory.getLogger(RegExScanner.class);
    private static final String URL_REGEX = "\"(GET|POST|PUT|DELETE|PATCH).+?\"";
    private static final String DATE_TIME_REGEX = "\\[\\d./[a-z]*/\\d.*?]";
    private static final String IPADDRESS_REGEX = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})";
    private static final String FILTER = "GET|POST|DELETE|PUT|PATCH|HTTP/1.1|HTTP/2|HTTP/3|HTTPS|\"";
    @Autowired
    ReportLogger logger;

    /**
     * Returns matching string with the given pattern and filters
     */
    private static String extractPatterns(String text, String pattern, String filter) {
        String extractedString = "";
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        while (m.find()) {
            final String match = m.group();

            final String filteredURL = match.replaceAll(filter, "");
            extractedString = filteredURL.trim();
        }
        return extractedString;
    }

    /**
     * Scans the input to generate objects
     *
     * @param inputData
     * @return List of LogObject after scanning the input records
     */
    @Override
    public List<LogObject> scan(List<String> inputData) throws ApplicationException {
        if (inputData == null) {
            final String message = "Input data provided is null ";
            throw new ApplicationException(message, LOG, new RuntimeException());
        }
        List<LogObject> logs = new ArrayList<>();
        inputData.forEach(line -> {
            LogObject logObject = new LogObject();
            logObject.setIpAddress(extractPatterns(line, IPADDRESS_REGEX, ""));
            logObject.setUrl(extractPatterns(line, URL_REGEX, FILTER));
            logs.add(logObject);
        });
        logger.info(LOG, "Input logs are parsed by using REGEX Successfully");
        return logs;
    }
}
