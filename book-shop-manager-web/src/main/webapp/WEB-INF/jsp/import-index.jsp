<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="css/taotao.css" />
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/common.js"></script>

<div>
	<a class="easyui-linkbutton" onclick="importIndex()">一键导入图书数据到索引库</a>
</div>
<script type="text/javascript">
function importIndex() {
	$.post("/index/import",function(data){
		if(data.status == 200){
			$.messager.alert('提示','导入索引库成功！！！！');
		}else{
			$.messager.alert('提示','导入索引库失败~~~~~');
		}
	});
}
</script>