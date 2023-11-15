package com.assignment.logparser;

import com.assignment.logparser.common.LogReportFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LogParserApplication {


    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(LogParserApplication
            .class, args);
        final LogReportFactory logReportFactory = applicationContext.getBean(LogReportFactory.class);
        logReportFactory.init();
        logReportFactory.generateReport();

    }

}
