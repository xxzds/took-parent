<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>菜单管理</title>
	<jsp:include page="../common/comm.jsp"></jsp:include>
</head>
<body>
	<table id="menu"></table>
	
	<!-- add modify-->
	<div id="formDialog" class="easyui-dialog" style="visibility:hidden;" data-options="closed:true,width:350,height:350,buttons:'#dlg-buttons'">
		<form id="form" method="post">
			<table align="center" width="100%">
				<tr height="35px">
					<td width="30%" align="right">菜单名称：</td>
					<td>
						<input class="easyui-textbox" id="menuName" name="menuName" data-options="required:true">
					</td>
				</tr>
				<tr height="35px">
					<td width="25%" align="right">菜单标识：</td>
					<td>
						<input class="easyui-textbox" id="menuIdentify" name="menuIdentify" data-options="required:true">
					</td>
				</tr>
				<tr height="35px">
					<td width="25%" align="right">菜单url：</td>
					<td>
						<input class="easyui-textbox" id="menuUrl" name="menuUrl" data-options="required:true">
					</td>
				</tr>
				<tr height="35px">
					<td width="25%" align="right">图标名称：</td>
					<td>
						<input class="easyui-textbox" id="menuIcon" name="menuIcon" data-options="required:true">
					</td>
				</tr>
				<tr height="35px">
					<td width="25%" align="right">排序位置：</td>
					<td>
						<input class="easyui-textbox" id="menuSort" name="menuSort" data-options="required:true">
					</td>
				</tr>
				<tr height="35px">
					<td width="25%" align="right">是否可见：</td>
					<td>
						<input type="radio" name="menuVisible" value="1" checked="checked"><label>可见</label>
						<input type="radio" name="menuVisible" value="0"><label>不可见</label>
					</td>
				</tr>
				<tr height="35px">
					<td width="25%" align="right">备注：</td>
					<td>
						<input class="easyui-textbox" id="menuRemark" name="menuRemark" data-options="multiline:true">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:void(0)" id="save" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#formDialog').dialog('close')">取消</a>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/system/menu.js?version=1"></script>
</body>
</html>