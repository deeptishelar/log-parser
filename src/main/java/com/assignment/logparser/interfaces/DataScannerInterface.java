package com.assignment.logparser.interfaces;

import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.model.LogObject;
import java.util.List;

/**
 * Can support any kind of parser or scannner
 */
public interface DataScannerInterface {

    List<LogObject> scan(List<String> inputData) throws ApplicationException;
}
