package com.assignment.logparser.interfaces;

import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.model.LogObject;
import java.util.List;

public interface DataScannerInterface {

    List<LogObject> scan(List<String> inputData) throws ApplicationException;
}
