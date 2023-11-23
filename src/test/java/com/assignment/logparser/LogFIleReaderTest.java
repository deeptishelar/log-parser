package com.assignment.logparser;

import static org.mockito.Mockito.when;

import com.assignment.logparser.FileMode.LogFileReader;
import com.assignment.logparser.config.ParserConfig;
import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class LogFIleReaderTest {

    @Mock
    ParserConfig parserConfig;
    @InjectMocks
    LogFileReader logFileReader;
    @Mock
    private transient ReportLogger logger;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        when(this.parserConfig.getInputfilepath()).thenReturn("src/test/resources/logs/");
        when(this.parserConfig.getInputfilename()).thenReturn("sample.log");
    }

    @Test
    void testReadInput() throws IOException {
        final List<String> strings = logFileReader.readInput();
        Assertions.assertEquals(strings.size(), 23);
    }

    @Test
    void testReadInputInvalidFIleName() throws IOException {
        when(this.parserConfig.getInputfilename()).thenReturn("sample1.log");
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class,
            () -> logFileReader.readInput());
    }

    @Test
    void testReadInputIOException() throws IOException {
        when(this.parserConfig.getInputfilename()).thenReturn("sample1.log");
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class,
            () -> logFileReader.readInput());
        Assertions.assertEquals(exception.getMessage().indexOf("File not found with given name and location"), 0);
    }

}
