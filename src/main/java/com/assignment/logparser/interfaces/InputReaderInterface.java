package com.assignment.logparser.interfaces;

import com.assignment.logparser.exception.ApplicationException;
import java.util.List;

public interface InputReaderInterface {

    List<String> readInput() throws ApplicationException;
}
