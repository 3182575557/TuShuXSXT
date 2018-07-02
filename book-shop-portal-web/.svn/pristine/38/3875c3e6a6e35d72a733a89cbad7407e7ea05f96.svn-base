var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("TT_TOKEN");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://localhost:8082/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到网上书城！<a href=\"javascript:TF.checkOutLogin();\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

var TF = TAOTAO = {
		checkOutLogin : function(){
			var _ticket = $.cookie("TT_TOKEN");
			if(!_ticket){
				return ;
			}
			$.ajax({
				url : "http://localhost:8082/user/logout/" + _ticket,
				//dataType : "jsonp",
				type : "GET",
				success : function(data){
					if(data.status == 200){
						alert("用户已退出！");
						location.reload(); 
					}
				}
			});
		}
	}



$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});