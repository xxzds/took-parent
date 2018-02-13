<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ds" uri="http://www.anjz.com/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品分类管理</title>
	<jsp:include page="../common/comm.jsp"></jsp:include>
</head>
<body>
	<div id="toolbar">
		<ds:hasPermission name="item:cate:view">
			<label>分类名称：</label><input id="searchItemCateName" name="searchItemCateName" class="easyui-textbox">
			<a href="javascript:void(0)" id="search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:'true'">查询</a>
			<br>
		</ds:hasPermission>
		<ds:hasPermission name="item:cate:add">
			<a href="javascript:void(0)" id="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'">新增</a>
		</ds:hasPermission>
		<ds:hasPermission name="item:cate:modify">
			<a href="javascript:void(0)" id="modify" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'">修改</a>
		</ds:hasPermission>
    	<ds:hasPermission name="item:cate:delete">
    		<a href="javascript:void(0)" id="del" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:'true'">删除</a>
    	</ds:hasPermission>    	
	</div>
	<table id="dg"></table>
	
	<!-- add modify -->
	<div id="formDialog" class="easyui-dialog"  data-options="closed:true,width:350,height:210,buttons:'#dlg-buttons'" style="visibility:hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id"/>
			<table align="center" width="100%">
				<tr height="35px">
					<td width="35%" align="right">分类名称：</td>
					<td>
						<input class="easyui-textbox" id="itemCateName" name="itemCateName" data-options="required:true">
					</td>
				</tr>
				<tr height="35px">
					<td align="right">排列位置：</td>
					<td>
						<input class="easyui-numberbox" id="itemCateSort" name="itemCateSort" data-options="required:true,min:1">
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
	
	<script type="text/javascript" src="${ctx}/static/js/took/item_cate.js?v=1"></script>
</body>
</html>