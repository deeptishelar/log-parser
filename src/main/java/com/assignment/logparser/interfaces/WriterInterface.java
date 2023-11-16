package com.assignment.logparser.interfaces;

import com.assignment.logparser.model.Report;

/**
 * can support any kind of output mode
 */
public interface WriterInterface {

    void writeData(Report data);
}
