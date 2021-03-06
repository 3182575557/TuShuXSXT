<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="css/taotao.css" />
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/common.js"></script>

<table class="easyui-datagrid" id="orderList" title="订单列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/order/list',method:'get',pageSize:30,toolbar:toolbar">
    <thead><!-- 
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'order_id',width:120">订单ID</th>
            <th data-options="field:'book_id',width:200">图书ID</th>
            <th data-options="field:'user_id',width:100">用户ID</th>
            <th data-options="field:'status',width:60,align:'center',formatter:TAOTAO.formatorderStatus">状态</th> 
            <th data-options="field:'payment_type',width:60">支付类型</th>
            <th data-options="field:'num',width:60">购买数量</th>
            <th data-options="field:'post_fee',width:70,align:'right',formatter:TAOTAO.formatPrice">邮费</th>
            <th data-options="field:'total_fee',width:70,align:'right',formatter:TAOTAO.formatPrice">总价</th>
            <th data-options="field:'receiver_name',width:80">收货人</th>
            <th data-options="field:'receiver_mobile',width:150">联系电话</th>
            <th data-options="field:'receiver_state',width:80">收货地址：省</th>
            <th data-options="field:'receiver_city',width:80">市</th>
            <th data-options="field:'receiver_district',width:80">县/区</th>
            <th data-options="field:'receiver_address',width:150">具体位置</th>
            <th data-options="field:'shipping_Name',width:150">物流</th>
            <th data-options="field:'create_time',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'payment_time',width:130,align:'center',formatter:TAOTAO.formatDateTime">付款日期</th>
            <th data-options="field:'consign_time',width:130,align:'center',formatter:TAOTAO.formatDateTime">发货日期</th>
        </tr> -->
        
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'orderId',width:120">订单ID</th>
            <th data-options="field:'bookId',width:200">图书ID</th>
            <th data-options="field:'userId',width:100">用户ID</th>
            <th data-options="field:'status',width:60,align:'center',formatter:TAOTAO.formatorderStatus">状态</th> 
            <th data-options="field:'paymentType',width:60">支付类型</th>
            <th data-options="field:'num',width:60">购买数量</th>
            <th data-options="field:'postFee',width:70,align:'right',formatter:TAOTAO.formatPrice">邮费</th>
            <th data-options="field:'totalFee',width:70,align:'right',formatter:TAOTAO.formatPrice">总价</th>
            <th data-options="field:'receiverName',width:80">收货人</th>
            <th data-options="field:'receiverMobile',width:150">联系电话</th>
            <th data-options="field:'receiverState',width:80">收货地址：省</th>
            <th data-options="field:'receiverCity',width:80">市</th>
            <th data-options="field:'receiverDistrict',width:80">县/区</th>
            <th data-options="field:'receiverAddress',width:150">具体位置</th>
            <th data-options="field:'shippingName',width:150">物流</th>
            <th data-options="field:'createTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'paymentTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">付款日期</th>
            <th data-options="field:'consignTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">发货日期</th>
        </tr>
    </thead>
</table>
<!-- ,href:'/rest/page/order-edit' -->
 <div id="orderEditWindow" class="easyui-window" title="设置订单状态" data-options="modal:true,closed:true,iconCls:'icon-save',href:'order-edit'" style="width:80%;height:80%;padding:10px;">
 </div>
 <div id="orderEditShippingWindow" class="easyui-window" title="设置订单物流" data-options="modal:true,closed:true,iconCls:'icon-save',href:'order-edit-Shipping'" style="width:80%;height:80%;padding:10px;">
</div>
 

<script>

    function getSelectionsIds(){
    	var orderList = $("#orderList");
    	var sels = orderList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].orderId);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var toolbar = [{
        text:'设置订单状态',
        iconCls:'icon-edit',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一个订单才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一个订单!');
        		return ;
        	}
        	
        	$("#orderEditWindow").window({
        		onLoad :function(){
        			//回显数据
        			var data = $("#orderList").datagrid("getSelections")[0];
        			data.priceView = TAOTAO.formatPrice(data.price);
        			$("#ordereEditForm").form("load",data);
        			var ids = getSelectionsIds();
        		}
        	}).window("open");
            
        	
        }
    },
    {
        text:'设置物流',
        iconCls:'icon-edit',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一个订单才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一个订单!');
        		return ;
        	}
        	
        	$("#orderEditShippingWindow").window({
        		onLoad :function(){
        			//回显数据
        			var data = $("#orderList").datagrid("getSelections")[0];
        			data.priceView = TAOTAO.formatPrice(data.price);
        			$("#orderEditShippingWindow").form("load",data);
        			var ids = getSelectionsIds();
        		}
        	}).window("open");
            
        	
        }
    },
    
    
    {
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中订单!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的订单吗？',function(r){
        	    if (r){
        	    	var params = "/rest/order/delete/"+ids;
                	$.post(params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','删除订单成功!',undefined,function(){
            					$("#orderList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    }];
</script>