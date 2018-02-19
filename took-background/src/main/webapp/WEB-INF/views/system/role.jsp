<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ds" uri="http://www.anjz.com/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>角色管理</title>
	<jsp:include page="../common/comm.jsp"></jsp:include>
</head>
<body>
	<div id="toolbar">
		<ds:hasPermission name="system:role:view">
			<label>角色名称：</label><input id="roleName" name="roleName" class="easyui-textbox">
			<a href="javascript:void(0)" id="search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:'true'">查询</a>
			<br>
		</ds:hasPermission>
		<ds:hasPermission name="system:role:add">
			<a href="javascript:void(0)" id="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'">新增</a>
		</ds:hasPermission>
		<ds:hasPermission name="system:role:modify">
			<a href="javascript:void(0)" id="modify" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'">修改</a>
		</ds:hasPermission>
    	<ds:hasPermission name="system:role:delete">
    		<a href="javascript:void(0)" id="del" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:'true'">删除</a>
    	</ds:hasPermission>
    	
	</div>
	<table id="dg"></table>
	
	<!-- add modify -->
	<div id="formDialog" class="easyui-dialog"  data-options="closed:true,width:350,height:180,modal:true,buttons:'#dlg-buttons'" style="visibility:hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id"/>
			<table align="center" width="100%">
				<tr height="35px">
					<td width="30%" align="right">角色名称：</td>
					<td>
						<input class="easyui-textbox" id="roleName" name="roleName" data-options="required:true">
					</td>
				</tr>
				<tr height="35px">
					<td align="right">角色描述：</td>
					<td>
						<input class="easyui-textbox" id="roleDescription" name="roleDescription" data-options="multiline:true">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" id="save" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#formDialog').dialog('close')">取消</a>
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/system/role.js?version=2"></script>
</body>
</html>