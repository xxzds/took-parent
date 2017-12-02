/*************************************************************************************
例：
    <input id="vv" class="easyui-validatebox" data-options="required:true,validType:'CHS'" />
    <input id="vv" class="easyui-validatebox" data-options="required:true,validType:['email','length[0,20]']" /> 多重验证

missingMessage：未填写时显示的信息
invalidMessage：无效的数据类型时显示的信息
class="easyui-validatebox" 文本验证
class="easyui-numberbox" 数字验证
required:true 必填

以实现规则如下：
email：匹配 email 正则表达式规则
url：匹配 URL 正则表达式规则
length[0,100]：允许从0到100个字符
remote['http://.../action.do','paramName']：发送 ajax 请求来验证值，成功时返回 'true' 。

**************************************************************************************/
$.extend($.fn.validatebox.defaults.rules, {
	//用户名校验，只能是字母、数字、下划线
	username:{
		validator:function(value){
			return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
		},
		message: '用户名不合法（字母开头，允许6-16位，允许字母数字下划线）'
	},
	//手机号码校验
	phone:{
		validator:function(value){
			return /^0?1[3|4|5|7|8][0-9]\d{8}$/.test(value);
		},
		message: '手机号码格式不正确'
	},
	
	//判断两个值是否相等
	equals: {    
        validator: function(value,param){    
            return value == $(param[0]).val();    
        },    
        message: '新密码和确认密码不相等'   
    }    

	
});