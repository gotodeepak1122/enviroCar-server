package org.envirocar.server.etl.loader;

import com.hp.hpl.jena.rdf.model.Model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author deepaknair on 14/07/15.
 */
public class RDFDumpWriter implements DataLoader {

    public static void writeIntoFile(Model model, String file) throws FileNotFoundException {
        OutputStream outputStream = new FileOutputStream(file);
        model.write(outputStream);
    }

}
