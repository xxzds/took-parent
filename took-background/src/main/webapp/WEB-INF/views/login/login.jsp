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
用户名：<input type="text" id="userName" name="userName"/><br>
密码：<input type="password" id="password" name="password"/><br>
验证码：<input type="text" id="code" name="code"/><img id="codeImage" alt="验证码看不清，换一张" src="${ctx}/getCodeImage"><br/>
<input type="checkbox" id="ifRemember" name="ifRemember"/>记住我
<div style="color:red;"><span id="tip"></span></div>
<input type="button" id="login" value="登录"/>

</form>
<script type="text/javascript" src="${ctx}/static/plugins/md5.min.js"></script>
<script type="text/javascript">

	/*针对于session为空时 判断是不是有iframe*/
	if (self.frameElement != null && (self.frameElement.tagName == "IFRAME" || self.frameElement.tagName == "iframe")) {
	    top.window.location.href = window.location.href;
	}
	
	//验证码切换
	$('#codeImage').click(function(){
		$(this).get(0).src = "${ctx}/getCodeImage?v="+new Date().getTime();
	});
	
	//登录
	$('#login').click(function(){
		var userName=$('#userName').val();
		var password=$('#password').val();
		var ifRemember = $('#ifRemember')[0].checked ? 'on':'off';
		var code = $('#code').val();
		
		if(userName==null || userName==''){
			$('#tip').html('用户名不能为空');
			return;
		}
		if(password==null || password==''){
			$('#tip').html('密码不能为空');
			return;
		}
		if(code==null || code==''){
			$('#tip').html('验证码不能为空');
			return;
		}
		$('#tip').html('');

		$.ajax({  
            type : "POST",  
            url : "${ctx}/login",
            data :{
            	userName:userName,
            	password:md5(password),
            	ifRemember:ifRemember,
            	code:code
            },
            dataType: "json",
            success : function(result) {
               if(result.success){
            	   window.location.href='${ctx}/main';
               }else{            	  
            	   $('#tip').html(result.message);
            	   //切换验证码
            	   $('#codeImage').click();
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