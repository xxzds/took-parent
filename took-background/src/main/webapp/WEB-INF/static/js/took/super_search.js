var superSearchModule = {
		// 初始化表格
		init : function(){
			$('#dg').datagrid({
				url:ctx+'/took/superSearch/getAlimamaItems',
				fit:true,
		        singleSelect: true,  
		        rownumbers:true,
		        toolbar:"#toolbar",
		        columns:[[
							{field:'auctionId',hidden:true},
							{field:'pictUrl',title:'主图',align:'center',width:80,formatter:function(value,row){
								return '<a target="_blank" href="'+row.auctionUrl+'"><img width="60px" width="60px" src="'+value+'"/></a>';
							}},
							{field:'title',title:'商品标题',align:'center',width:200,formatter:function(value){
								 return "<span title='" + value + "'>" + value + "</span>";
							}},
							{field:'reservePrice',title:'原价(元)',align:'center',width:80},
							{field:'zkPrice',title:'现价(元)',align:'center',width:80},
							{field:'biz30day',title:'月销(件)',align:'center',width:80},
							{field:'tkRate',title:'佣金比率(%)',align:'center',width:80},
							{field:'tkCommFee',title:'佣金(元)',align:'center',width:80},
							{field:'userType',title:'类型',align:'center',width:50,formatter:function(value,row){
								return value==1 ? '天猫' : ((value == 0) ? '淘宝' : '');
							}},
							{field:'shopTitle',title:'商店名称',align:'center',width:150},
							{field:'nick',title:'卖家昵称',align:'center',width:100},
							{field:'couponAmount',title:'优惠券金额(元)',align:'center',width:90},
							{field:'couponEffectiveStartTime',title:'优惠券有效开始时间',align:'center',width:120},
							{field:'couponEffectiveEndTime',title:'优惠券有效结束时间',align:'center',width:120}
		                ]],
	            onLoadError : function(data) {
		  			this.datagrid('loadData', {
		  				total : 0,
		  				rows : []
		  			})
	            },
	            onBeforeLoad:function(param){
		  			//设置查询条件
	            	param.q = $('#q').val();
	            	param.dpyhq=$('#dpyhq').val();
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
		search : function(){
			$('#dg').datagrid('reload');
		},	
}

$(function(){
	superSearchModule.init();
	$('#search').click(superSearchModule.search);
});