<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ds" uri="http://www.anjz.com/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>超级搜索</title>
	<jsp:include page="../common/comm.jsp"></jsp:include>
</head>
<body>
	<div id="toolbar">
		<ds:hasPermission name="item:superSearch:view">
			<label>关键字：</label><input id="q" name="q" class="easyui-textbox">
			<input type="checkbox" id="dpyhq" name="dpyhq" value="1">是否包含优惠券
			<a href="javascript:void(0)" id="search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:'true'">查询</a>
			<br>
		</ds:hasPermission>  	
	</div>
	<table id="dg"></table>
	<script type="text/javascript" src="${ctx}/static/js/took/super_search.js?v=4"></script>
</body>
</html>