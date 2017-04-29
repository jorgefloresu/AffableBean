<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    </head>
    <body>
        <sql:query var="result" dataSource="jdbc/affablebean">SELECT * FROM customer_order</sql:query>
        <table border="1">
            <!-- column headers -->
            <tr>
                <c:forEach var="columnName" items="${result.columnNames}">
                    <th>
                        <c:out value="${columnName}"/>
                    </th>
                </c:forEach>
            </tr>
             
            <!-- column data -->
             
            <c:forEach var="row" items="${result.rowsByIndex}">
                <tr>
                    <c:forEach var="column" items="${row}">
                        <td>
                            <c:out value="${column}"/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>