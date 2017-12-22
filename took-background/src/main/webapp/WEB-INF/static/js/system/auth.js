var authModule = {
	//角色列表初始化
	roleInit:function(){
		$('#role').datagrid({
			url:ctx+'/system/role/getRoles',
			fit:true,
	        singleSelect: true,  
	        rownumbers:true,
	        toolbar:"#toolbar",
	        columns:[[
						{field:'id',hidden:true},
						{field:'roleName',title:'角色名称',align:'center',width:150}
	                ]],
            onLoadError : function(data) {
	  			this.datagrid('loadData', {
	  				total : 0,
	  				rows : []
	  			})
            },
  			loadFilter:function(data){
	        	return {
	        		total:data.totalCount,
	        		rows:data.data
	        	}
	        },
	        pagination : false,
	        onClickRow:function(index,row){
	        	$('#roleId').val(row.id);
	        	$('#menu_tree').tree('reload');
	        	
	        	//重置
	        	$('#roleMenuId').val('');
	        	authModule.permissionInit();
	        }
		});
	},
	menuTreeInit:function(){
		$('#menu_tree').tree({
			url : ctx + '/system/auth/getMenuByRoleId',
			checkbox:true,
			animate:true,
			onBeforeLoad:function(node,param){
				param.roleId=$('#roleId').val();
			},
			onClick:function(node){
				var roleMenuId = node.attributes.roleMenuId;
				if(roleMenuId != null && roleMenuId != ''){
					$('#roleMenuId').val(roleMenuId);
					authModule.permissionInit();
				}	
			}
		});
	},
	permissionInit:function(){
		$.ajax({  
            type : "POST",  
            url : ctx+"/system/permission/queryPermissons",
            data:{
            	roleMenuId:$('#roleMenuId').val()
            },
            success : function(result) {
            		$('#permission_list').empty();
            		$('#permission_list').append(result);
            },
            error:function(){
            	messager.alert("网络异常"); 
            }
        }); 
	},
	menuSave:function(){
		var roleId=$('#roleId').val();
		
		if(roleId==null || roleId==''){
			messager.show("请选择一角色");
			return;
		}
		
		var menuIds=[];
		var nodes = $('#menu_tree').tree('getChecked');
		for(var i=0;i<nodes.length;i++){
			menuIds.push(nodes[i].id);
		}
		
		$.ajax({  
            type : "POST",  
            url : ctx+"/system/auth/addRoleMenu",
            dataType: "json",
            data:{
            	roleId:roleId,
            	menuIds:menuIds.join(",")
            },
            success : function(result) {
            	if(result.success){
            		messager.show("保存菜单成功");
                	$('#menu_tree').tree('reload');
            	}else{
            		messager.show(result.message); 
            	}           	
            },
            error:function(){
            	messager.alert("网络异常"); 
            }
        }); 
	},
	permissionSave:function(){
	    var roleMenuId = $('#roleMenuId').val();
	    
	    if(roleMenuId==null || roleMenuId==''){
			messager.show("请选择一菜单");
			return;
		}
	    
	    var permissionIds =[];
	    $('input[name="permissionId"]:checked').each(function(){
	    	permissionIds.push($(this).val());
	    })
	    
	    $.ajax({  
            type : "POST",  
            url : ctx+"/system/auth/addRoleMenuPermission",
            dataType: "json",
            data:{
            	roleMenuId:roleMenuId,
            	permissionIds:permissionIds.join(",")
            },
            success : function(result) {
            	if(result.success){
            		messager.show("保存权限成功");
            		authModule.permissionInit();
            	}else{
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
	authModule.roleInit();
	authModule.menuTreeInit();
	authModule.permissionInit();
	//保存菜单
	$('#menusave').click(authModule.menuSave);
	//保存权限
	$('#permissionsave').click(authModule.permissionSave);
});