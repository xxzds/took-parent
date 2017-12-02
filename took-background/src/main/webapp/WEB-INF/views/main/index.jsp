<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>主页</title>
	<jsp:include page="../common/comm.jsp"></jsp:include> 
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/main.css">
	<script type="text/javascript" src="${ctx}/static/js/main.js"></script>
</head>
<body class="easyui-layout"> 
    <div data-options="region:'north'" class="north">
    	<div class="right">欢迎您：${user.userName}
    	<a href="javascript:;" id="modifyPwd" class="btnok">修改密码</a>
    	<a href="javascript:;" id="logout" class="btnok">退出</a></div>
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
  
  <div id="formDialog" style="visibility:hidden;">
  	<form id="form" method="post">
  		<input type="hidden" name="token" value="${token}">
  		<table align="center" width="100%">
				<tr height="35px">
					<td width="32%" align="right">原密码：</td>
					<td>
						<input class="easyui-textbox" id="oldPwd" name="oldPwd" data-options="type:'password',required:true">
					</td>
				</tr>
				<tr height="35px">
					<td width="32%" align="right">新密码：</td>
					<td>
						<input class="easyui-textbox" id="newPwd" name="newPwd" data-options="type:'password',required:true">
					</td>
				</tr>
				<tr height="35px">
					<td width="32%" align="right">确认密码：</td>
					<td>
						<input class="easyui-textbox" id="confirmPwd" name="confirmPwd" data-options="type:'password',required:true,validType:'equals[\'#newPwd\']'">
					</td>
				</tr>
			</table>
  	</form>
  </div> 
</body>  

</html>