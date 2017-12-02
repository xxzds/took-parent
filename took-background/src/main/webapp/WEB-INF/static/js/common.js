//提示
var messager={
		show:function(title){
			$.messager.show({
				title : '提示',
				msg : title,
				timeout : 2000,
				showType : 'slide'
			});
		},
		alert:function(title){
			$.messager.alert("错误",title);
		}
}

//全局设置ajax，处理请求时的session超时
$.ajaxSetup({
    contentType: 'application/x-www-form-urlencoded;charset=utf-8',
    complete: function (XMLHttpRequest, textStatus) {
        var sessionstatus = XMLHttpRequest.getResponseHeader('sessionstatus'); //通过XMLHttpRequest取得响应头sessionstatus
        if (sessionstatus == 'timeout') {
        	$.messager.alert("提示","你已超时,请重新登录","info",function(){
        		 top.window.location.href = XMLHttpRequest.getResponseHeader('login');
        	});           
          }
    }
});