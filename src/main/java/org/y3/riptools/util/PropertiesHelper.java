package org.y3.riptools.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/** 
 * <p>Title: org.y3.riptools.util - PropertiesHelper</p>
 * <p>Description: Utilities to support management of properties object </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 * @version $Id$
*/
public class PropertiesHelper {

    /**
     * Load properties object from path
     *
     * @param pathAndLocation path and location fo the properties file
     * @param createIfNotExists Create properties file if not exists
     * @return properties file
     * @throws java.io.IOException
     */
    public static Properties loadProperties(String pathAndLocation, boolean createIfNotExists) throws IOException {
        Properties properties = null;
        File propFile = new File(pathAndLocation);
        if (!propFile.exists() && createIfNotExists) {
            properties = createPropertiesFile(properties, pathAndLocation);
        }
        if (properties == null) {
            properties = new Properties();
        }
        FileReader reader = new FileReader(propFile);
        properties.load(reader);
        return properties;

    }
    
    /**
     * Create properties file to file system
     * @param properties properties object to store
     * @param pathAndLocation path and location fo the properties file
     * @return properties object, which is even created if it was null
     * @throws IOException 
     */
    public static Properties createPropertiesFile(Properties properties, String pathAndLocation) throws IOException {
        //try (FileWriter writer = new FileWriter(new File(pathAndLocation))) {
    	FileWriter writer = new FileWriter(new File(pathAndLocation));
    	if (properties == null) {
            properties = new Properties();
        }
        properties.store(writer, "");
        writer.close();
        return properties;
    }

    /**
     * Extension on method getIntegerFromProperties(Properties properties, String key)
     * If value is 0, it sets the default value
     * @param properties source properties object
     * @param key key of value
     * @param defaultValue default value to set if value is 0
     * @return value from properties object for given key, or default value if cannot be answered or is 0
     */
    public static int getIntegerFromProperties(Properties properties, String key, int defaultValue) {
        int value = getIntegerFromProperties(properties, key);
        if (value == 0) {
            value = defaultValue;
        }
        return value;
    }
    
    /**
     * Safe request to properties object, to receive integer value for given key
     * If request cannot be answered, the value will be 0.
     * @param properties source properties object
     * @param key key of value
     * @return value from properties object for given key, or 0 if cannot be answered
     */
    public static int getIntegerFromProperties(Properties properties, String key) {
        int value = 0;
        if (properties != null && key != null && properties.containsKey(key)) {
            Object pValue = properties.get(key);
            try {
                value = Integer.parseInt((String) pValue);
            } catch (NumberFormatException nfe) {
            }
        }
        return value;
    }

    public static void saveProperties(Properties properties, String pathAndLocation, boolean createIfNotExists) throws IOException {
        createPropertiesFile(properties, pathAndLocation);
    }
}
