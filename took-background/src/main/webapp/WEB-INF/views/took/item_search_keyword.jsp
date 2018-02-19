<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ds" uri="http://www.anjz.com/tags"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品搜索关键字管理</title>
	<jsp:include page="../common/comm.jsp"></jsp:include>
</head>
<body>
	<div id="toolbar">
		<ds:hasPermission name="item:search:view">
			<label>商品分类名称：</label><input id="searchItemCate" name="searchItemCate" style="width:120px;">
			<label>搜索关键字：</label><input id="keyword" name="keyword" class="easyui-textbox">
			<label>是否可用：</label>			
			<select id="searchIsAvailable" class="easyui-combobox" name="searchIsAvailable" style="width:120px;">
			    <option value="">全部</option>
			    <c:forEach items="${isAvailables}" var="isAvailable">
			    	<option value="${isAvailable.code}">${isAvailable.name}</option>
			    </c:forEach>
			</select>			
			<a href="javascript:void(0)" id="search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:'true'">查询</a>
			<br>
		</ds:hasPermission>
		<ds:hasPermission name="item:search:add">
			<a href="javascript:void(0)" id="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'">新增</a>
		</ds:hasPermission>
		<ds:hasPermission name="item:search:modify">
			<a href="javascript:void(0)" id="modify" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'">修改</a>
		</ds:hasPermission>
    	<ds:hasPermission name="item:search:delete">
    		<a href="javascript:void(0)" id="del" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:'true'">删除</a>
    	</ds:hasPermission>
    	<ds:hasPermission name="item:search:cacheSyn">
    		<a href="javascript:void(0)" id="cacheSyn" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:'true'">缓存同步</a>
    	</ds:hasPermission>    	   	
	</div>
	<table id="dg"></table>
	
	<!-- add modify -->
	<div id="formDialog" class="easyui-dialog"  data-options="closed:true,width:350,height:230,modal:true,buttons:'#dlg-buttons'" style="visibility:hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id"/>
			<table align="center" width="100%">
				<tr height="35px">
					<td width="35%" align="right">商品分类：</td>
					<td>
						<input id="itemCateId" name="itemCateId" data-options="required:true">
					</td>
				</tr>
				<tr height="35px">
					<td align="right">搜索关键字：</td>
					<td>
						<input class="easyui-textbox" id="searchKeyword" name="searchKeyword" data-options="required:true">
					</td>
				</tr>
				<tr height="35px">
					<td align="right">最大页：</td>
					<td>
						<input class="easyui-numberbox" id="maxPage" name="maxPage" data-options="required:true,min:1">
					</td>
				</tr>
				<tr height="35px">
					<td align="right">是否可用：</td>
					<td>
						<input type="radio" name="isAvailable" value="1" checked="checked"><label>可用</label>
						<input type="radio" name="isAvailable" value="2"><label>不可用</label>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" id="save" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#formDialog').dialog('close')">取消</a>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/took/item_search_keyword.js?v=4"></script>
    <script type="text/javascript" src="${ctx}/static/js/combobox.js?version=3"></script>
</body>
</html>