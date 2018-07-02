<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>用户注册界面</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/camera.css" />
<link rel="stylesheet" href="/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/css/matrix-login.css" />
<link href="/css/font-awesome.css" rel="stylesheet" />
<script type="text/javascript" src="/js/jquery-1.5.1.min.js"></script>


</head>
<body>

	<div
		style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
		<div id="loginbox">
			<form id="personRegForm" method="post" onsubmit="return false;">
				<div class="control-group normal_text">
					<h3>
						<span class="add-on" style="color: red;font-size: 7ex;">用</span>
						<span class="add-on" style="color: yellow;font-size: 7ex;">户</span>
						<span class="add-on" style="color: blue;font-size: 7ex;">注</span>
						<span class="add-on" style="color: green;font-size: 7ex;">册</span><!-- 
						<span class="add-on" style="color: orange;font-size: 7ex;">页</span>
						<span class="add-on" style="color: purple;font-size: 7ex;">面</span> -->
					</h3>
				</div>
				<!-- 用户名 -->
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span  style="color: red;font-size: 4ex;width: 20%;height: 25px;">用户名：</span>
							
							<input type="text" id="regName" name="username" tabindex="1" autoComplete="off"
                                   onpaste="return false;"
                                   value=""
                                   onfocus="if(this.value=='') this.value='';this.style.color='#333'"
                                   onblur="if(this.value=='') {this.value='';this.style.color='#999999'}"/>
                                   
						</div>
					</div>
				</div>
				<!-- 密码 -->
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span style="color: red;font-size: 4ex;width: 20%;height: 25px;">密  码：</span>
                            <input type="password" id="pwd" name="password" class="text" tabindex="2"
                                   style="ime-mode:disabled;"
                                   onpaste="return  false" autocomplete="off"/>
                          
						</div>
					</div>
				</div>
				<!-- 确认密码 -->
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span style="color: red;font-size: 4ex;width: 20%;height: 25px;">确认密码:</span>
							 <input type="password" id="pwdRepeat" name="pwdRepeat" class="text" tabindex="3"
                                   onpaste="return  false" autocomplete="off"/>
						</div>
					</div>
				</div>
				<!-- 邮箱 -->
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span style="color: red;font-size: 4ex;width: 20%;height: 25px;">邮  箱：</span>
							  <input type="text" id="regEmail" name="email" class="text" tabindex="1" autoComplete="off"
                                   onpaste="return false;"
                                   value=""
                                   onfocus="if(this.value=='') this.value='';this.style.color='#333'"
                                   onblur="if(this.value=='') {this.value='';this.style.color='#999999'}"/>
						</div>
					</div>
				</div>
				<!-- 手机号 -->
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span style="color: red;font-size: 4ex;width: 20%;height: 25px;">手机号：</span>
							  <input type="text" id="phone" maxlength="11" name="phone"
								class="text" tabindex="4"
								autocomplete="off" /> 
						</div>
					</div>
				</div>
				
				
				
				
				
				<div class="form-actions">
					<div style="width:86%;padding-left:8%;">
						<input type="button" style="background-color: blue; padding-right:3%; width: 200px; height: 40px;" class="pull-left"  id="registsubmit" value="注册" />
						<input type="button"  style="background-color: blue; padding-right:3%; width: 200px; height: 40px;" class="pull-right" id="logintsubmit" value="去登录"/>
                	</div>
				</div>

			</form>


			<div class="controls">
				<div class="main_input_box">
					<font color="white"><span id="nameerr" style="color: blue;font-size: 3ex;width: 20%;height: 25px;">设计者：14级计科一班-吴鉴锋</span></font>
				</div>
			</div>
		</div>
	</div>
	<!-- 背景图 -->
	<div id="templatemo_banner_slide" class="container_wapper">
		<div class="camera_wrap camera_emboss" id="camera_slide">
			<div data-src="/images/banner_slide_01.jpg"></div>
			<div data-src="/images/banner_slide_02.jpg"></div>
			<div data-src="/images/banner_slide_03.jpg"></div>
		</div>
		<!-- #camera_wrap_3 -->
	</div>
	
<script type="text/javascript">
$(function(){
	$("#logintsubmit").click(function(){
		REGISTER.login(); 
	});
});

$(function(){
	$("#registsubmit").click(function(){
		REGISTER.reg(); 
	});
});
	var REGISTER={
		param:{
			//单点登录系统的url
			surl:""
		},
		inputcheck:function(){
				//不能为空检查
				if ($("#regName").val() == "") {
					alert("用户名不能为空");
					$("#regName").focus();
					return false;
				}
				if ($("#pwd").val() == "") {
					alert("密码不能为空");
					$("#pwd").focus();
					return false;
				}
				if ($("#phone").val() == "") {
					alert("手机号不能为空");
					$("#phone").focus();
					return false;
				}
				//邮箱验证
				if ($("#regEmail").val() == "") {
					alert("邮箱不能为空");
					$("#regEmail").focus();
					return false;
				}
				//密码检查
				if ($("#pwd").val() != $("#pwdRepeat").val()) {
					alert("确认密码和密码不一致，请重新输入！");
					$("#pwdRepeat").select();
					$("#pwdRepeat").focus();
					return false;
				}
				return true;
		},
		beforeSubmit:function() {
			//alert($("#regName").val()+$("#phone").val()+$("#regEmail").val());
			var username = $("#regName").val();
			var phone = $("#phone").val();
			var email = $("#regEmail").val();
				//检查用户是否已经被占用
				$.ajax({
	            	url : REGISTER.param.surl + "/user/check?param="+username+"&type=1",
	            	success : function(data) {
	            		//alert(5);
	            		if (data.data) {
	            			//检查手机号是否存在
	            			$.ajax({
	            				url : REGISTER.param.surl + "/user/check?param="+phone+"&type=2",
				            	success : function(data) {
				            		if (data.data) {
				            			//检查邮箱号是否存在
				            			$.ajax({
				            				url : REGISTER.param.surl + "/user/check?param="+email+"&type=3",
							            	success : function(data) {
							            		if (data.data) {
								            		REGISTER.doSubmit();
							            		} else {
							            			alert("此邮箱已经被注册！");
							            			$("#regEmail").select();
							            		}
							            	}
				            			});
				            		} else {
				            			alert("此手机号已经被注册！");
				            			$("#phone").select();
				            		}
				            	}
	            			});
	            		} else {
	            			alert("此用户名已经被占用，请选择其他用户名");
	            			$("#regName").select();
	            		}	
	            	}
				});
	            	
		},
		doSubmit:function() {
			$.post("/user/register",$("#personRegForm").serialize(), function(data){
				if(data.status == 200){
					alert('用户注册成功，请登录！');
					REGISTER.login();
				} else {
					alert("注册失败！");
				}
			});
		},
		login:function() {
			 location.href = "/page/login";
			 return false;
		},
		reg:function() {
			if (this.inputcheck()) {
				this.beforeSubmit();
			}
		}
	};
</script>
<script src="/js/bootstrap.min.js"></script>
	<script src="/js/jquery-1.7.2.js"></script>
	<script src="/js/jquery.easing.1.3.js"></script>
	<script src="/js/jquery.mobile.customized.min.js"></script>
	<script src="/js/camera.min.js"></script>
	<script src="/js/templatemo_script.js"></script>
	<script type="text/javascript" src="/js/jquery.tips.js"></script>
	<script type="text/javascript" src="/js/jquery.cookie.js"></script>
</body>
</html>
