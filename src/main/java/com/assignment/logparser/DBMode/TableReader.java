package com.assignment.logparser.DBMode;

import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.interfaces.InputReaderInterface;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TBD This class should read logs from a DB table. The DB connection can be configured in properties file
 */
public class TableReader implements InputReaderInterface {

    private static final Logger LOG = LoggerFactory.getLogger(TableReader.class);

    @Override
    public List<String> readInput() throws ApplicationException {
        throw new ApplicationException("This reader is not supported", LOG, new RuntimeException());
    }
}
