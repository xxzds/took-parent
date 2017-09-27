<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/static/plugins/jquery.min.js"></script>
<title>登录</title>
</head>
<body>

<form id="form">
用户名：<input type="text" id="userName"/><br>
密码：<input type="password" id="password"/><br>
<div style="color:red;"><span id="tip"></span></div>
<input type="button" id="login" value="登录"/>

</form>

<script type="text/javascript">
	//登录
	$('#login').click(function(){
		var userName=$('#userName').val();
		var password=$('#password').val();
		
		if(userName==null || userName==''){
			$('#tip').html('用户名不能为空');
			return;
		}
		if(password==null || password==''){
			$('#tip').html('密码不能为空');
			return;
		}
		$('#tip').html('');

		$.ajax({  
            type : "POST",  
            url : "${ctx}/login",
            data : $('#form').serialize(),
            dataType: "json",
            success : function(result) {
               if(result.success){
            	   window.location.href='${ctx}/main';
               }else{
            	   $('#tip').html(result.message);
               }
            },
            error:function(){
            	alert("网络异常"); 
            }
        });  
    });
</script>
</body>
</html>