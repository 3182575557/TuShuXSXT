<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../css/taotao2.css" />
<script type="text/javascript" src="../js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/common.js"></script>

<span>
	<a href="http://localhost:8082" style="background-color: #ffff80; size: 20px;"><span style="color: black;">回到首页</span></a>
</span>

<table class="easyui-datagrid" id="orderList" title="订单列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/order/userid'" >
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'orderId',width:120">订单ID</th>
            <th data-options="field:'bookId',width:200">图书ID</th>
            <th data-options="field:'userId',width:100">用户ID</th>
            <th data-options="field:'status',width:60,align:'center',formatter:TAOTAO.formatorderStatus">状态</th> 
            <th data-options="field:'paymentYype',width:60">支付类型</th>
            <th data-options="field:'num',width:60">购买数量</th>
            <th data-options="field:'postFee',width:70,align:'right',formatter:TAOTAO.formatPrice">邮费</th>
            <th data-options="field:'totalFee',width:70,align:'right',formatter:TAOTAO.formatPrice">总价</th>
            <th data-options="field:'receiverName',width:80">收货人</th>
            <th data-options="field:'receiverMobile',width:150">联系电话</th>
            <th data-options="field:'receiverState',width:80">收货地址：省</th>
            <th data-options="field:'receiverCity',width:80">市</th>
            <th data-options="field:'receiverDistrict',width:80">县/区</th>
            <th data-options="field:'receiverAddress',width:150">具体位置</th>
            <th data-options="field:'createTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'paymentTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">付款日期</th>
            <th data-options="field:'consignTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">发货日期</th>
        </tr>
    </thead>
</table>

