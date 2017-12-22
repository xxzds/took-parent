<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table>
	<c:forEach items="${permissions}" var="permission" varStatus="status">
		<c:if test="${(status.count-1)%2==0}"><tr></c:if>		
		<td><input  name="permissionId" type="checkbox" value="${permission.id}" <c:if test="${permission.selected}">checked="checked"</c:if>/>
		    ${permission.permissionName}(${permission.permissionIdentify })
		</td>
		<c:if test="${(status.count-1)%2!=0}"></tr></c:if>
	</c:forEach>
</table>