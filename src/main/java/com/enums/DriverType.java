package com.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum DriverType {

    GOOGLE_CHROME("CHROME"),
    FIREFOX("FIREFOX"),

    GOOGLE_CHROME_HEADLESS("CHROME_HEADLESS"),
    FIREFOX_HEADLESS("FIREFOX_HEADLESS");

    private final String text;

    private static Map<String, DriverType> driverTypesMap = populateMap();

    DriverType(final String text){
        this.text = text;
    }

    public static Map<String, DriverType> populateMap(){
        Map<String, DriverType> map = new HashMap<>();
        DriverType[] values = values();
        Stream.of(values).forEach(val -> map.put(val.text, val));
        return map;
    }

    public static DriverType parse(String text){
        if(!driverTypesMap.containsKey(text)){
            throw new IllegalArgumentException("text");
        }
        return driverTypesMap.get(text);
    }

    @Override
    public String toString(){
        return text;
    }

}
