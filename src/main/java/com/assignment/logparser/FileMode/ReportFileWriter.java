package com.assignment.logparser.FileMode;

import com.assignment.logparser.config.ParserConfig;
import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import com.assignment.logparser.interfaces.WriterInterface;
import com.assignment.logparser.model.Report;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportFileWriter implements WriterInterface {

    private static final Logger LOG = LoggerFactory.getLogger(ReportFileWriter.class);

    @Autowired
    ParserConfig config;
    @Autowired
    ReportLogger logger;

    @Override
    public void writeData(Report data) {
        printData(data);
    }

    private void printData(Report data) throws ApplicationException {
        if (data == null) {
            final String message = "Error occurred while writing the report because the input data is null ";
            throw new ApplicationException(message, LOG, new RuntimeException());
        }
        final Map<String, List<String>> content = data.getContent();

        String outputFileName = config.getOutputpath() + "Report_" + config.getInputfilename();
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter(outputFileName);
            printWriter = new PrintWriter(fileWriter);
            printWriter.print("Log Report");
            PrintWriter finalPrintWriter = printWriter;
            content.forEach((k, v) ->
                finalPrintWriter.printf("\n<span><b> %s </b></span>--> %s \n", k, String.join("\n", v))
            );
            printWriter.close();
            logger.info(LOG, "Report printed successfully to the output file -> " + outputFileName);
        } catch (IOException e) {
            String message =
                "Error occurred while writing data to the file (could be output directory not found) " + outputFileName;
            throw new ApplicationException(message, LOG, e);
        }
    }
}
