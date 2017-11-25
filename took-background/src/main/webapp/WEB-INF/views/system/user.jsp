<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户管理</title>
	<jsp:include page="../common/comm.jsp"></jsp:include>
</head>
<body>
	<div id="toolbar">
		<label>用户名：</label><input id="userName" name="userName" class="easyui-textbox">
		<a href="javascript:void(0)" id="search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:'true'">查询</a>
		<br>		
		<a href="javascript:void(0)" id="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'">新增</a>
    	<a href="javascript:void(0)" id="modify" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'">修改</a>
    	<a href="javascript:void(0)" id="del" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:'true'">删除</a>
	</div>
	<table id="dg"></table>
	
	<!-- add modify -->
	<div id="formDialog">
		<form id="form" method="post">
			<input type="hidden" name="id"/>
			<table align="center" width="100%">
				<tr height="35px">
					<td width="35%" align="right">用户名：</td>
					<td>
						<input class="easyui-textbox" id="userNameStr" name="userName" data-options="required:true">
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
					<td width="35%" align="right">用户状态：</td>
					<td>
						<input type="radio" name="userStatus" value="normal" checked="checked"><label>正常</label>
						<input type="radio" name="userStatus" value="blocked"><label>封禁</label>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/system/user.js?version=1"></script>
</body>
</html>