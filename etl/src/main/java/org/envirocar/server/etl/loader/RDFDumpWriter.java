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
        model.setNsPrefix("base", "http://envirocar.org/");
        model.write(outputStream);
    }


}
