package com.assignment.logparser.common;

import com.assignment.logparser.model.LogObject;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

public class LogDataComparator implements Comparator<Entry<String, List<LogObject>>> {

    /**
     * Implements descending order.
     */
    @Override
    public int compare(Entry<String, List<LogObject>> o1, Entry<String, List<LogObject>> o2) {
        if (o1.getValue().size() < o2.getValue().size()) {
            return 1;
        } else if (o1.getValue().size() > o2.getValue().size()) {
            return -1;
        }
        return 0;
    }

}
