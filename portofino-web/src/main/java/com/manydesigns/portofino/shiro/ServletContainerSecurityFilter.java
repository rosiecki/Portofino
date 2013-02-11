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

package com.manydesigns.portofino.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Add this to web.xml to manage user authentication with the servlet container.
 *
 * @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo          - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla       - alessio.stalla@manydesigns.com
 */
public class ServletContainerSecurityFilter extends PathMatchingFilter {
    public static final String copyright =
            "Copyright (c) 2005-2013, ManyDesigns srl";

    public static final Logger logger = LoggerFactory.getLogger(ServletContainerSecurityFilter.class);

    @Override
    protected void onFilterConfigSet() throws Exception {
        if(this.appliedPaths.isEmpty()) {
            processPathConfig("/**", null);
        }
        super.onFilterConfigSet();
    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        HttpServletRequest req = (HttpServletRequest) request;
        boolean shiroAuthenticated = subject.isAuthenticated();
        boolean containerAuthenticated = req.getUserPrincipal() != null;
        logger.debug("User authenticated by Shiro? {} User authenticated by the container? {}", shiroAuthenticated, containerAuthenticated);
        if (!shiroAuthenticated && containerAuthenticated) {
            logger.debug("User is known to the servlet container, but not to Shiro, attempting programmatic login");
            try {
                subject.login(new ServletContainerToken(req));
                logger.info("User {} login", req.getUserPrincipal().getName());
            } catch (AuthenticationException e) {
                logger.warn("Programmatic login failed", e);
            }
        } else if(shiroAuthenticated && !containerAuthenticated) {
            logger.debug("User is authenticated to Shiro, but not to the servlet container; logging out of Shiro.");
            String userName = ShiroUtils.getPrimaryPrincipal(SecurityUtils.getSubject()) + "";
            subject.logout();
            logger.info("User {} logout", userName);
            //TODO valutare effetti del distruggere o meno la sessione
            /*HttpSession session = req.getSession(false);
            if(session != null) {
                session.invalidate();
            }*/
        }
        return true;
    }
}
