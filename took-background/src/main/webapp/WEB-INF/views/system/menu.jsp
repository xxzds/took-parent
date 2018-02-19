<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ds" uri="http://www.anjz.com/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>菜单管理</title>
	<jsp:include page="../common/comm.jsp"></jsp:include>
	<style type="text/css">
	  .icon-span{
	  	display:inline-block;width:16px;height:16px;padding:5px;
	  }
	</style>
</head>
<body>
	<div id="toolbar">
		<ds:hasPermission name="system:menu:add">
			<a href="javascript:void(0)" id="add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'">增加目录</a>
		</ds:hasPermission>
	</div>
	<table id="menu"></table>
	
	<!-- add modify-->
	<div id="formDialog" class="easyui-dialog" style="visibility:hidden;" data-options="closed:true,width:350,height:350,modal:true,buttons:'#dlg-buttons'">
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
						<input id="menuIcon" name="menuIcon" data-options="required:true">
						<span class="textbox" id="selectedIcon" style="border-width:0;"></span>						
					</td>
				</tr>
				<tr height="35px">
					<td width="25%" align="right">排序位置：</td>
					<td>
						<input class="easyui-numberbox" id="menuSort" name="menuSort" data-options="required:true,min:1">
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
	
	<!-- 图标选择面板 -->
	<div id="iconSelectPanel">
		<div id="iconList"></div>
		<div id="pp" class="datagrid-pager pagination"></div>
	</div>
	<!-- 模板 -->
	<script id="iconTemplate" type="text/x-jsrender">
		<span class="icon-span {{:iconIdentity}}" data-icon='{{:iconIdentity}}' title='{{:remark}}'></span>		
	</script>
	
	<script type="text/javascript" src="${ctx}/static/plugins/jsrender.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/system/menu.js?version=5"></script>
	<script type="text/javascript">
	//操作格式化,现限制到二级菜单(增加权限控制)
	menuModule.formatterOper = function(id,row){
		var content ='';
		 if (row.parentId == menuModule.root) {
			 content+='<div style="margin-left:auto;margin-right:auto;text-align:center;background-color:red;width:90px;">';
			 <ds:hasPermission name="system:menu:add">
			 	content += '<div style="float:left;cursor:pointer;" onclick="menuModule.add('
					+ id
					+ ')">增加</div>';
			 </ds:hasPermission>
					
			 <ds:hasPermission name="system:menu:modify">
				 content += '<div  style="margin-left:5px;float:left;cursor:pointer;" onclick="menuModule.modify('
					+ id
					+ ')">修改</div>';
			 </ds:hasPermission>
			 
			 <ds:hasPermission name="system:menu:delete">
			 	content += '<div style="margin-left:5px;float:left;cursor:pointer;" onclick="menuModule.del('
						+ id
						+ ','
						+ row.parentId
						+ ',\''
						+ row.menuName
						+ '\')" >删除</div></div>';
			 </ds:hasPermission>		
			content +='</div>';
			} else {
				content+='<div style="margin-left:auto;margin-right:auto;text-align:center;background-color:red;width:58px;">';
				<ds:hasPermission name="system:menu:modify">
					 content += '<div style="float:left;cursor:pointer;" onclick="menuModule.modify('
	 						+ id
	 						+ ')" >修改</div>';
				 </ds:hasPermission>
				 
				 <ds:hasPermission name="system:menu:delete">
				 	content += '<div style="float:right;cursor:pointer;" onclick="menuModule.del('
						+ id
						+ ','
						+ row.parentId
						+ ',\''
						+ row.menuName
						+ '\')">删除</div>' + '</div>';
				 </ds:hasPermission>
				 content +='</div>';
			}
		 return content;
	 };	
	</script>
</body>
</html>