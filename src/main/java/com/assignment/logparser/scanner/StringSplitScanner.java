package com.assignment.logparser.scanner;

import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.interfaces.DataScannerInterface;
import com.assignment.logparser.model.LogObject;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TBD can be developed to support parsing based on string split technique
 */
public class StringSplitScanner implements DataScannerInterface {

    private static final Logger LOG = LoggerFactory.getLogger(StringSplitScanner.class);

    @Override
    public List<LogObject> scan(List<String> inputData) {
        throw new ApplicationException("This scanner is not supported", LOG, new RuntimeException());

    }
}
