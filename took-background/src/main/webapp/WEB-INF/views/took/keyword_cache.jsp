<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ds" uri="http://www.anjz.com/tags"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>关键字缓存</title>
	<jsp:include page="../common/comm.jsp"></jsp:include>
</head>
<body>
	<div id="toolbar">	
		<ds:hasPermission name="item:keywordcache:view">
			<label>接口类型：</label>			
			<select id="type" class="easyui-combobox" name="type" style="width:120px;">
			    <c:forEach items="${apiTypes}" var="apiType">
			    	<option value="${apiType.type}">${apiType.name}</option>
			    </c:forEach>
			</select>			
			<a href="javascript:void(0)" id="search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:'true'">查询</a>
			<br>
		</ds:hasPermission>
	</div>
	<table id="dg"></table>
	<script type="text/javascript" src="${ctx}/static/js/took/keyword_cache.js"></script>
</body>
</html>