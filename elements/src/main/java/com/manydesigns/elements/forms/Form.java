/*
 * Copyright (C) 2005-2010 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * Unless you have purchased a commercial license agreement from ManyDesigns srl,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as published by
 * the Free Software Foundation.
 *
 * There are special exceptions to the terms and conditions of the GPL
 * as it is applied to this software. View the full text of the
 * exception in file OPEN-SOURCE-LICENSE.txt in the directory of this
 * software distribution.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307  USA
 *
 */

package com.manydesigns.elements.forms;

import com.manydesigns.elements.Mode;
import com.manydesigns.elements.composites.AbstractCompositeElement;
import com.manydesigns.elements.fields.Field;
import com.manydesigns.elements.reflection.PropertyAccessor;
import com.manydesigns.elements.xml.XhtmlBuffer;
import org.jetbrains.annotations.NotNull;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
*/
public class Form extends AbstractCompositeElement<FieldSet> {
    public static final String copyright =
            "Copyright (c) 2005-2010, ManyDesigns srl";

    protected final Mode mode;

    public Form(Mode mode) {
        this.mode = mode;
    }

    public void toXhtml(@NotNull XhtmlBuffer xb) {
        for (FieldSet current : this) {
            current.toXhtml(xb);
        }
    }

    public boolean isRequiredFieldsPresent() {
        for (FieldSet current : this) {
            if (current.isRequiredFieldsPresent()) {
                return true;
            }
        }
        return false;
    }

    public boolean isMultipartRequest() {
        for (FieldSet current : this) {
            if (current.isMultipartRequest()) {
                return true;
            }
        }
        return false;
    }

    public Mode getMode() {
        return mode;
    }

    public Field findFieldByPropertyName(String propertyName) {
        for (FieldSet fieldSet : this) {
            for (Field field : fieldSet) {
                PropertyAccessor accessor = field.getPropertyAccessor();
                if (accessor.getName().equals(propertyName)) {
                    return field;
                }
            }
        }
        return null;
    }
}
