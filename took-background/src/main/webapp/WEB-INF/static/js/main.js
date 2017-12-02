$(function(){
	// 实例化树形菜单
	$("#tree").tree({
		url : ctx + '/getMenu',
		lines : true,
		onClick : function(node) {
			//是叶子节点才能打开tab页
			if(node.attributes.isLeaf){
				open(ctx+node.attributes.url, node.text);
			}	
		}
	});
	// 在右边center区域打开菜单，新增tab
	function open(url, title) {
		if ($('#tabs').tabs('exists', title)) {
			$('#tabs').tabs('select', title);
		} else {
			$('#tabs').tabs('add',
					{
						iconCls : 'icon-tip',
						title : title,
						content : '<iframe frameborder="0" src="'+url+'" style="width:100%;height:99%;"/>',
						closable : true
					});
		}
	
	}
	
	// 绑定tabs的右键菜单
	$("#tabs").tabs({
		onContextMenu : function(e, title) {
			e.preventDefault();
			$('#tabsMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data("tabTitle", title);
		}
	});

	// 实例化menu的onClick事件
	$("#tabsMenu").menu({
		onClick : function(item) {
			CloseTab(this, item.name);
		}
	});

	// 几个关闭事件的实现
	function CloseTab(menu, type) {
		var curTabTitle = $(menu).data("tabTitle");
		var tabs = $("#tabs");

		if (type === "close") {
			tabs.tabs("close", curTabTitle);
			return;
		}

		var allTabs = tabs.tabs("tabs");
		var closeTabsTitle = [];

		$.each(allTabs, function() {
			var opt = $(this).panel("options");
			if (opt.closable && opt.title != curTabTitle && type === "other") {
				closeTabsTitle.push(opt.title);
			} else if (opt.closable && type === "all") {
				closeTabsTitle.push(opt.title);
			}
		});

		for (var i = 0; i < closeTabsTitle.length; i++) {
			tabs.tabs("close", closeTabsTitle[i]);
		}
	}
	
	//修改密码
	$('#modifyPwd').click(function(){
		//显示对话框的内容
		$('#formDialog').css('visibility','visible');
				
		$('#formDialog').dialog({
			title: '修改密码',    
		    width: 400,    
		    height: 200,    
		    iconCls:'icon-edit',
		    cache: false,   
		    modal: true,
		    buttons:[{
		    	text:'保存',
		    	iconCls:'icon-ok',
		    	plain:true,
		    	handler:function(){
		    		debugger;
		    		$('#form').form('submit', {    
		    		    url:ctx+'/system/user/modifyPassword', 
		    		    onSubmit: function(param){    
		    		    	return $(this).form('validate');
		    		    },    
		    		    success:function(dataStr){
		    		    	var data = JSON.parse(dataStr);
		    		    	if(data.success){
		    		    		messager.show('密码修改成功');
		    		    		$(this).form('clear');
		    		    		$('#formDialog').dialog('close');
		    		    	}else{
		    		    		messager.show(data.message);
		    		    	}
		    		    }    
		    		}); 
		    	}
		    },{
		    	text:'关闭',
		    	iconCls:'icon-cancel',
		    	plain:true,
		    	handler:function(){
		    		$('#form').form('clear');
		    		$('#formDialog').dialog('close');
		    	}
		    }]
		});
	});
	
	
	//退出
	$('#logout').click(function(){
		$.messager.confirm('确认','您确认要退出吗？',function(r){
			if(r){
				$.ajax({  
		            type : "POST",  
		            url : ctx+"/logout",
		            dataType: "json",
		            success : function(result) {
		                if (result.success) {  
		                	top.window.location.href=ctx+"/toLogin";		                	
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
	});
});