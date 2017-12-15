var roleModule={
	// 初始化表格
	init:function(){
		$('#dg').datagrid({
			url:ctx+'/system/role/getRoles',
			fit:true,
	        singleSelect: true,  
	        rownumbers:true,
	        toolbar:"#toolbar",
	        columns:[[
						{field:'id',hidden:true},
						{field:'roleName',title:'角色名称',align:'center',width:200},
						{field:'roleDescription',title:'角色描述',align:'center',width:200}
	                ]],
            onLoadError : function(data) {
	  			this.datagrid('loadData', {
	  				total : 0,
	  				rows : []
	  			})
            },
            onBeforeLoad:function(param){
	  			//设置查询条件
	  			param.roleName=$('#roleName').val();
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
	add:function(){
		
	},
	modify:function(){
		
	},
	del:function(){
		
	}
}

$(function(){
	roleModule.init();
	$('#search').click(roleModule.search);
	$('#add').click(roleModule.add);
	$('#modify').click(roleModule.modify);
	$('#del').click(roleModule.del);
})