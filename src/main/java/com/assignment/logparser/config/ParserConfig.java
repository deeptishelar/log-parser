package com.assignment.logparser.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "logparser")
@Getter
@Setter
public class ParserConfig {

    private String scanmode;
    private String outputmode;
    private String inputfilepath;
    private String inputfilename;
    private String outputpath;
    private String topcount;
    private String inputmode;
}
