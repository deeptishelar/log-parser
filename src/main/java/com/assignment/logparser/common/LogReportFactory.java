package com.assignment.logparser.common;

import com.assignment.logparser.FileMode.LogFileReader;
import com.assignment.logparser.FileMode.ReportFileWriter;
import com.assignment.logparser.config.ParserConfig;
import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import com.assignment.logparser.interfaces.DataScannerInterface;
import com.assignment.logparser.interfaces.InputReaderInterface;
import com.assignment.logparser.interfaces.WriterInterface;
import com.assignment.logparser.model.LogObject;
import com.assignment.logparser.scanner.RegExScanner;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is the backnone of the program It does the following: 1. Reads the application.propertoes file and build
 * parserConfig object 2. Instantiates appropriate classes for reading, writing and parsing 3. Calls to generate report
 * data
 * <p>
 * Please note the TBD below are to be developed in future. Base/core version is in working condition
 */
@Service
public class LogReportFactory {

    private static final Logger LOG = LoggerFactory.getLogger(LogReportFactory.class);
    InputReaderInterface readerInterface;
    DataScannerInterface dataScanner;
    WriterInterface writerInterface;
    @Autowired
    ReportDataGenerator generator;
    @Autowired
    LogFileReader fileReader;
    @Autowired
    ReportFileWriter fileWriter;
    @Autowired
    RegExScanner regExScanner;
    @Autowired
    ParserConfig config;
    @Autowired
    ReportLogger logger;

    /**
     * instantiates the appropriate components
     */
    public void init() {
        if (config == null) {
            throw new ApplicationException("Error creating configuration", LOG, new RuntimeException());
        }
        switch (config.getInputmode()) {
            case "FILE":
                readerInterface = fileReader;
                break;
            case "DB":
                readerInterface = null;//TBD TableReader;
                break;
        }
        switch (config.getScanmode()) {
            case "REGEX":
                dataScanner = regExScanner;
                break;
            case "STRING_SPLIT":
                dataScanner = null;//TBD  StringSpiltScanner()
                break;
        }
        switch (config.getOutputmode()) {
            case "FILE":
                writerInterface = fileWriter;
                break;
            case "CONSOLE":
                writerInterface = null;//TBD consoleWriter()
                break;
        }
    }

    /**
     * calls methods for configured components
     */
    public void generateReport() {
        logger.info(LOG, "Logs Report operation Started ... ");
        try {
            final List<String> inputData = readerInterface.readInput();
            List<LogObject> logData = dataScanner.scan(inputData);
            writerInterface.writeData(generator.getReportData(logData));
            logger.info(LOG, "Logs Report operation completed Successfully");
        } catch (ApplicationException e) {
            logger.logErrorWithException(e.getLOG(), e.getMessage(), e);
        } catch (Exception e) {
            String message = "An error occurred while generating log report";
            logger.logErrorWithException(LOG, message, e);
        }
    }

}
