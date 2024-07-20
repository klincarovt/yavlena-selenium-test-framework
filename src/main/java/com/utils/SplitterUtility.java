package com.utils;

public class SplitterUtility {
    public static String splitAndConcatName(String name) {
        String[] nameArray = name.split(" ");
        return String.join("+", nameArray);
    }

}
