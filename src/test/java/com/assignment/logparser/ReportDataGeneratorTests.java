package com.assignment.logparser;

import static org.mockito.Mockito.when;

import com.assignment.logparser.common.ReportDataGenerator;
import com.assignment.logparser.config.ParserConfig;
import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import com.assignment.logparser.model.LogObject;
import com.assignment.logparser.model.Report;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReportDataGeneratorTests {

    @InjectMocks
    ReportDataGenerator generator;
    @Mock
    List<LogObject> logs;
    @Mock
    ParserConfig parserConfig;
    @Mock
    ReportLogger logger;

    private static List<LogObject> getLogObjects() {
        List<LogObject> logs = new ArrayList<>();
        LogObject logObject = new LogObject();
        logObject.setUrl("/intranet-analytics/");
        logObject.setIpAddress("177.71.128.22");
        logs.add(logObject);
        logObject = new LogObject();
        logObject.setUrl("/intranet-analytics2/");
        logObject.setIpAddress("177.71.128.22");
        logs.add(logObject);
        logObject = new LogObject();
        logObject.setUrl("/this/page/does/not/exist/");
        logObject.setIpAddress("177.71.128.21");
        logs.add(logObject);
        logObject = new LogObject();
        logObject.setUrl("/this/page/does/not/exist/");
        logObject.setIpAddress("177.71.128.23");
        logs.add(logObject);
        logObject = new LogObject();
        logObject.setUrl("/intranet-analytics/");
        logObject.setIpAddress("177.71.128.21");
        logs.add(logObject);
        logObject = new LogObject();
        logObject.setUrl("http://example.net/blog/category/meta/");
        logObject.setIpAddress("177.71.128.21");
        logs.add(logObject);
        return logs;
    }


    @Test
    void testGenerateError() {
        Throwable exception = Assertions.assertThrows(ApplicationException.class, () -> generator.getReportData(null));
        Assertions.assertNotEquals(exception.getMessage().indexOf("Error"), -1);
    }

    @Test
    void testGenerateSuccess() {
        when(parserConfig.getTopcount()).thenReturn(3);
        List<LogObject> logs = new ArrayList<>();
        final LogObject logObject = new LogObject();
        logObject.setUrl("URL");
        logObject.setIpAddress("IP");
        logs.add(logObject);

        final Report reportData = generator.getReportData(logs);
        Assertions.assertNotNull(reportData);

        Assertions.assertDoesNotThrow(() -> generator.getReportData(logs));
    }

    @Test
    void testGetUniqIP() {
        when(parserConfig.getTopcount()).thenReturn(3);
        final List<LogObject> logs = getLogObjects();
        Assertions.assertDoesNotThrow(() -> generator.getReportData(logs));
        final Report reportData = generator.getReportData(logs);
        Assertions.assertNotNull(reportData);
        final Map<String, List<String>> content = reportData.getContent();
        Assertions.assertEquals(3, content.get("Unique IP Addresses : ").size());
        final List<String> ips = content.get("Unique IP Addresses : ");
        Assertions.assertEquals(ips.stream().findFirst().get(), "177.71.128.22");
    }

    @Test
    void testGetTop3ActiveIP() {
        when(parserConfig.getTopcount()).thenReturn(3);
        final List<LogObject> logs = getLogObjects();
        Assertions.assertDoesNotThrow(() -> generator.getReportData(logs));
        final Report reportData = generator.getReportData(logs);
        Assertions.assertNotNull(reportData);
        final Map<String, List<String>> content = reportData.getContent();
        Assertions.assertEquals(3, content.get("Active IP Addresses : ").size());
        final List<String> ips = content.get("Active IP Addresses : ");
        Assertions.assertEquals(ips.stream().findFirst().get(), "177.71.128.21");
    }

    @Test
    void testMostUsedURLs() {
        when(parserConfig.getTopcount()).thenReturn(3);
        final List<LogObject> logs = getLogObjects();
        Assertions.assertDoesNotThrow(() -> generator.getReportData(logs));
        final Report reportData = generator.getReportData(logs);
        Assertions.assertNotNull(reportData);
        final Map<String, List<String>> content = reportData.getContent();
        Assertions.assertEquals(3, content.get("Most used urls : ").size());
        final List<String> urls = content.get("Most used urls : ");
        Assertions.assertEquals(urls.stream().findFirst().get(), "/this/page/does/not/exist/");
    }
}
