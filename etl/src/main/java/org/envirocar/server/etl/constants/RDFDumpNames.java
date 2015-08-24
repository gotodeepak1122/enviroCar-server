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
package org.envirocar.server.etl.constants;

import org.envirocar.server.etl.utils.ConfigReader;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author deepaknair on 22/08/15.
 */

public class RDFDumpNames {


    public static String MEASUREMENTS_RDF_LOCATION = "measurements.rdf";
    public static String PHENOMENONS_RDF_LOCATION = "phenomenons.rdf";
    public static String SENSORS_RDF_LOCATION = "sensors.rdf";
    public static String TRACKS_RDF_LOCATION = "tracks.rdf";
    public static String USERS_RDF_LOCATION = "users.rdf";
    public static ArrayList<String> rdfLocations = new ArrayList<String>();

    static {

        try {
            ConfigReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MEASUREMENTS_RDF_LOCATION = ConfigReader.DUMP_LOCATION + MEASUREMENTS_RDF_LOCATION;
        PHENOMENONS_RDF_LOCATION = ConfigReader.DUMP_LOCATION + PHENOMENONS_RDF_LOCATION;
        SENSORS_RDF_LOCATION = ConfigReader.DUMP_LOCATION + SENSORS_RDF_LOCATION;
        TRACKS_RDF_LOCATION = ConfigReader.DUMP_LOCATION + TRACKS_RDF_LOCATION;
        USERS_RDF_LOCATION = ConfigReader.DUMP_LOCATION + USERS_RDF_LOCATION;


        rdfLocations.add(MEASUREMENTS_RDF_LOCATION);
        rdfLocations.add(PHENOMENONS_RDF_LOCATION);
        rdfLocations.add(SENSORS_RDF_LOCATION);
        rdfLocations.add(TRACKS_RDF_LOCATION);
        rdfLocations.add(USERS_RDF_LOCATION);

    }


}
