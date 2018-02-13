var keywordCacheModule = {
	init : function(){
		$('#dg').datagrid({
			url:ctx+'/took/cache/queryKeywordDetails',
			fit:true,
	        singleSelect: true,  
	        rownumbers:true,
	        toolbar:"#toolbar",
	        columns:[[
						{field:'keyword',title:'商品分类id_关键字_最大页',align:'center',width:300},
						{field:'currentPage',title:'当前页',align:'center',width:200}
	                ]],
            onLoadError : function(data) {
	  			this.datagrid('loadData', {
	  				total : 0,
	  				rows : []
	  			})
            },
            onBeforeLoad:function(param){
	  			//接口类型
            	param.type =  $('#type').combobox('getValue');            	
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
    search : function(){
    	$('#dg').datagrid('reload');
    }
		
};

$(function(){
	keywordCacheModule.init();
	$('#search').click(keywordCacheModule.search);
});