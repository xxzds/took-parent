//角色列表
$('#role').combobox({
	url:ctx+"/common/getRoles",
	valueField:'id',    
    textField:'roleName'
});

//商品分类-(包含全部，默认选中全部)
$('#searchItemCate').combobox({
	url:ctx+"/common/getItemCates",
	valueField:'id',    
    textField:'itemCateName',
    onBeforeLoad:function(param){
    	param.isIncludeAll = true;
    },
    onLoadSuccess:function(){
    	var val = $(this).combobox("getData");
    	$(this).combobox('select', val[0].id);
    }
});

//商品分类(不包含全部，默认不选中)
$('#itemCateId').combobox({
	url:ctx+"/common/getItemCates",
	valueField:'id',    
    textField:'itemCateName',
    onBeforeLoad:function(param){
    	param.isIncludeAll = false;
    }
});
