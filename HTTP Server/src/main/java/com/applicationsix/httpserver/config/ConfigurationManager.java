package com.applicationsix.httpserver.config;

import com.applicationsix.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {

        if (myConfigurationManager == null)
            myConfigurationManager = new ConfigurationManager();
        return myConfigurationManager;
    }

    /**
    * Used to Load a configuration file by the path provided
    */

    public void loadConfigurationFile (String filePath)  {
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }


        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File",e);
        }

        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File, internal",e);
        }

    }


    /**
     * Returns the Current loaded Configuration
     *
     * @return
     */

    public Configuration getCurrentConfiguration() {
        if (myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No current configuration set.");
        }
        return myCurrentConfiguration;
    }

}
