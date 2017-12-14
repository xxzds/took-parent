var menuModule={
	 //根节点的PID
	 root:0,
	 //操作格式化,现限制到二级菜单
	 formatterOper:function(id,row){
		 if (row.parentId == menuModule.root) {
				return '<div style="margin-left:auto;margin-right:auto;text-align:center;background-color:red;width:90px;"><div style="float:left;cursor:pointer;" onclick="menuModule.add('
						+ id
						+ ')">增加</div>'
						+ '<div  style="margin-left:5px;float:left;cursor:pointer;" onclick="menuModule.modify('
						+ id
						+ ')">修改</div>'
						+ '<div style="margin-left:5px;float:left;cursor:pointer;" onclick="menuModule.del('
						+ id
						+ ','
						+ row.parentId
						+ ',\''
						+ row.menuName
						+ '\')" >删除</div></div>';
			} else {
				return '<div style="margin-left:auto;margin-right:auto;text-align:center;background-color:red;width:58px;">'
						+ '<div style="float:left;cursor:pointer;" onclick="menuModule.modify('
						+ id
						+ ')" >修改</div>'
						+ '<div style="float:right;cursor:pointer;" onclick="menuModule.del('
						+ id
						+ ','
						+ row.parentId
						+ ',\''
						+ row.menuName
						+ '\')">删除</div>' + '</div>';
			}
	 },
	 init:function(){
		 $('#menu').treegrid({
			 idField:'id',
			 treeField:'menuName',
			 url:ctx+'/system/menu/getMenuTree',
			 rownumbers: true,
			 toolbar:[{
				 iconCls:'icon-add',
					text:'增加目录',
					handler:function(){
						menuModule.add(menuModule.root);
					}
			 }],
			 queryParams:{
				 id:0
			 },//首次查询的参数
			 columns:[[
			           {field:'menuName',title:'菜单名称',width:200},
			           {field:'menuIdentify',title:'菜单标识',width:100},
			           {field:'menuUrl',title:'菜单url',width:200},
			           {field:'menuSort',title:'排序位置',width:60},
			           {field:'menuVisible',title:'是否可见',width:100,formatter:function(value,row){
			        	   return value==0 ? '否':(value==1 ? '是':'');
			           }},
			           {field:'id',title:'操作',width:200,formatter:menuModule.formatterOper,align:'center'}
			         ]]
		 });
	 },
	 //增加
	 add:function(pid){
		menuModule.clearForm();
		if(pid==menuModule.root){
			$('#formDialog').dialog({title: '增加目录', iconCls:'icon-add'});
		}else{
			$('#formDialog').dialog({title: '增加功能页面', iconCls:'icon-add'});
		}
		$('#formDialog').dialog('open');
		
		//保存按钮注册click事件
		$('#save').off('click').on('click',function(){
			$('#form').form('submit', {    
    		    url:ctx+'/system/menu/addMenu', 
    		    onSubmit: function(param){    
    		    	var valid = $(this).form('validate');
    		    	if(valid){
    		    		param.menuParentId=pid;
    		    	}
    		    	return valid;
    		    },    
    		    success:function(dataStr){
    		    	var data = JSON.parse(dataStr);
    		    	if(data.success){   		    		
    		    		$('#formDialog').dialog('close');
    		    		menuModule.reload(pid);
    		    		messager.show("添加成功");
    		    	}else{
    		    		$.messager.alert('提示',data.message); 
    		    	}
    		    }    
    		});
		});
	 },
	 //修改
	 modify:function(id){
		 menuModule.clearForm();
		 $('#formDialog').dialog({title: '修改页面信息',iconCls:'icon-edit'});
		 $('#formDialog').dialog('open');
		 
		 var row = $("#menu").treegrid('find', id);
		 //载入数据到表单
		 $('#form').form('load',row);
		 //图标
		 $('#menuIcon').textbox('setValue',row.iconCls);
		 
		//保存按钮注册click事件
		$('#save').off('click').on('click',function(){
			$('#form').form('submit', {    
    		    url:ctx+'/system/menu/modifyMenu', 
    		    onSubmit: function(param){    
    		    	var valid = $(this).form('validate');
    		    	if(valid){
    		    		param.id = id;
    		    	}
    		    	return valid;
    		    },    
    		    success:function(dataStr){
    		    	var data = JSON.parse(dataStr);
    		    	if(data.success){
    		    		$('#formDialog').dialog('close');
    		    		
    		    		//从父节点刷新
    		    		menuModule.reload(row.parentId);
    		    		messager.show("修改成功");
    		    	}else{
    		    		$.messager.alert('提示',data.message); 
    		    	}
    		    }    
    		});
		});
	 },
	 //删除
	 del:function(id,pid,title){
		 $.messager.confirm("提示", "删除后，数据将无法恢复！确认要删除【" + title + "】吗？",
				function(r) {
			 		if(r){
			 			$.ajax({  
				            type : "POST",  
				            url : ctx+"/system/menu/delMenu/"+id,
				            dataType: "json",
				            success : function(result) {
				                if (result.success) {  
				                	messager.show("删除菜单成功");
				                	//重新加载
				                	menuModule.reload(pid);		                	
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
	 //重新加载
	 reload:function(id){
		 if(id == menuModule.root){
				$("#menu").treegrid('reload');
			}else{
				$("#menu").treegrid('reload', id);
			}
	 },
	 //清除表单
	 clearForm:function(){
		$('#form').form('clear');
		$('input[name="menuVisible"]').removeAttr("checked").eq(0).prop('checked',"checked");
	}
}

$(function(){
	//初始化，树型菜单
	menuModule.init();
	
	//解决页面加载时，出现未渲染的对话框内容
	$('#formDialog').css('visibility','visible');
})