<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"
%><%@ taglib prefix="mde" uri="/manydesigns-elements"
%><%@ taglib tagdir="/WEB-INF/tags" prefix="portofino"
%><%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<stripes:layout-render name="/skins/default/admin-page.jsp">
    <jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.actions.admin.TablesAction"/>
    <stripes:layout-component name="customScripts">
        <style type="text/css">
            .tableForm {
                overflow-x: auto; padding-right: 1px; margin-bottom: 1em;
            }
            .ui-widget-content a {
                color: blue;
            }
            .ui-tabs {
                padding: 0; border: 0;
            }
            .ui-tabs-nav {
                border: 0;
            }
            .ui-widget-header {
                background: none;
                border: 0;
                color: black;
            }
            .ui-tabs .ui-tabs-panel {
                padding: 1em 0 0 0;
            }
        </style>
        <script type="text/javascript">
            $(function() {
                $("#tabs").tabs();
                $("#shortName").after($("#editShortNameButton"));
                <c:if test="${not empty actionBean.selectedTabId}">
                    $("#tabs").tabs("select", <c:out value="${actionBean.selectedTabId});" />);
                </c:if>
            });
        </script>
    </stripes:layout-component>
    <stripes:layout-component name="pageTitle">
        Edit table ${actionBean.table.qualifiedName}
    </stripes:layout-component>
    <stripes:layout-component name="contentHeader">
        <portofino:buttons list="table-edit" cssClass="contentButton" />
    </stripes:layout-component>
    <stripes:layout-component name="portletTitle">
        Edit table ${actionBean.table.qualifiedName}
    </stripes:layout-component>
    <stripes:layout-component name="portletBody">
        <div id="tabs">
            <ul>
                <li><a href="#tabs-1">Table and columns</a></li>
                <li><a href="#tabs-2">Foreign keys and selection providers</a></li>
            </ul>
            <div id="tabs-1">
                <mde:write name="actionBean" property="tableForm" />
                <span id="editShortNameButton"><portofino:buttons list="table-edit-short-name" cssClass="portletButton" /></span>
                <div class="tableForm">
                    <fieldset class="mde-form-fieldset">
                        <legend>Columns</legend>
                        <div style="margin-top: 1em;">
                            <mde:write name="actionBean" property="columnsTableForm" />
                        </div>
                    </fieldset>
                </div>
            </div>
            <div id="tabs-2">
                <div class="tableForm">
                    <fieldset class="mde-form-fieldset">
                        <legend>Foreign keys</legend>
                        <div style="margin-top: 1em;">
                            <c:if test="${not empty actionBean.table.foreignKeys}">
                                <table>
                                    <tr>
                                        <th>Name</th>
                                        <th>Columns</th>
                                        <th>Ref. table</th>
                                        <th>Ref. columns</th>
                                    </tr>
                                    <c:forEach items="${actionBean.table.foreignKeys}" var="fk">
                                        <tr>
                                            <td colspan="${fn:length(fk.references)}"><c:out value="${fk.name}" /></td>
                                            <td><c:out value="${fk.references[0].actualFromColumn.columnName}" /></td>
                                            <td colspan="${fn:length(fk.references)}"><c:out value="${fk.toTable.tableName}" /></td>
                                            <td><c:out value="${fk.references[0].actualToColumn.columnName}" /></td>
                                        </tr>
                                        <c:forEach items="${fk.references}" var="ref" varStatus="status">
                                            <c:if test="${not status.first}">
                                                <tr>
                                                    <td><c:out value="${ref.actualFromColumn.columnName}" /></td>
                                                    <td><c:out value="${ref.actualToColumn.columnName}" /></td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </table>
                            </c:if>
                            <c:if test="${empty actionBean.table.foreignKeys}">
                                None available
                            </c:if>
                        </div>
                    </fieldset>
                </div>
                <div class="tableForm">
                    <fieldset class="mde-form-fieldset">
                        <legend>Selection providers</legend>
                        <div style="margin-top: 1em;">
                            <c:if test="${not empty actionBean.table.selectionProviders}">
                                <table>
                                    <tr>
                                        <th>Name</th>
                                        <th>Columns</th>
                                        <th>To database</th>
                                        <th>Query</th>
                                    </tr>
                                    <c:forEach items="${actionBean.table.selectionProviders}" var="sp">
                                        <tr>
                                            <td colspan="${fn:length(sp.references)}"><c:out value="${sp.name}" /></td>
                                            <td><c:out value="${sp.references[0].actualFromColumn.columnName}" /></td>
                                            <td colspan="${fn:length(sp.references)}"><c:out value="${sp.toDatabase}" /></td>
                                            <td colspan="${fn:length(sp.references)}">
                                                <c:if test="${not empty sp.hql}">
                                                    <b>(HQL)</b><br />
                                                    <c:out value="${sp.hql}" /><br />
                                                </c:if>
                                                <c:if test="${not empty sp.sql}">
                                                    <b>(SQL)</b><br />
                                                    <c:out value="${sp.sql}" />
                                                </c:if>
                                            </td>
                                        </tr>
                                        <c:forEach items="${sp.references}" var="ref" varStatus="status">
                                            <c:if test="${not status.first}">
                                                <tr>
                                                    <td><c:out value="${ref.actualFromColumn.columnName}" /></td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </table>
                            </c:if>
                            <c:if test="${empty actionBean.table.selectionProviders}">
                                None available
                            </c:if>
                            <portofino:buttons list="table-selection-providers" cssClass="contentButton" />
                        </div>
                    </fieldset>
                </div>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="contentFooter">
        <portofino:buttons list="table-edit" cssClass="contentButton" />
    </stripes:layout-component>
</stripes:layout-render>