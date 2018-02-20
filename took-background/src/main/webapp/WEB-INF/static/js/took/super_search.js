var superSearchModule = {
		// 初始化表格
		init : function(){
			$('#dg').datagrid({
				url:ctx+'/took/superSearch/getAlimamaItems',
				fit:true, 
		        rownumbers:true,
		        toolbar:"#toolbar",
		        columns:[[
		                    {field:'ck',checkbox:true},
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
		  			//关键字
	            	param.q = $('#q').val();
	            	//排序方式
	            	var queryType = $('#queryType').combobox('getValue');
	            	if(queryType == 0 || queryType == 2){
	            		param.queryType = queryType;
	            	}else{
	            		param.sortType = queryType;
	            	}
	            	//包邮
	            	if($('#freeShipment').is(':checked')){
	            		param.freeShipment=$('#freeShipment').val();
	            	}
	            	//营销和定向计划
	            	if($('#yxjh').is(':checked')){
	            		param.yxjh=$('#yxjh').val();
	            	}	
	            	//优惠券
	            	if($('#dpyhq').is(':checked')){
	            		param.dpyhq=$('#dpyhq').val();
	            	}
	            	//月成交转化率高于行业均值
	            	if($('#hPayRate30').is(':checked')){
	            		param.hPayRate30=$('#hPayRate30').val();
	            	}
	            	//天猫旗舰店
	            	if($('#b2c').is(':checked')){
	            		param.b2c=$('#b2c').val();
	            	}
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
		collect : function(){
			var rows = $('#dg').datagrid('getChecked');
			if(!rows || rows.length == 0){
				messager.show("请选择要采集的商品!");
				return;
			}
			debugger;
			$('#form').form('clear');
			$('#formDialog').dialog({title: '采集', iconCls:'icon-add'});
			$('#formDialog').dialog('open');
			
			//采集按钮注册click事件
			$('#save').off('click').on('click',function(){
				var validate = $('#form').form('validate'); 
				if(!validate) return;
				$.ajax({
					url:ctx+'/took/superSearch/collectItems?cateId='+$('#itemCateId').combobox('getValue'),
					type:'POST',									
					contentType:'application/json',
					data:JSON.stringify(rows),					
					success:function(data){
						if(data.success){   		    		
	    		    		$('#formDialog').dialog('close');
	    		    		//重新加载列表
	    		    		$('#dg').datagrid('reload');	
	    		    		messager.show(data.data);
	    		    	}else{
	    		    		$.messager.alert('提示',data.message); 
	    		    	}
					},
			        error:function(){
			        	$.messager.alert('提示','网络异常'); 
			        }
			  });
		  })
		}
}

$(function(){
	superSearchModule.init();
	$('#search').click(superSearchModule.search);
	$('#collect').click(superSearchModule.collect);
	//解决页面加载时，出现未渲染的对话框内容
	$('#formDialog').css('visibility','visible');
});