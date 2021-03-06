/*
 * Copyright (C) 2005-2017 ManyDesigns srl.  All rights reserved.
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

package com.manydesigns.portofino.pageactions;

import com.manydesigns.elements.annotations.CssClass;
import com.manydesigns.elements.annotations.Label;
import com.manydesigns.elements.annotations.Required;
import com.manydesigns.elements.annotations.Updatable;
import com.manydesigns.elements.util.BootstrapSizes;

/**
 * @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo          - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla       - alessio.stalla@manydesigns.com
 */
public class EditPage {
    public static final String copyright =
            "Copyright (C) 2005-2017 ManyDesigns srl";

    @Label("id")
    @Updatable(false)
    public String id;

    @Label("title")
    @Required
    @CssClass(BootstrapSizes.FILL_ROW)
    public String title;

    @Label("description")
    @CssClass(BootstrapSizes.FILL_ROW)
    public String description;

    @Label("template")
    @Required
    public String template;

    @Label("detail.template")
    @Required
    public String detailTemplate;

    @Label("apply.template.recursively")
    @Required
    public boolean applyTemplateRecursively;

}
