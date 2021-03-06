<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="css/taotao.css" />
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/common.js"></script>

<table class="easyui-datagrid" id="bookList" title="图书列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/book/list',method:'get',pageSize:30,toolbar:toolbar">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'id',width:160">图书ID</th>
            <th data-options="field:'title',width:200">图书标题</th>
            <th data-options="field:'cid',width:100">叶子类目</th>
            <th data-options="field:'sellPoint',width:100">卖点</th>
            <th data-options="field:'price',width:70,align:'right',formatter:TAOTAO.formatPrice">价格</th>
            <th data-options="field:'num',width:70,align:'right'">库存数量</th>
            <th data-options="field:'barcode',width:100">条形码</th>
            <th data-options="field:'status',width:60,align:'center',formatter:TAOTAO.formatBookStatus">状态</th>
            <th data-options="field:'created',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'updated',width:130,align:'center',formatter:TAOTAO.formatDateTime">更新日期</th>
        </tr>
    </thead>
</table>
<div id="bookEditWindow" class="easyui-window" title="编辑图书" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/book-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>

    function getSelectionsIds(){
    	var bookList = $("#bookList");
    	var sels = bookList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].id);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var toolbar = [ {
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	TT.createWindow({
    			url : "/book-add"
    		}); 
        }
    }, {
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一个图书才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一个图书!');
        		return ;
        	}
        	
        	$("#bookEditWindow").window({
        		onLoad :function(){
        			//回显数据
        			var data = $("#bookList").datagrid("getSelections")[0];
        			data.priceView = TAOTAO.formatPrice(data.price);
        			$("#bookeEditForm").form("load",data);
        			
        			// 加载图书详情内容描述/rest/book/query/book/desc/
        			$.getJSON('/rest/book/desc/'+data.id,function(_data){
        				if(_data.status == 200){
        					//UM.getEditor('bookeEditDescEditor').setContent(_data.data.bookDesc, false);
        					bookEditEditor.html(_data.data.bookDesc);
        				}
        			});
        	
        			
        			TAOTAO.init({
        				"pics" : data.image,
        				"cid" : data.cid,
        				fun:function(node){
        					TAOTAO.changeBookParam(node, "bookeEditForm");
        				}
        			});
        		}
        	}).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中图书!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的图书吗？',function(r){
        	    if (r){
        	    	//var params = {"ids":ids};
        	    	var url = "/rest/book/delete/"+ids;
                	$.post(url, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','删除图书成功!',undefined,function(){
            					$("#bookList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    },'-',{
        text:'下架',
        iconCls:'icon-remove',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中图书!');
        		return ;
        	}
        	$.messager.confirm('确认','确定下架ID为 '+ids+' 的图书吗？',function(r){
        	    if (r){
        	    	var params = "/rest/book/instock/"+ids;
                	$.post(params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','下架图书成功!',undefined,function(){
            					$("#bookList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    },{
        text:'上架',
        iconCls:'icon-remove',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中图书!');
        		return ;
        	}
        	$.messager.confirm('确认','确定上架ID为 '+ids+' 的图书吗？',function(r){
        	    if (r){
        	    	var params = "/rest/book/reshelf/"+ids;
                	$.post(params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','上架图书成功!',undefined,function(){
            					$("#bookList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    }];
</script>