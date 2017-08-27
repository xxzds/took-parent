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
});