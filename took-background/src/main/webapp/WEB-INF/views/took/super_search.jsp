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
			<label>关键字：</label><input id="q" name="q" class="easyui-textbox" style="width:200px;">
			<label>排序方式：</label>
			<select id="queryType" class="easyui-combobox" name="queryType" style="width:200px;">
			    <option value="0">默认排序</option>
			    <option value="2">人气</option>
			    <option value="3">价格从高到低</option>
			    <option value="4">价格从低到高</option>
			    <option value="9">销量从高到低</option>
			    <option value="1">收入比率从高到低</option>
			    <option value="5">月推广量从高到低</option>
			    <option value="7">月支出佣金从高到低</option>
			</select>
			<br>			
			<input type="checkbox" id="freeShipment" name="freeShipment" value="1">包邮
			<input type="checkbox" id="yxjh" name="yxjh" value="1">营销和定向计划
			<input type="checkbox" id="dpyhq" name="dpyhq" value="1">优惠券
			<input type="checkbox" id="hPayRate30" name="hPayRate30" value="1">月成交转化率高于行业均值
			<input type="checkbox" id="b2c" name="b2c" value="1">天猫旗舰店
			<a href="javascript:void(0)" id="search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:'true'">查询</a>
			<br>
		</ds:hasPermission> 
		<ds:hasPermission name="item:superSearch:collect">
			<a href="javascript:void(0)" id="collect" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'">商品采集</a>
		</ds:hasPermission> 	
	</div>
	<table id="dg"></table>
	
	<!-- collect -->
	<div id="formDialog" class="easyui-dialog"  data-options="closed:true,width:350,height:120,modal:true,buttons:'#dlg-buttons'" style="visibility:hidden;">
		<form id="form">
			<table align="center" width="100%">
				<tr height="35px">
					<td width="35%" align="right">商品分类：</td>
					<td>
						<input id="itemCateId" name="itemCateId" data-options="required:true">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" id="save" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">采集</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#formDialog').dialog('close')">取消</a>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/took/super_search.js?v=5"></script>
	<script type="text/javascript" src="${ctx}/static/js/combobox.js?version=1"></script>
</body>
</html>