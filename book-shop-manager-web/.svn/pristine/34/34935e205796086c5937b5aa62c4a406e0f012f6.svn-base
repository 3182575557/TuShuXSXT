<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div style="padding:10px 10px 10px 10px">
	<form id="orderEditShippingWindow" >
	    <table cellpadding="5">
	        <tr>
	        
	         <td>订单ID:</td>
            <td style="width:50" >
            	<input style="width:50" class="order" type="text" name="orderId" id="orderId" readonly="readonly"/><br>
            </td>
	        <tr>
	            <td>请修改物流:</td>
	            <td>
	            <input style="width:50" class="order" type="text" name="shippingName" id="Shipping"/>
	             </td>
	            <td>
	           		<a href="javascript:changeShipping()" id="btn" class="easyui-linkbutton selectorderCat order">修改</a>
	            </td>
	        </tr>
	       
	    </table>
	</form>
	
</div>
<script type="text/javascript">

function changeShipping(){
	var url = "/order/change/Shipping?id="+$("#orderId").val()+"&Shipping="+$("#Shipping").val();
	//alert(url);
	//alert("/rest/order/update/"+$("#orderId").val()+Shipping);?page=1&rows=30
	//$("#btn").click(function(){
		$.post(url, function(data){
			if(data.status == 200){
				$.messager.alert('提示','修改物流成功!','info',function(){
					$("#orderEditShippingWindow").window('close');
					$("#orderList").datagrid("reload");
				});
			}
		//});
	});
}	
	
</script>
