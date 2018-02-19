var cookieModule = {
		// 初始化表格
		init : function(){
			$('#dg').datagrid({
				url:ctx+'/took/cookie/getCookies',
				fit:true,
		        singleSelect: true,  
		        rownumbers:true,
		        toolbar:"#toolbar",
		        columns:[[
							{field:'id',title:'主键',align:'center',width:50},
							{field:'name',title:'名称',align:'center',width:200},
							{field:'alimamaCookie',title:'cookie',align:'center',width:600,formatter:function(value){
								 return "<span title='" + value + "'>" + value + "</span>";
							}},
							{field:'isAvailable',title:'是否可用',align:'center',width:200,formatter:function(value,row){
								return value==1 ? '可用' : '不可用';
							}}
		                ]],
	            onLoadError : function(data) {
		  			this.datagrid('loadData', {
		  				total : 0,
		  				rows : []
		  			})
	            },
	            onBeforeLoad:function(param){
	            	param.name = $('#searchName').val();
	            	//是否可用
	            	param.isAvailable = $('#searchIsAvailable').combobox('getValue');
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
		search : function(){
			$('#dg').datagrid('reload');
		},
		add : function() {
			cookieModule.clearForm();
			$('#formDialog').dialog({title: '新增', iconCls:'icon-add'});
			$('#formDialog').dialog('open');
			
			//保存按钮注册click事件
			$('#save').off('click').on('click',function(){
				$('#form').form('submit', {    
	    		    url:ctx+'/took/cookie/addAlimamaCookie', 
	    		    onSubmit: function(param){    
	    		    	return  $(this).form('validate');  		    	
	    		    },    
	    		    success:function(dataStr){
	    		    	var data = JSON.parse(dataStr);
	    		    	if(data.success){   		    		
	    		    		$('#formDialog').dialog('close');
	    		    		//重新加载列表
	    		    		$('#dg').datagrid('reload');	
	    		    		messager.show("添加成功");
	    		    	}else{
	    		    		$.messager.alert('提示',data.message); 
	    		    	}
	    		    }    
	    		});
			});
		},
		modify : function() {
			var row = $('#dg').datagrid('getSelected');
			if(!row){
				messager.show("请选择一条记录!");
				return;
			}
			
			cookieModule.clearForm();
			 $('#formDialog').dialog({title: '修改',iconCls:'icon-edit'});
			 //载入数据到表单
			 $('#form').form('load',row);
			 $('#formDialog').dialog('open');
			 
			//保存按钮注册click事件
			$('#save').off('click').on('click',function(){
				$('#form').form('submit', {    
	    		    url:ctx+'/took/cookie/modifyAlimamaCookie', 
	    		    onSubmit: function(param){    
	    		    	return  $(this).form('validate');  		    	
	    		    },    
	    		    success:function(dataStr){
	    		    	var data = JSON.parse(dataStr);
	    		    	if(data.success){   		    		
	    		    		$('#formDialog').dialog('close');
	    		    		//重新加载列表
	    		    		$('#dg').datagrid('reload');	
	    		    		messager.show("修改成功");
	    		    	}else{
	    		    		$.messager.alert('提示',data.message); 
	    		    	}
	    		    }    
	    		});
			});

		},
		del : function() {
			var row = $('#dg').datagrid('getSelected');
			if(!row){
				messager.show("请选择一条记录!");
				return;
			}
			
			$.messager.confirm("提示", "您确定删除此阿里妈妈的cookie吗？",
					function(r) {
				 		if(r){
				 			$.ajax({  
					            type : "POST",  
					            url : ctx+"/took/cookie/delAlimamaCookie/"+row.id,
					            dataType: "json",
					            success : function(result) {
					                if (result.success) {  
					                	messager.show("删除成功");
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
		// 清除表单
		clearForm : function() {
			$('#form').form('clear');
			$('input[name="isAvailable"]').removeAttr("checked").eq(0).prop('checked',"checked");
		}
};

$(function(){
	cookieModule.init();
	$('#search').click(cookieModule.search);
	$('#add').click(cookieModule.add);
	$('#modify').click(cookieModule.modify);
	$('#del').click(cookieModule.del);
	//解决页面加载时，出现未渲染的对话框内容
	$('#formDialog').css('visibility','visible');
});