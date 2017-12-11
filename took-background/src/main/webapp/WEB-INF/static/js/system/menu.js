var menuModule={
	 init:function(){
		 $('#menu').treegrid({
			 idField:'id',
			 treeField:'menuName',
			 url:ctx+'/system/menu/getMenuTree',
			 rownumbers: true,
			 queryParams:{
				 pid:0
			 },//首次查询的参数
			 columns:[[
			           {field:'menuName',title:'菜单名称'},
			           {field:'menuIdentify',title:'菜单标识'},
			           {field:'menuUrl',title:'菜单url'}
			         ]],  
			 loadFilter:function(data,parentId){
				 console.log(data);
				 return {
					 rows:data.data
				 }
			 }
		 });
	 }
}

$(function(){
	menuModule.init();
})