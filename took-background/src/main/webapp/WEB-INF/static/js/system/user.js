var userModule = {
	// 初始化表格
	init:function(){
		$('#dg').datagrid({
			url:ctx+'/system/user/userList',
			fit:true,
	        singleSelect: true,  
	        rownumbers:true,
	        toolbar:"#toolbar",
			columns:[[
			          {field:'id',hidden:true},
			          {field:'userName',title:'用户名',align:'center',width:100},
			          {field:'userPhone',title:'手机号码',align:'center',width:100},
			          {field:'userEmail',title:'电子邮箱',align:'center',width:200},
			          {field:'userCreateTimeStr',title:'创建时间',align:'center',width:200},
			          {field:'userStatusStr',title:'用户状态',align:'center',width:100},
			          {field:'role',title:'用户角色',align:'center',width:100,formatter:function(value,row){
			        	   var formatDate;
			        	   $.ajax({  
					            type : "POST",  
					            url : ctx+"/system/userRole/queryUserRoles",
					            dataType: "json",
					            async: false,
					            data:{
					            	userId:row.id
					            },
					            success : function(result) {
					            	console.log(result);
					                if (result.success) {
					                	var data = result.data;
					                	if(data && data.length>0){
					                		formatDate = data[0].sysRole.roleName;
					                	}else{
					                		formatDate = '<span style="color:red">无角色</span>';
					                	}
					                }
					            },
					            error:function(){
					            	messager.alert("网络异常"); 
					            }
					        }); 
			        	  return formatDate;
			          }},
			          {field:'action',title:'操作',width:100,align:'center',formatter:function(value,row){
			        	  return '<a href="javascript:void(0);" onclick="userModule.setDefaultPwd(\''+row.id+'\');">恢复默认密码</a>';
			          }}
			        ]],
	        onLoadError : function(data) {
	  			this.datagrid('loadData', {
	  				total : 0,
	  				rows : []
	  			});
	  		},
	  		onBeforeLoad:function(param){
	  			//设置查询条件
	  			param.userName=$('#userName').val();
	  		},
	        loadFilter:function(data){
	        	return {
	        		total:data.totalCount,
	        		rows:data.data
	        	}
	        },
	        pageSize: 20,         
	        pageList: [20,40,60,80],
	        pagination : true
		});
	},
	search:function(){
		$('#dg').datagrid('reload');
	},
	setDefaultPwd:function(id){
		$.messager.confirm('确认','您确认恢复默认密码吗？',function(r){    
		    if (r){
		    	$.ajax({  
		            type : "POST",  
		            url : ctx+"/system/user/defaultUserPwd/"+id,
		            dataType: "json",
		            success : function(result) {
		                if (result.success) {  
		                	messager.show("重置密码成功");		                	
		                } else {  
		                	messager.alert(result.message); 
		                }  
		            },
		            error:function(){
		            	messager.alert("网络异常"); 
		            }
		        });  
		    }    
		});  

	},
	add:function(){
		$('#userName').textbox('readonly',false);
		//打开校验
	    $('#userName').textbox({novalidate:false});
		userModule.clearForm();
		$('#formDialog').dialog({
			title: '添加',    
		    width: 400,    
		    height: 280,    
		    iconCls:'icon-add',
		    cache: false,   
		    modal: true,
		    buttons:[{
		    	text:'保存',
		    	iconCls:'icon-save',
		    	plain:true,
		    	handler:function(){
		    		$('#form').form('submit', {    
		    		    url:ctx+'/system/user/addUser', 
		    		    onSubmit: function(param){    
		    		    	return $(this).form('validate');
		    		    },    
		    		    success:function(dataStr){
		    		    	var data = JSON.parse(dataStr);
		    		    	if(data.success){
		    		    		$('#formDialog').dialog('close');
		    		    		//清空数据
		    		    		userModule.clearForm();
		    		    		//重新加载列表
		    		    		$('#dg').datagrid('reload');
		    		    	}else{
		    		    		$.messager.alert('提示',data.message); 
		    		    	}
		    		    }    
		    		}); 
		    	}
		    },{
		    	text:'关闭',
		    	iconCls:'icon-no',
		    	plain:true,
		    	handler:function(){
		    		$('#formDialog').dialog('close');
		    	}
		    }]
		});
	},
	modify:function(){
		var row = $('#dg').datagrid('getSelected');
		if(!row){
			messager.show("请选择一条记录!");
			return;
		}
		
		//用户名不可修改
		$('#userName').textbox('readonly',true);
		
		//关闭校验
	    $('#userName').textbox({novalidate:true});
		
		//载入数据到表单
		$('#form').form('load',row);
			
		$('#formDialog').dialog({
			title: '修改',    
		    width: 400,    
		    height: 280,    
		    iconCls:'icon-edit',
		    cache: false,   
		    modal: true,
		    buttons:[{
		    	text:'保存',
		    	iconCls:'icon-save',
		    	plain:true,
		    	handler:function(){
		    		$('#form').form('submit', {    
		    		    url:ctx+'/system/user/editUser', 
		    		    onSubmit: function(param){    
		    		    	return $(this).form('validate');
		    		    },    
		    		    success:function(dataStr){
		    		    	var data = JSON.parse(dataStr);
		    		    	if(data.success){
		    		    		$('#formDialog').dialog('close');
		    		    		//清空数据
		    		    		userModule.clearForm();
		    		    		//重新加载列表
		    		    		$('#dg').datagrid('reload');
		    		    	}else{
		    		    		$.messager.alert('提示',data.message); 
		    		    	}
		    		    }    
		    		}); 
		    	}
		    },{
		    	text:'关闭',
		    	iconCls:'icon-no',
		    	plain:true,
		    	handler:function(){
		    		$('#formDialog').dialog('close');
		    	}
		    }]
		});
	},
	del:function(){
		var row = $('#dg').datagrid('getSelected');
		if(!row){
			messager.show("请选择一条记录!");
			return;
		}
		
		if(row.userName=='admin'){
			messager.show("超级管理员用户不可删除");
			return;
		}
		$.messager.confirm('确认','您确认删除此用户吗？',function(r){    
		    if (r){
		    	$.ajax({  
		            type : "POST",  
		            url : ctx+"/system/user/logicDelUser/"+row.id,
		            dataType: "json",
		            success : function(result) {
		                if (result.success) {  
		                	messager.show("删除用户成功");
		                	//重新加载列表
	    		    		$('#dg').datagrid('reload');		                	
		                } else {  
		                	messager.alert(result.message); 
		                }  
		            },
		            error:function(){
		            	messager.alert("网络异常"); 
		            }
		        });  
		    }    
		}); 		
	},
	clearForm:function(){
		$('#form').form('clear');
		$('input[name="userStatus"]').removeAttr("checked").eq(0).prop('checked',"checked");
	}
}

$(function(){	
	userModule.init();
	$('#search').click(userModule.search);
	$('#add').click(userModule.add);
	$('#modify').click(userModule.modify);
	$('#del').click(userModule.del);
	
	//解决页面加载时，出现未渲染的对话框内容
	$('#formDialog').css('visibility','visible');	
})