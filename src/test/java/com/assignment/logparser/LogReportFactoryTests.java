package com.assignment.logparser;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.assignment.logparser.FileMode.LogFileReader;
import com.assignment.logparser.FileMode.ReportFileWriter;
import com.assignment.logparser.common.LogReportFactory;
import com.assignment.logparser.common.ReportDataGenerator;
import com.assignment.logparser.config.ParserConfig;
import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import com.assignment.logparser.model.LogObject;
import com.assignment.logparser.model.Report;
import com.assignment.logparser.scanner.RegExScanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LogReportFactoryTests {

    @Mock
    private static Logger LOG;

    @InjectMocks
    LogReportFactory factory;
    ParserConfig parserConfig;
    @Mock
    LogFileReader readerInterface;
    @Mock
    ReportFileWriter fileWriter;
    @Mock
    RegExScanner regExScanner;
    @Mock
    ReportDataGenerator generator;
    List<LogObject> logObjects = new ArrayList<>();
    @Mock
    private transient ReportLogger logger;

    //    @BeforeEach
    void init() {
        this.parserConfig = Mockito.mock(ParserConfig.class);
        MockitoAnnotations.initMocks(this);
        when(this.parserConfig.getInputmode()).thenReturn("FILE");
        when(this.parserConfig.getOutputmode()).thenReturn("FILE");
        when(this.parserConfig.getScanmode()).thenReturn("REGEX");
        when(this.parserConfig.getOutputpath()).thenReturn("src/test/resources/logs/");
        when(this.parserConfig.getOutputfilename()).thenReturn("sample_report.txt");

        List<String> logLines = new ArrayList<>();
        logLines.add(
            "168.41.191.40 - - [09/Jul/2018:10:11:30 +0200] \"GET http://example.net/faq/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (Linux; U; Android 2.3.5; en-us; HTC Vision Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1\"\n");
        logLines.add(
            "177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"\n");

        LogObject log1 = new LogObject();
        log1.setIpAddress("168.41.191.40");
        log1.setUrl("http://example.net/faq/");
        logObjects.add(log1);
        LogObject log2 = new LogObject();
        log2.setIpAddress("177.71.128.21");
        log2.setUrl("/intranet-analytics/");
        logObjects.add(log2);
        when(this.readerInterface.readInput()).thenReturn(logLines);
        when(this.regExScanner.scan(logLines)).thenReturn(logObjects);


    }

    @Test
    void testConfigNull() {
        Assertions.assertThrows(ApplicationException.class, () -> factory.init());
    }

    @Test
    void testConfigReadInit() {
        init();
        Assertions.assertDoesNotThrow(() -> factory.init());
    }

    @Test
    void testReport() {
        init();
        Map<String, List<String>> reportMap = new HashMap();
        reportMap.put("Active IP Addresses : ", new ArrayList<>());
        reportMap.put("Most used urls : ", new ArrayList<>());
        reportMap.put("Unique IP Addresses : ", new ArrayList<>());
        final Report report = new Report(reportMap);
        when(this.generator.getReportData(logObjects)).thenReturn(report);
        Assertions.assertDoesNotThrow(() -> factory.init());
        Assertions.assertDoesNotThrow(() -> factory.generateReport());
        verify(fileWriter, times(1)).writeData(report);
    }

    @Test
    void testReportError() throws IOException {
        init();
        when(LOG.isErrorEnabled()).thenReturn(true);
        when(readerInterface.readInput()).thenThrow(ApplicationException.class);
        Assertions.assertDoesNotThrow(() -> factory.init());
        String outputPath = parserConfig.getOutputpath() + "Report_sample_report.txt";
        Path path = Paths.get(outputPath);
        Files.deleteIfExists(path);
        File file = new File(outputPath);
        Assertions.assertFalse(file.exists());
    }
}
