var SP = TAOTAO = {
		getorderuserid : function(){
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
					$.ajax({
						url : "http://localhost:8082/user/get/" + username,
						//dataType : "jsonp",
						type : "GET",
						success : function(data){
							if(data.status == 200){
								var userid = data.data.id;
								$("#orderuserid").val(userid);
							}
						}
					});
				}
			}
		});
	}
}


$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	SP.getorderuserid();
});