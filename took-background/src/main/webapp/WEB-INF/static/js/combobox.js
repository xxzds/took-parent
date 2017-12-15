//角色列表
$('#role').combobox({
	url:ctx+"/common/getRoles",
	valueField:'id',    
    textField:'roleName'
});