/*
 * Copyright (C) 2005-2013 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package com.manydesigns.portofino.application;

import com.manydesigns.portofino.AbstractPortofinoTest;
import com.manydesigns.portofino.model.database.DatabaseLogic;
import com.manydesigns.portofino.model.Model;
import com.manydesigns.portofino.model.database.*;

public class ApplicationTest extends AbstractPortofinoTest {


    Model model;

    public void setUp() throws Exception {
        super.setUp();
        model = application.getModel();
    }


    public void testFindDatabaseByName() {
        String qualifiedName = "jpetstore";
        Database database = DatabaseLogic.findDatabaseByName(model, qualifiedName);
        assertNotNull(database);
        assertEquals(qualifiedName, database.getDatabaseName());

        String dummyName = "foo";
        database = DatabaseLogic.findDatabaseByName(model, dummyName);
        assertNull(database);
    }

    public void testFindSchemaByQualifiedName() {
        Schema schema = DatabaseLogic.findSchemaByName(model, "jpetstore", "PUBLIC");
        assertNotNull(schema);
        assertEquals("jpetstore.PUBLIC", schema.getQualifiedName());

        schema = DatabaseLogic.findSchemaByName(model, "jpetstore", "foo");
        assertNull(schema);
    }

    public void testFindTableByQualifiedName() {
        Table table = DatabaseLogic.findTableByName(model, "jpetstore", "PUBLIC", "product");
        assertNotNull(table);
        assertEquals("jpetstore.PUBLIC.product", table.getQualifiedName());

        table = DatabaseLogic.findTableByName(model, "jpetstore", "PUBLIC", "foo");
        assertNull(table);
    }

    public void testFindColumnByQualifiedName() {
        Column column = DatabaseLogic.findColumnByName(model, "jpetstore", "PUBLIC", "product", "category");
        assertNotNull(column);
        assertEquals("jpetstore.PUBLIC.product.category", column.getQualifiedName());

        column = DatabaseLogic.findColumnByName(model, "jpetstore", "PUBLIC", "product", "foo");
        assertNull(column);
    }
}