var messager={
		show:function(title){
			$.messager.show({
				title : '提示',
				msg : title,
				timeout : 2000,
				showType : 'slide'
			});
		},
		alert:function(title){
			$.messager.alert("错误",title);
		}
}