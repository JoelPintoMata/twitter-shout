package com.letsShout.business;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Business class containing the shouter rules
 */
public class Shouter {

    /**
     * Shouts all the strings contained in the given list
     * @param strings, a list of strings to shout
     * @return a list of upper cased concatenated with '!' strings
     */
    public List<String> shout(List<String> strings) {
        return strings.stream().map(x -> x.toUpperCase() + "!").collect(Collectors.toList());
    }
}