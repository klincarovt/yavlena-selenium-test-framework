package com.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader{
    public static String readFromProperties(String propertyPath, String variableName){
        String result = "";
        Properties properties =  new Properties();
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(propertyPath);
            properties.load(inputStream);
            result = properties.getProperty(variableName);
        }
        catch(Exception exception){
            exception.printStackTrace();
        }finally{
            if(inputStream != null){
                try{
                    inputStream.close();
                }
                catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        }
        return result;
    }

}