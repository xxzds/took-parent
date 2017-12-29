var menuModule={
	 //根节点的PID
	 root:0,
	 //操作格式化,现限制到二级菜单
//	 formatterOper:function(id,row){
//		 if (row.parentId == menuModule.root) {
//				return '<div style="margin-left:auto;margin-right:auto;text-align:center;background-color:red;width:90px;"><div style="float:left;cursor:pointer;" onclick="menuModule.add('
//						+ id
//						+ ')">增加</div>'
//						+ '<div  style="margin-left:5px;float:left;cursor:pointer;" onclick="menuModule.modify('
//						+ id
//						+ ')">修改</div>'
//						+ '<div style="margin-left:5px;float:left;cursor:pointer;" onclick="menuModule.del('
//						+ id
//						+ ','
//						+ row.parentId
//						+ ',\''
//						+ row.menuName
//						+ '\')" >删除</div></div>';
//			} else {
//				return '<div style="margin-left:auto;margin-right:auto;text-align:center;background-color:red;width:58px;">'
//						+ '<div style="float:left;cursor:pointer;" onclick="menuModule.modify('
//						+ id
//						+ ')" >修改</div>'
//						+ '<div style="float:right;cursor:pointer;" onclick="menuModule.del('
//						+ id
//						+ ','
//						+ row.parentId
//						+ ',\''
//						+ row.menuName
//						+ '\')">删除</div>' + '</div>';
//			}
//	 },
	 init:function(){
		 $('#menu').treegrid({
			 idField:'id',
			 treeField:'menuName',
			 url:ctx+'/system/menu/getMenuTree',
			 rownumbers: true,
			 toolbar:'#toolbar',
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
		$('#selectedIcon').html('');
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
		 $('#selectedIcon').html('');
		 $('#formDialog').dialog({title: '修改页面信息',iconCls:'icon-edit'});
		 $('#formDialog').dialog('open');
		 
		 var row = $("#menu").treegrid('find', id);
		 //载入数据到表单
		 $('#form').form('load',row);
		 //图标名称
		 $('#menuIcon').textbox('setValue',row.iconCls);
		 //图标
		 $('#selectedIcon').html('<span class="icon-span ' + row.iconCls + '"></span>');
		 
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
	},
	menuIconInit:function(){
		$('#menuIcon').combo({
			required : true,
			width : 150,
			panelWidth : 300,
			editable:false
		});

		$('#iconSelectPanel').appendTo($('#menuIcon').combo('panel'));
		
		//注册事件
		$('#iconList').delegate('span', 'click', function () {
			var value = $(this).data('icon');
            var title = $(this).attr('title');
			
			var formatter = '<span class="icon-span ' + value + '" title="' + title + '" ></span>';
            $('#selectedIcon').html(formatter);
            $('#menuIcon').combo('setValue', value).combo('setText', value).combo('hidePanel');
		});

		//分页
		$('#pp').pagination({
			pageNumber : 1,
			pageSize : 50,
			pageList: [20,40,60,80],
			showPageList: false,
			displayMsg:'',
			onSelectPage : function(pageNumber, pageSize) {
				$(this).pagination('loading');
				$(this).pagination('loaded');
				menuModule.requestIcon(pageNumber,pageSize);
			}
		});
		
		//初始请求
		menuModule.requestIcon(1,50);
	},
	requestIcon:function(pageNumber,pageSize){
		$.ajax({  
            type : "POST",  
            url : ctx+"/system/icon/getIcons",
            dataType: "json",
            data: {
                page: pageNumber,
                rows: pageSize
            },
            success : function(result) {
                if (result.success) {  
                	$('#pp').pagination({ total: result.totalCount});
                	 $('#iconList').html($('#iconTemplate').render(result.data));
                } else {  
                	messager.show(result.message); 
                }  
            },
            error:function(){
            	messager.alert("网络异常"); 
            }
        });
	}
}

$(function(){
	//初始化，树型菜单
	menuModule.init();
	
	//初始化，图标
	menuModule.menuIconInit();
	
	//增加目录
	$('#add').click(function(){
		menuModule.add(menuModule.root);
	});
		
	//解决页面加载时，出现未渲染的对话框内容
	$('#formDialog').css('visibility','visible');
})