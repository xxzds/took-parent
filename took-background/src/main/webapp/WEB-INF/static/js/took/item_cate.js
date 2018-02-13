var itemCateModule = {
		// 初始化表格
		init : function(){
			$('#dg').datagrid({
				url:ctx+'/took/itemcate/getItemCates',
				fit:true,
		        singleSelect: true,  
		        rownumbers:true,
		        toolbar:"#toolbar",
		        columns:[[
							{field:'id',hidden:true},
							{field:'itemCateName',title:'分类名称',align:'center',width:200},
							{field:'itemCateSort',title:'排列位置',align:'center',width:200},
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
		  			//设置查询条件
	            	param.itemCateName = $('#searchItemCateName').val();
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
			itemCateModule.clearForm();
			$('#formDialog').dialog({title: '新增', iconCls:'icon-add'});
			$('#formDialog').dialog('open');
			
			//保存按钮注册click事件
			$('#save').off('click').on('click',function(){
				$('#form').form('submit', {    
	    		    url:ctx+'/took/itemcate/addItemCate', 
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
			
			itemCateModule.clearForm();
			 $('#formDialog').dialog({title: '修改',iconCls:'icon-edit'});
			 //载入数据到表单
			 $('#form').form('load',row);
			 $('#formDialog').dialog('open');
			 
			//保存按钮注册click事件
			$('#save').off('click').on('click',function(){
				$('#form').form('submit', {    
	    		    url:ctx+'/took/itemcate/modifyItemCate', 
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
			
			$.messager.confirm("提示", "您确定删除此商品分类吗？",
					function(r) {
				 		if(r){
				 			$.ajax({  
					            type : "POST",  
					            url : ctx+"/took/itemcate/delItemCate/"+row.id,
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
	itemCateModule.init();
	$('#search').click(itemCateModule.search);
	$('#add').click(itemCateModule.add);
	$('#modify').click(itemCateModule.modify);
	$('#del').click(itemCateModule.del);
	
	//解决页面加载时，出现未渲染的对话框内容
	$('#formDialog').css('visibility','visible');
});