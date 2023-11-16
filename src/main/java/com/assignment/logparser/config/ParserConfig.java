package com.assignment.logparser.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * reads and populates as per application.properties file
 */
@Configuration
@ConfigurationProperties(prefix = "logparser")
@Getter
@Setter
public class ParserConfig {

    /**
     * scanmode : Either REGEX or STRING_SPLIT
     */
    private String scanmode;
    /**
     * outputmode : Either FILE or CONSOLE
     */
    private String outputmode;
    /**
     * inputmode : Either FILE or DB
     */
    private String inputmode;
    /**
     * topcount can be any number like top 3 active urls etc
     */
    private int topcount;
    private String inputfilepath;
    private String inputfilename;
    private String outputfilename;
    private String outputpath;
}
