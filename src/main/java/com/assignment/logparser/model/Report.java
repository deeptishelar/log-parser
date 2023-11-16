package com.assignment.logparser.model;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * object for output
 */
@AllArgsConstructor
@Getter
public class Report {

    Map<String, List<String>> content;

}
