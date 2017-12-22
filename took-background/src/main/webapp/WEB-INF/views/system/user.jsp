<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="ds" uri="http://www.anjz.com/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户管理</title>
	<jsp:include page="../common/comm.jsp"></jsp:include>
</head>
<body>
	<div id="toolbar">
		<ds:hasPermission name="system:user:view">
			<label>用户名：</label><input id="userName-search" name="userName-search" class="easyui-textbox">
			<a href="javascript:void(0)" id="search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:'true'">查询</a>
			<br>
		</ds:hasPermission>	
	    <ds:hasPermission name="system:user:add">
	    	<a href="javascript:void(0)" id="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'">新增</a>
	    </ds:hasPermission>	
		<ds:hasPermission name="system:user:modify">
			<a href="javascript:void(0)" id="modify" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'">修改</a>
		</ds:hasPermission>
    	<ds:hasPermission name="system:user:delete">
    		<a href="javascript:void(0)" id="del" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:'true'">删除</a>
    	</ds:hasPermission>    	
	</div>
	<table id="dg"></table>
	
	<!-- add modify -->
	<div id="formDialog" style="visibility:hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id"/>
			<table align="center" width="100%">
				<tr height="35px">
					<td width="35%" align="right">用户名：</td>
					<td>
						<input class="easyui-textbox" id="userName" name="userName" data-options="required:true,validType:'remote[\'${ctx}/system/user/userNameIfNotRepeate\',\'userName\']',invalidMessage:'用户名不可重复'">
					</td>
				</tr>
				<tr height="35px">
					<td width="35%" align="right">手机号码：</td>
					<td>
						<input class="easyui-textbox" id="userPhone" name="userPhone" data-options="validType:'phone'">
					</td>
				</tr>
				<tr height="35px">
					<td width="35%" align="right">电子邮箱：</td>
					<td>
						<input class="easyui-textbox" id="userEmail" name="userEmail" data-options="validType:'email'">
					</td>
				</tr>
				<tr height="35px">
					<td width="35%" align="right">用户角色：</td>
					<td>
						<input id="role" name="role" data-options="required:true">
					</td>
				</tr>
				<tr height="35px">
					<td width="35%" align="right">用户状态：</td>
					<td>
						<input type="radio" name="userStatus" value="normal" checked="checked"><label>正常</label>
						<input type="radio" name="userStatus" value="blocked"><label>封禁</label>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/system/user.js?version=4"></script>
	<script type="text/javascript" src="${ctx}/static/js/combobox.js?version=1"></script>
</body>
</html>