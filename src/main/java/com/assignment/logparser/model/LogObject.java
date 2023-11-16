package com.assignment.logparser.model;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * maps to Log data The equals hashcode are overridden to supprt sorting
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class LogObject implements Comparable {

    String ipAddress;
    String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LogObject logObject = (LogObject) o;
        return Objects.equals(ipAddress, logObject.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
