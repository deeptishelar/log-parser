package com.assignment.logparser;

import static org.mockito.Mockito.when;

import com.assignment.logparser.FileMode.ReportFileWriter;
import com.assignment.logparser.config.ParserConfig;
import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import com.assignment.logparser.model.Report;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReportFileWriterTest {

    @Mock
    ParserConfig parserConfig;
    @InjectMocks
    ReportFileWriter reportFileWriter;
    @Mock
    private transient ReportLogger logger;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        when(this.parserConfig.getOutputpath()).thenReturn("src/test/resources/logs/");
        when(this.parserConfig.getInputfilename()).thenReturn("sample_report.txt");

    }

    @Test
    void test1() {
        Map<String, List<String>> content = new HashMap<>();
        List<String> rows = new ArrayList<>();
        rows.add("Answer 1");
        content.put("Question 1", rows);
        Report report = new Report(content);
        reportFileWriter.writeData(report);
        File file = new File(parserConfig.getOutputpath() + "Report_sample_report.txt");
        Assertions.assertTrue(file.exists());
    }

    @Test
    void testError() {
        Throwable exception = Assertions.assertThrows(ApplicationException.class,
            () -> reportFileWriter.writeData(null));
        Assertions.assertNotEquals(exception.getMessage().indexOf("Error"), -1);
    }

}
