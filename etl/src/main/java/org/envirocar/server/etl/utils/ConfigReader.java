/*
 * Copyright (C) 2013 The enviroCar project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.envirocar.server.etl.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * @author deepaknair on 04/08/15.
 *         File that reads configuration and can be used throughout the program for deployability
 */

public class ConfigReader {

    public static String FUSEKI_URL;
    public static String MONGO_USERNAME;
    public static String MONGO_PASSWORD;
    public static String ENDPOINT_URL;


    public static void read() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        FUSEKI_URL = (properties.getProperty("fuseki_url"));
        MONGO_USERNAME = (properties.getProperty("username"));
        MONGO_PASSWORD = (properties.getProperty("password"));
        ENDPOINT_URL = (properties.getProperty("endpointurl"));

    }


}
