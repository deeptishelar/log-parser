package com.assignment.logparser;

import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import com.assignment.logparser.scanner.StringSplitScanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StringSplitScannerTest {

    @InjectMocks
    StringSplitScanner scanner;
    @Mock
    private transient ReportLogger logger;

    @Test
    void testScan() {
        Throwable exception = Assertions.assertThrows(ApplicationException.class, () -> scanner.scan(null));
        Assertions.assertEquals(exception.getMessage(), "This scanner is not supported");


    }
}
