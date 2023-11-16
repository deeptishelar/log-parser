package com.assignment.logparser;

import com.assignment.logparser.DBMode.TableReader;
import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TableReaderTest {

    @InjectMocks
    TableReader reader;
    @Mock
    private transient ReportLogger logger;

    @Test
    void testScan() {
        Throwable exception = Assertions.assertThrows(ApplicationException.class, () -> reader.readInput());
        Assertions.assertEquals(exception.getMessage(), "This reader is not supported");
    }
}
