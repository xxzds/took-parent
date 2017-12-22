<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>授权管理</title>
	<jsp:include page="../common/comm.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
			
		<div data-options="region:'west',title:'角色',split:true" style="width:200px;">
			<table id="role"></table>
		</div> 
		
		<div data-options="region:'center'" style="width:100px;">
			<div class="easyui-panel"  title="菜单" data-options="iconCls:'icon-lock',tools:'#menu_panel_tools',fit:true">
				<input type="hidden" id="roleId" name="roleId"/>
				<ul id="menu_tree"></ul>
			</div>
		</div> 
		 
		
		<div data-options="region:'east',split:true" style="width:500px;">
			<div class="easyui-panel"  title="权限" data-options="iconCls:'icon-lock',tools:'#permission_panel_tools',fit:true">
				<input type="hidden" id="roleMenuId" name="roleMenuId"/>
				<div id="permission_list"></div>
			</div>
			
		</div>  
		
		<div id="menu_panel_tools">
			<a href="javascript:;"  id="menusave" class="icon-save" title="保存"></a>
		</div>
		
		<div id="permission_panel_tools">
			<a href="javascript:;" id="permissionsave" class="icon-save" title="保存"></a>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/system/auth.js?version=9"></script>
</body>
</html>