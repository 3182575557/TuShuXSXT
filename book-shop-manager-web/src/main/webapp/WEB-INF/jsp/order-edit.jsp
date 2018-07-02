<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div style="padding:10px 10px 10px 10px">
	<form id="ordereEditForm" >
	    <table cellpadding="5">
	        <tr>
	        
	         <td>订单ID:</td>
            <td style="width:50" >
            	<input style="width:50" class="order" type="text" name="orderId" id="orderId" readonly="readonly"/><br>
            </td>
	        <tr>
	            <td>请选择订单状态:</td>
	            
	            <td>
	            	<a href="javascript:changeStauts1(1)" id="btn" class="easyui-linkbutton selectorderCat order">未付款</a>
	            </td>
	            <td>
	            	<a href="javascript:changeStauts2(2)" id="btn" class="easyui-linkbutton selectorderCat order">已付款</a>
	            </td>
	            <td>
	            	<a href="javascript:changeStauts3(3)" id="btn" class="easyui-linkbutton selectorderCat order">未发货</a>
	            </td>
	            <td>
	            	<a href="javascript:changeStauts4(4)" id="btn" class="easyui-linkbutton selectorderCat order">已发货</a>
	            </td>
	            <td>
	            	<a href="javascript:changeStauts5(5)" id="btn" class="easyui-linkbutton selectorderCat order">交易成功</a>
	            </td>
	            <td>
	            	<a href="javascript:changeStauts6(6)" id="btn" class="easyui-linkbutton selectorderCat order">交易关闭</a>
	            </td>
	        </tr>
	       
	    </table>
	</form>
	
</div>
<script type="text/javascript">

function changeStauts1(stauts){
	var url = "/order/change/stauts?id="+$("#orderId").val()+"&stauts="+stauts;
	//alert("/rest/order/update/"+$("#orderId").val()+stauts);?page=1&rows=30
	//$("#btn").click(function(){
		$.post(url, function(data){
			if(data.status == 200){
				$.messager.alert('提示','修改订单成功!','info',function(){
					$("#orderEditWindow").window('close');
					$("#orderList").datagrid("reload");
				});
			}
		//});
	});
}	

function changeStauts2(stauts){
	var url = "/order/change/stauts?id="+$("#orderId").val()+"&stauts="+stauts;
	//alert("/rest/order/update/"+$("#orderId").val()+stauts);?page=1&rows=30
	//$("#btn").click(function(){
		$.post(url, function(data){
			if(data.status == 200){
				$.messager.alert('提示','修改订单成功!','info',function(){
					$("#orderEditWindow").window('close');
					$("#orderList").datagrid("reload");
				});
			}
		//});
	});
}

function changeStauts3(stauts){
	var url = "/order/change/stauts?id="+$("#orderId").val()+"&stauts="+stauts;
	//alert("/rest/order/update/"+$("#orderId").val()+stauts);?page=1&rows=30
	//$("#btn").click(function(){
		$.post(url, function(data){
			if(data.status == 200){
				$.messager.alert('提示','修改订单成功!','info',function(){
					$("#orderEditWindow").window('close');
					$("#orderList").datagrid("reload");
				});
			}
		//});
	});
}	

function changeStauts4(stauts){
	var url = "/order/change/stauts?id="+$("#orderId").val()+"&stauts="+stauts;
	//alert("/rest/order/update/"+$("#orderId").val()+stauts);?page=1&rows=30
	//$("#btn").click(function(){
		$.post(url, function(data){
			if(data.status == 200){
				$.messager.alert('提示','修改订单成功!','info',function(){
					$("#orderEditWindow").window('close');
					$("#orderList").datagrid("reload");
				});
			}
		});
	//});
}

function changeStauts5(stauts){
	var url = "/order/change/stauts?id="+$("#orderId").val()+"&stauts="+stauts;
	//alert("/rest/order/update/"+$("#orderId").val()+stauts);?page=1&rows=30
	//$("#btn").click(function(){
		$.post(url, function(data){
			if(data.status == 200){
				$.messager.alert('提示','修改订单成功!','info',function(){
					$("#orderEditWindow").window('close');
					$("#orderList").datagrid("reload");
				});
			}
		//});
	});
}

function changeStauts6(stauts){
	var url = "/order/change/stauts?id="+$("#orderId").val()+"&stauts="+stauts;
	//alert("/rest/order/update/"+$("#orderId").val()+stauts);?page=1&rows=30
	//$("#btn").click(function(){
		$.post(url, function(data){
			if(data.status == 200){
				$.messager.alert('提示','修改订单成功!','info',function(){
					$("#orderEditWindow").window('close');
					$("#orderList").datagrid("reload");
				});
			}
		//});
	});
}	
</script>
