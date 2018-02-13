<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
	    <title>系统登录</title>    
	    <link href="${ctx}/static/css/login.css" rel="stylesheet" />
	    <link href="${ctx}/static/css/main.css" rel="stylesheet" />
	</head>
<body>
	<!--上部背景-->
    <div id="Login">
        <div class="bg2"></div>
        <div class="bg3"></div>
        <!--上部背景 end-->
        <!--登录-->
        <div class="bg4">
            <form id="form">
            <ul class="login clearfloat">
                <li class="fl login-logo">
                    <img src="${ctx}/static/images/login/logo.jpeg"  width="220px" height="120px"/></li>
                <li class="fl line">
                    <img src="${ctx}/static/images/login/line.png" /></li>
                <li class="fl denglu ml25">
                    <dl class="clearfloat">
                        <dt class="dl-title">优惠券后台管理系统</dt>
                        <dt>用户名：<input class="text-inputs" type="text" value="" name="userName" id="userName"></dt>
                        <dt>密　码：<input class="text-inputs" type="password" name="password" id="password" value=""></dt>
                        <dt class="clearfloat">
                            <span class="fl">验证码：</span>
                            <span class="fl"><input type="text" maxlength="5" id="code" class="vcode text-inputs" style="width:94px" name="code"></span>
                            <span class="fl ml10"><img id="codeImage" src="${ctx}/getCodeImage"/></span>                          
                      	</dt>
                      	<dt class="clearfloat">
                      		<input type="checkbox" id="ifRemember" name="ifRemember"/> 记住我
                      	</dt>                    	                  
                       <dt>
                       		<span class="error" id="tip"></span>
                       </dt>
                    </dl>
                </li>
                <li class="fl ml30">
                    <a class="login-btn" href="javascript:;" title="这里是登录按钮" id="login"></a>
                </li>
            </ul>
            </form>
            
        </div>
        
        <!--登录 end-->
        <!--下部背景-->
        <div class="bg5">版权所有&nbsp;Copyright&nbsp;©&nbsp;2010-2018&nbsp;All&nbsp;Rights&nbsp;Reserved&nbsp;促优网</div>
        <div class="bg6"></div>
        <!--下部背景 end-->
    </div>
    <script type="text/javascript" src="${ctx}/static/plugins/md5.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/plugins/jquery.min.js"></script>
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