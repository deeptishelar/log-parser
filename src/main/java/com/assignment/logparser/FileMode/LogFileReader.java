package com.assignment.logparser.FileMode;

import com.assignment.logparser.config.ParserConfig;
import com.assignment.logparser.exception.ApplicationException;
import com.assignment.logparser.exception.ReportLogger;
import com.assignment.logparser.interfaces.InputReaderInterface;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogFileReader implements InputReaderInterface {

    private static final Logger LOG = LoggerFactory.getLogger(LogFileReader.class);
    @Autowired
    ParserConfig parserConfig;
    @Autowired
    ReportLogger logger;

    @Override
    public List<String> readInput() throws ApplicationException {
        List<String> lines = new ArrayList<String>();
        BufferedReader br = null;
        final String pathname = parserConfig.getInputfilepath() + parserConfig.getInputfilename();
        File file = new File(pathname);
        try (
            FileInputStream inputStream = new FileInputStream(file)) {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            final String message = "File not found with given name and location " + pathname;
            throw new ApplicationException(message, LOG, e);
        } catch (IOException e) {
            final String message = "Error occurred while accessing the file at  " + pathname;
            throw new ApplicationException(message, LOG, e);
        }
        logger.info(LOG, "Input logs are read successfully from -> " + pathname);
        return lines;
    }
}
