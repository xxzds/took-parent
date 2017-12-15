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
	        pagination : false
		});
	},
	menuTreeInit:function(){
		$('#menu_tree').tree({
			url : ctx + '/getMenu',
			checkbox:true
		});
	}
}

$(function(){
	authModule.roleInit();
	authModule.menuTreeInit();
});