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
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReportDataGeneratorTests {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @InjectMocks
    ReportDataGenerator generator;
    @Mock
    List<LogObject> logs;
    @Mock
    ParserConfig parserConfig;
    @Mock
    ReportLogger logger;

    @Test
    void testGenerateError() {
        Throwable exception = Assertions.assertThrows(ApplicationException.class, () -> generator.getReportData(null));
        Assertions.assertNotEquals(exception.getMessage().indexOf("Error"), -1);
    }

    @Test
    void testGenerateSuccess() {
        List<LogObject> logs = new ArrayList<>();
        final LogObject logObject = new LogObject();
        logObject.setUrl("URL");
        logObject.setIpAddress("IP");
        logs.add(logObject);
        when(parserConfig.getTopcount()).thenReturn("3");
        final Report reportData = generator.getReportData(logs);
        Assertions.assertNotNull(reportData);

        Assertions.assertDoesNotThrow(() -> generator.getReportData(logs));
    }
}
