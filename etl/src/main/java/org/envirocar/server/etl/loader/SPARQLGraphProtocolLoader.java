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
package org.envirocar.server.etl.loader;

import org.envirocar.server.etl.constants.DBNames;
import org.envirocar.server.etl.constants.RDFDumpNames;

import java.io.*;

/**
 * @author deepaknair on 23/08/15.
 */


public class SPARQLGraphProtocolLoader implements TripleStoreLoader {

    static String projectBaseDirectory = System.getProperty("user.dir");


    @Override
    public String getTargetLoader() {
        return DBNames.APACHE_JENA_FUSEKI;
    }

    public void createScriptFile(String fusekiURL, String RDFLocation) throws IOException {

        String command;

        File scriptFile = new File(projectBaseDirectory + "/etl/graphStoreProtocolUploader.sh");
        if (!scriptFile.exists()) {
            scriptFile.createNewFile();
        }

        FileWriter fw = new FileWriter(scriptFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        for (String rdfDumpFile : RDFDumpNames.rdfLocations) {

            command = "curl -X POST -d @" + rdfDumpFile + " -i -H \"Content-Type: application/rdf+xml\" " + fusekiURL;
            bw.write(command);
            bw.newLine();
        }
        bw.close();

    }


    public void load(String fusekiURL, String RDFLocation) throws InterruptedException, IOException {

        createScriptFile(fusekiURL, RDFLocation);
        Process p, m = null;
        String line = "";

        m = Runtime.getRuntime().exec("chmod 777" + projectBaseDirectory + "/etl/graphStoreProtocolUploader.sh");
        m.waitFor();

        p = Runtime.getRuntime().exec("sh " + projectBaseDirectory + "/etl/graphStoreProtocolUploader.sh");
        p.waitFor();

        BufferedReader reader2 = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = reader2.readLine()) != null) {
            System.out.println(line);
        }


    }


}
