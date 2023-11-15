package com.assignment.logparser;

import static org.mockito.Mockito.when;

import com.assignment.logparser.FileMode.LogFileReader;
import com.assignment.logparser.FileMode.ReportFileWriter;
import com.assignment.logparser.common.LogReportFactory;
import com.assignment.logparser.config.ParserConfig;
import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import com.assignment.logparser.scanner.RegExScanner;
import java.io.File;
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
    private transient ReportLogger logger;

    //    @BeforeEach
    void init() {
        this.parserConfig = Mockito.mock(ParserConfig.class);
        MockitoAnnotations.initMocks(this);
        when(this.parserConfig.getInputmode()).thenReturn("FILE");
        when(this.parserConfig.getOutputmode()).thenReturn("FILE");
        when(this.parserConfig.getScanmode()).thenReturn("REGEX");
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
        Assertions.assertDoesNotThrow(() -> factory.generateReport());

    }


    @Test
    void testReportError() {
        init();
        when(LOG.isErrorEnabled()).thenReturn(true);
        when(readerInterface.readInput()).thenThrow(ApplicationException.class);
        Assertions.assertDoesNotThrow(() -> factory.init());
        factory.generateReport();
        File file = new File(parserConfig.getOutputpath() + "Report_sample_report.txt");
        Assertions.assertFalse(file.exists());
    }
}
