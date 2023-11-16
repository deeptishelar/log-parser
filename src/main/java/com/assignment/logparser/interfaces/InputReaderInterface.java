package com.assignment.logparser.interfaces;

import com.assignment.logparser.exception.ApplicationException;
import java.util.List;

/**
 * can support any kind of input mode
 */
public interface InputReaderInterface {

    List<String> readInput() throws ApplicationException;
}
