<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>主页</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/main.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/plugins/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/plugins/easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/plugins/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/plugins/easyui/jquery.easyui.min.js"></script>
	
	<script type="text/javascript" src="${ctx}/static/js/main.js"></script>
	<script type="text/javascript">
		var ctx='${ctx}';
	</script>
</head>
<body class="easyui-layout"> 
    <div data-options="region:'north'" class="north">
    	<div class="right">欢迎您：admin
    	<a class="btnok">修改密码</a>
    	<a class="btnok">退出</a></div>
    </div> 	
    <div data-options="region:'west',title:'导航菜单'" class="west">
    	<ul id="tree"></ul>
    </div>   
    <div data-options="region:'center'">
    	<div id="tabs" class="easyui-tabs" data-options="fit:'true',border:'false'"></div>
    </div>
    
    <div id="tabsMenu" class="easyui-menu" style="width:120px;">  
	    <div name="close">关闭</div>  
	    <div name="other">关闭其他</div>  
	    <div name="all">关闭所有</div>
  </div>  
</body>  

</html>