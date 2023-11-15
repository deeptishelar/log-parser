package com.assignment.logparser.common;

import com.assignment.logparser.config.ParserConfig;
import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import com.assignment.logparser.model.LogObject;
import com.assignment.logparser.model.Report;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDataGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(ReportDataGenerator.class);
    @Autowired
    ParserConfig parserConfig;
    List<LogObject> logs;
    @Autowired
    private ReportLogger logger;

    private List<String> getUniqueIPAddresses() {
        List<String> uniqueIps = new ArrayList<>();
        final List<LogObject> uniqueList = logs.stream().distinct().collect(Collectors.toList());
        uniqueList.forEach(item -> uniqueIps.add(item.getIpAddress()));
        return uniqueIps;
    }

    private List<String> getActiveIpAddresses() {
        Map<String, List<LogObject>> map = logs.stream().collect(
            Collectors.groupingBy(log -> log.getIpAddress()));
        return sortAndRetunTop(map);
    }

    private List<String> sortAndRetunTop(Map<String, List<LogObject>> map) {
        List<String> topList = new ArrayList<>();
        List<Entry<String, List<LogObject>>> results = new ArrayList<>(map.entrySet());
        Collections.sort(results, new LogDataComparator());
        int topCount = Integer.parseInt(parserConfig.getTopcount());
        topCount = topCount < results.size() ? topCount : results.size();
        final List<Entry<String, List<LogObject>>> entries = results.subList(0, topCount);
        for (var entry : entries) {
            topList.add(entry.getKey());
        }
        return topList;
    }

    private List<String> getMostUsedUrls() {
        Map<String, List<LogObject>> map = logs.stream().collect(
            Collectors.groupingBy(log -> log.getUrl()));
        return sortAndRetunTop(map);
    }

    void setLogData(List<LogObject> data) {
        this.logs = data;
    }

    public Report getReportData(List<LogObject> logData) throws ApplicationException {
        if (logData == null) {
            final String message = "Error occurred while generating report because the input data is null ";
            throw new ApplicationException(message, LOG, new RuntimeException());
        }
        setLogData(logData);
        Map<String, List<String>> reportMap = new HashMap();
        reportMap.put("Active IP Addresses : ", getActiveIpAddresses());
        reportMap.put("Most used urls : ", getMostUsedUrls());
        reportMap.put("Unique IP Addresses : ", getUniqueIPAddresses());
        logger.info(LOG, "Report Data is generated successfully");
        return new Report(reportMap);
    }

}
