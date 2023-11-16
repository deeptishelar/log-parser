package com.assignment.logparser;

import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import com.assignment.logparser.model.LogObject;
import com.assignment.logparser.scanner.RegExScanner;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RegExScannerTest {

    @InjectMocks
    RegExScanner regExScanner;
    @Mock
    private transient ReportLogger logger;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testScan() {
        List<String> logLines = new ArrayList<>();
        logLines.add(
            "168.41.191.40 - - [09/Jul/2018:10:11:30 +0200] \"GET http://example.net/faq/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (Linux; U; Android 2.3.5; en-us; HTC Vision Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1\"\n");
        logLines.add(
            "177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"POST /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"\n");
        final List<LogObject> logObjects = regExScanner.scan(logLines);
        Assertions.assertEquals(logObjects.size(), logLines.size());
        Assertions.assertEquals("http://example.net/faq/", logObjects.get(0).getUrl());
        Assertions.assertEquals("/intranet-analytics/", logObjects.get(1).getUrl());
        Assertions.assertEquals("168.41.191.40", logObjects.get(0).getIpAddress());
        Assertions.assertEquals("177.71.128.21", logObjects.get(1).getIpAddress());
    }

    @Test
    void testScanNoInput() {
        Assertions.assertThrows(ApplicationException.class, () -> regExScanner.scan(null));
    }

    @Test
    void testScanObjectData() {
        List<String> logLines = new ArrayList<>();
        logLines.add(
            "168.41.191.40 - - [09/Jul/2018:10:11:30 +0200] \"GET http://example.net/faq/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (Linux; U; Android 2.3.5; en-us; HTC Vision Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1\"\n");
        logLines.add(
            "177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"\n");
        final List<LogObject> logObjects = regExScanner.scan(logLines);
        Assertions.assertNotEquals(logObjects.size(), -1);
        Assertions.assertEquals("168.41.191.40", logObjects.get(0).getIpAddress());
        Assertions.assertEquals("/intranet-analytics/", logObjects.get(1).getUrl());

    }

}
